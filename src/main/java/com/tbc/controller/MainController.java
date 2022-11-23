package com.tbc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tbc.model.BtcPurchaseTable;
import com.tbc.model.CustomerData;
import com.amazonaws.services.s3.AmazonS3;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.tbc.dto.EmailDetailsDto;
import com.tbc.dto.UserRegistrationDto;
import com.tbc.model.EmailDetails;
import com.tbc.model.KycTable;
import com.tbc.model.PurchaseTable;
import com.tbc.model.ReferralTable;
import com.tbc.model.ReferralTransactions;
import com.tbc.model.TbcTransaction;
import com.tbc.model.User;
import com.tbc.repository.BtcPurchaseTableRepository;
import com.tbc.repository.KycRepository;
import com.tbc.repository.PurchaseTableRepository;
import com.tbc.repository.ReferTranRepo;
import com.tbc.repository.ReferralRepository;
import com.tbc.repository.TbcTransactionRepository;
import com.tbc.repository.UserRepository;
import com.tbc.service.EmailService;
import com.tbc.service.UserService;

import net.bytebuddy.utility.RandomString;

@Controller
public class MainController {

	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PurchaseTableRepository ptr;

	@Autowired
	private TbcTransactionRepository tbctr;
	@Autowired
	private BtcPurchaseTableRepository btcptr;
	@Autowired
	private ReferralRepository rr;
	@Autowired
	private ReferTranRepo rtr;
	@Autowired
	KycRepository kycRepo;

	@Autowired
	private AmazonS3 s3Client;

	private String bucketName = "tbcsource";

	@Value("${stripe.apikey}")
	String stripeKey;

	public User loginDetails() {
		AbstractAuthenticationToken auth = (AbstractAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();
		User user = userRepository.findByMail(auth.getName());
		return user;
	}

	public String uploadDoc(MultipartFile file) throws IOException {

		File modifiedFile = new File(file.getOriginalFilename());
		FileOutputStream os = new FileOutputStream(modifiedFile);
		os.write(file.getBytes());

		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

		s3Client.putObject(bucketName, fileName, modifiedFile);

		modifiedFile.delete();

		return fileName;
	}

	@PostMapping("/kyc")
	public String aadharFrontUpload(@RequestParam("profilePic") MultipartFile profilePic,
			@RequestParam("aadharFront") MultipartFile aadharFront,
			@RequestParam("aadharBack") MultipartFile aadharBack, @RequestParam("address") String address) {
		Long id = loginDetails().getId();
		kycVerification(id, profilePic, address, aadharFront, aadharBack);
		return "redirect:/profile";
	}

	public User kycVerification(Long id, MultipartFile profilePic, String address, MultipartFile aadharFront,
			MultipartFile aadharBack) {
		User kycTbl = loginDetails();
		try {
			String ProfilePicFileName = uploadDoc(profilePic);
			String aadharFrontFileName = uploadDoc(aadharFront);
			String aadharBackFileName = uploadDoc(aadharBack);
			System.out.println(profilePic);
			System.err.println(address);
			kycTbl.setProfileName(ProfilePicFileName);
			kycTbl.setAadharFrontName(aadharFrontFileName);
			kycTbl.setAadharBackName(aadharBackFileName);
			kycTbl.setAddress(address);
			kycTbl.setStatus("uploaded");
			kycTbl.setOutputCode("success");
			User user = loginDetails();
			user.setAddress(address);
			userRepository.save(user);
			if (kycTbl.getOutputCode().equalsIgnoreCase("Success")) {
				userRepository.save(kycTbl);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			kycTbl.setStatus("Failure");
		}
		return kycTbl;
	}

	@GetMapping("/login")
	public String login() throws IOException, SchedulerException {
		return "login";
	}

	@GetMapping("/profile")
	public String profile(Model model) {
		KycTable kycTable = new KycTable();
		model.addAttribute("kycTable", kycTable);
		model.addAttribute("user", loginDetails());
		return "app-profile";
	}

	@PostMapping("/paywithcoin")
	public @ResponseBody String paywithcoin(HttpServletRequest request, @RequestParam("amount") double amount,
			@RequestParam("from") String from, HttpSession session) {
		String name = loginDetails().getName();
		String email = loginDetails().getMail();
		System.out.println(from);
		double amoun = amount;
		request.getSession().setAttribute("from", from);
		request.getSession().setAttribute("purchasePrice", amount);
		System.out.println(request.getSession().getAttribute("from"));
		System.out.println("PayWithCoin" + amoun);
		String statusUrl = coinPaymentTransfer(request, name, email, amount);
		System.out.println(statusUrl);
		return statusUrl;
	}

	public String coinPaymentTransfer(HttpServletRequest request, String name, String email, double amount) {
		CoinPaymentsAPI api = new CoinPaymentsAPI("54ecd4924f98eb055003eee7a32b452c4a95cf6232dc9bb8e1e32aae88f1330f",
				"49C27879A1921255475b101F0800A765be4e1EB23a2a40d4219371eB4c0aE3E6");

		boolean status = false;
		if (request.getSession().getAttribute("from").equals("package")) {
			System.out.println("bought through package");
		}
		// Getting basic account information
		JsonObject accountInfo = api.call("get_basic_info");
		System.out.println(accountInfo.get("username").getAsString());
		System.out.println(request.getSession().getAttribute("from"));

		System.out.println(accountInfo.get("email").getAsString());
		// Creating a transaction
		// amount in dollar
		JsonObject transactionInfo = api.set("amount", amount).set("currency1", "USD").set("currency2", "BTC")
				.set("buyer_name", name).set("buyer_email", email).call("create_transaction");
		System.out.println(transactionInfo.get("txn_id").getAsString());
		System.out.println(transactionInfo.get("status_url").getAsString());
		String statusUrl = transactionInfo.get("status_url").getAsString();
		System.out.println(transactionInfo.get("qrcode_url").getAsString());
		System.out.println(status);
		return statusUrl;
	}

	@RequestMapping("/paymentForm")
	public String paymentForm(@ModelAttribute("c") CustomerData c, Model model, ModelMap map) throws StripeException {
		Stripe.apiKey = stripeKey;
		CustomerData customer = new CustomerData();
		customer.setAmount(c.getAmount());
		customer.setEmail(c.getEmail());
		model.addAttribute("c", customer);
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount(c.getAmount() * 100)
				.setCurrency("inr").addPaymentMethodType("card").setCustomer(loginDetails().getStripeId()).build();
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		System.out.println(paymentIntent.getClientSecret());
		map.put("clientSecret", paymentIntent.getClientSecret());
		return "checkout";
	}

	@PostMapping("/payWithNewCard")
	public @ResponseBody String payWithNewCard(@RequestParam("paymentId") String paymentId, HttpSession httpSession)
			throws StripeException {
		System.out.println("in paywithnewcard");
		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);

		String paymentResponse = paymentIntent.toJson();
		System.out.println(paymentResponse);
		Double purchasePrice = (double) (paymentIntent.getAmount() / 100);
		System.out.println(purchasePrice);
		buy(purchasePrice);
		return "Your Transaction is Success";
	}

	@PostMapping("/payFromDashboard")
	public @ResponseBody String payFromDashboard(@RequestParam("paymentId") String paymentId, HttpSession httpSession)
			throws StripeException {
		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);
		String paymentResponse = paymentIntent.toJson();
		System.out.println(paymentResponse);
		Double purchasePrice = (double) (paymentIntent.getAmount() / 100);

		User user = loginDetails();
		long amount = (long) (purchasePrice / 5);
		try {
			boolean transferTokenStatus = userService.transferToken(user.getWalletAddress(), amount);
			if (transferTokenStatus) {
				setBtcTable(purchasePrice);
				setTransactionTable(purchasePrice);
				return "Your Transaction is Success";
			} else {
				return "Internal Server Error Please Try Again";
			}
		} catch (Exception e) {
			return "Internal Server Error Please Try Again";
		}
	}

	@PostMapping("/coinIpn")
	public String ipn(HttpServletRequest request) {
		if (request.getSession().getAttribute("from").equals("dashBoard")) {
			User user = loginDetails();
			double purchasePrice = (double) request.getSession().getAttribute("from");
			long amount = (long) (purchasePrice / 5);
			try {
				boolean transferTokenStatus = userService.transferToken(user.getWalletAddress(), amount);
				if (transferTokenStatus) {
					setBtcTable(purchasePrice);
					setTransactionTable(purchasePrice);
					request.getSession().invalidate();
					return "redirect:/";
				} else {
					request.getSession().invalidate();
					return "redirect:/?paymenetStatus";
				}
			} catch (Exception e) {
				request.getSession().invalidate();
				return "redirect:/?paymenetStatus";
			}
		}
		if (request.getSession().getAttribute("from").equals("package")) {
			double purchasePrice = (double) request.getSession().getAttribute("from");
			buy(purchasePrice);
			request.getSession().invalidate();
			return "redirect:/myWallets";
		}
		return "null";
	}

	public void setBtcTable(Double purchasePrice) {

		User user = loginDetails();
		int purchaserTbc = (int) (purchasePrice * 20 / 100);
		BtcPurchaseTable bpt = new BtcPurchaseTable();
		bpt.setDate(new Date());
		bpt.setPrice(purchasePrice);
		bpt.setQuantity(purchaserTbc);
		bpt.setStatus("success");
		bpt.setUser(user);
		btcptr.save(bpt);
	}

	public void setTransactionTable(Double purchasePrice) {

		User user = loginDetails();
		int purchaseTbc = (int) (purchasePrice * 20 / 100);
		int pp = (int) (purchasePrice * 1 / 1);
		TbcTransaction tbct = new TbcTransaction();
		tbct.setDate(new Date());
		tbct.setPrice(pp);
		tbct.setTbcAmount(purchaseTbc);
		tbct.setStatus("success");
		tbct.setUser(user);
		tbctr.save(tbct);

	}

	public void buy(Double purchasePrice) {
		System.out.println("in buy");
		User user = loginDetails();
		user.setHasPurchased("yes");
		double purchaserTbc = purchasePrice * 20 / 100;
		user.setTbcBalance(user.getTbcBalance() + purchaserTbc);
		if (purchasePrice == 100.0) {
			user.setHas100pack("yes");
			user.setHasPurchased("yes");
		}
		if (purchasePrice == 200.0) {
			user.setHas200pack("yes");
			user.setHasPurchased("yes");
		}
		if (purchasePrice == 500.0) {
			user.setHas500Pack("yes");
			user.setHasPurchased("yes");
		}
		if (purchasePrice == 1000.0) {
			user.setHas1000pack("yes");
			user.setHasPurchased("yes");

		}
		if (purchasePrice == 1500.0) {
			user.setHas1500pack("yes");
			user.setHasPurchased("yes");
		}
		if (purchasePrice == 2000.0) {
			user.setHas2000pack("yes");
			user.setHasPurchased("yes");
		}
		if (purchasePrice == 5000.0) {
			user.setHas5000pack("yes");
			user.setHasPurchased("yes");
		}
		if (purchasePrice == 10000.0) {
			user.setHas10000pack("yes");
			user.setHasPurchased("yes");
		}
		setPurchaseTable(purchasePrice, user);
		userRepository.save(user);
		if (user.getReferedByPk() != null && user.getReferedByPk().length() > 1) {

			ReferralTable rl1 = rr.findByReferralId(user.getReferedByPk());
			Optional<User> ul1l = userRepository.findById(rl1.getUser().getId());
			if (!ul1l.isEmpty()) {
				int level = 1;
				User ul1 = ul1l.get();
				System.out.println(ul1.getName());
				double ul1tbc = purchaserTbc * 4 / 100;
				ul1.setTbcBalance(ul1.getTbcBalance() + ul1tbc);
				userRepository.save(ul1);
				setReferralTransaction(level, ul1, ul1tbc, purchasePrice);
				if (ul1.getReferedByPk().length() > 1) {
					ReferralTable rl2 = rr.findByReferralId(ul1.getReferedByPk());

					Optional<User> ul2l = userRepository.findById(rl2.getUser().getId());
					if (!ul2l.isEmpty()) {
						level = 2;
						User ul2 = ul2l.get();
						System.out.println(ul2.getName());
						double ul2tbc = ul1tbc * 3 / 100;
						ul2.setTbcBalance(ul2.getTbcBalance() + ul2tbc);
						userRepository.save(ul2);
						setReferralTransaction(level, ul2, ul2tbc, purchasePrice);
						if (ul2.getReferedByPk().length() > 1) {
							ReferralTable rl3 = rr.findByReferralId(ul2.getReferedByPk());

							Optional<User> ul3l = userRepository.findById(rl3.getUser().getId());
							if (!ul3l.isEmpty()) {
								level = 3;
								User ul3 = ul3l.get();
								System.out.println(ul3.getName());
								Double ul3tbc = ul2tbc * 2 / 100;
								ul3.setTbcBalance(ul3.getTbcBalance() + ul3tbc);
								userRepository.save(ul3);
								setReferralTransaction(level, ul3, ul3tbc, purchasePrice);
							}
						}
					}
				}
			}
		}
	}

	private void setReferralTransaction(int level, User ul1, double i, Double purchasePrice) {
		ReferralTransactions rt = new ReferralTransactions();
		rt.setLevel(level);
		rt.setPlanOfPurchase(purchasePrice);
		rt.setBtcEarned(i);
		rt.setTime(new Date());
		System.out.println(i);
		rt.setUser(ul1);
		rt.setGotFrom(loginDetails());
		rtr.save(rt);
	}

	private void setPurchaseTable(Double purchasePrice, User user) {
		PurchaseTable pt = new PurchaseTable();
		pt.setAmountDeposited(0);
		Date date = new Date();
		pt.setDate(date);
		pt.setPurchasePrice(purchasePrice);
		pt.setStatus("success");
		pt.setUser(user);
		ptr.save(pt);
	}

	@GetMapping("/referralTransactions")
	public String referralTransactions(Model model) {

		User user = loginDetails();
		System.out.println(userService.rtr());
		model.addAttribute("rtr", userService.rtr());
		model.addAttribute("user", loginDetails());
		return "referal-tranasactions";
	}

	@GetMapping("/kycVerification")
	public String kycVerification(Model model) {
		model.addAttribute("alluser", userRepository.findAll());
		model.addAttribute("user", loginDetails());
		return "kyc-verification";
	}

	@GetMapping("/viewUser/{id}")
	public String viewUser(@PathVariable(value = "id") long id, Model model) throws IOException, SchedulerException {
		model.addAttribute("user", loginDetails());
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			System.out.println(user.get());
		}
		ReferralTable rt = rr.findByReferralId(user.get().getReferedByPk());
		model.addAttribute("rt", rt);
		model.addAttribute("u", user.get());
		return "viewUser";
	}

	@GetMapping("/verify/{id}")
	public String verify(@PathVariable(value = "id") long id, Model model) throws IOException, SchedulerException {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			System.out.println(user.get());
		}
		user.get().setStatus("verified");
		userRepository.save(user.get());
		return "redirect:/kycVerification?verified";
	}

	@GetMapping("/reject/{id}")
	public String reject(@PathVariable(value = "id") long id, Model model) throws IOException, SchedulerException {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			System.out.println(user.get());
		}
		user.get().setStatus("rejected");
		userRepository.save(user.get());
		return "redirect:/kycVerification?rejected";
	}

	@GetMapping("/activateUser/{id}")
	public String activateUser(@PathVariable(value = "id") long id, Model model)
			throws IOException, SchedulerException {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			System.out.println(user.get());
		}
		user.get().setRoles("ROLE_USER");
		user.get().setIsDeleted(0);
		userRepository.save(user.get());
		return "redirect:/userControl?activated";
	}

	@GetMapping("/blockUser/{id}")
	public String blockUser(@PathVariable(value = "id") long id, Model model) throws IOException, SchedulerException {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			System.out.println(user.get());
		}
		user.get().setRoles("ROLE_BLOCKED");
		user.get().setIsDeleted(1);
		userRepository.save(user.get());
		return "redirect:/userControl?blocked";
	}

	@GetMapping("/buy")
	public String buy(Model model, ModelMap map, @RequestParam("param1") int param1)
			throws IOException, SchedulerException, StripeException {
		Stripe.apiKey = stripeKey;
		User user = loginDetails();
		CustomerData customer = new CustomerData();
		customer.setAmount((long) param1);
		customer.setEmail(user.getMail());
		model.addAttribute("c", customer);
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount((long) param1 * 100)
				.setCurrency("inr").addPaymentMethodType("card").setCustomer(user.getStripeId()).build();
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		System.out.println(paymentIntent.getClientSecret());
		map.put("clientSecret", paymentIntent.getClientSecret());
		return "buy500p";

	}

	@PostMapping("/buy2")
	public String buy2(@ModelAttribute("user") UserRegistrationDto registrationDto, Model model, ModelMap map)
			throws StripeException {
		System.out.println("in post" + registrationDto.getAmount());
		int price = registrationDto.getAmount() * 5;
		Stripe.apiKey = stripeKey;
		User user = loginDetails();
		CustomerData customer = new CustomerData();
		customer.setAmount((long) price);
		customer.setEmail(user.getMail());
		model.addAttribute("c", customer);
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount((long) price * 100)
				.setCurrency("inr").addPaymentMethodType("card").setCustomer(user.getStripeId()).build();
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		map.put("clientSecret", paymentIntent.getClientSecret());
		return "dashboardBuy";
	}

	@GetMapping("/")
	public String home(Model model, ModelMap map) throws IOException, SchedulerException {
		User user = loginDetails();
		String walletAddress = user.getWalletAddress();
		System.out.println(walletAddress);
		String[] balances = userService.getTokenBalance(walletAddress);
		String tokenBalance = balances[1];
		
		BigInteger tokenBal = new BigInteger(tokenBalance);
		BigInteger dollarConversion = new BigInteger("2");
		BigInteger resultAmount = tokenBal.divide(dollarConversion);
		
		map.put("tokenBalUsd", String.format("%.02f", resultAmount.floatValue()));
		
		System.out.println(user.getRoles());
		model.addAttribute(user);
		String usd = String.format("%.02f", user.getTbcBalance() * .61);
		map.put("usd", usd);
		map.put("tokenBalance", tokenBalance);
		System.out.println("logging in");
		if (user.isEnabled()) {
			return "index";
		} else {
			return "login?error";
		}
	}

	@GetMapping("/registerSuccess")
	public String otp(Model model) {
		return "registerSuccess";
	}

	@GetMapping("/getEmailForm")
	public String emailForm(Model model) {

		EmailDetails emailDetails = new EmailDetails();

		User user = loginDetails();

		model.addAttribute(emailDetails);
		model.addAttribute(user);
		return "sendMail";
	}

	@GetMapping("/myWallets")
	public String myWallets(Model model, HttpServletRequest request) {
		PurchaseTable pt = new PurchaseTable();
		User user = loginDetails();
		long id = user.getId();
		model.addAttribute("pt", userService.pt());
		model.addAttribute("user", user);
		System.out.println(pt.getAmountDeposited());
		System.out.println(request.getSession().getAttribute("from"));

		model.addAttribute("allpt", userService.allPt());
		return "my-wallets";
	}

	@GetMapping("/packagePurchased")
	public String packagePurchased(Model model) {
		User user = loginDetails();
		model.addAttribute("user", user);
		model.addAttribute("allpt", userService.allPt());
		return "package-purchased";
	}

	@GetMapping("/transaction")
	public String transaction(Model model) {
		User user = loginDetails();
		model.addAttribute("tt", userService.tt());
		model.addAttribute("user", user);
		return "tranasactions";
	}

	@GetMapping("/userControl")
	public String userControl(Model model) {
		User user = loginDetails();
		model.addAttribute("alluser", userRepository.findAll());
		model.addAttribute("user", user);
		return "user-control";
	}

	@PostMapping("/getEmail")
	public String submitMail(@ModelAttribute("emailDetails") EmailDetailsDto edt, Model model) {

		EmailDetails emailDetails = new EmailDetails();
		System.out.println(edt);
		String status = emailService.sendSimpleMail(edt);
		return "plans";
	}

}
