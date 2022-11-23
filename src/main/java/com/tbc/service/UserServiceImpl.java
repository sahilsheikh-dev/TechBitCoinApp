package com.tbc.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.tbc.dto.UserRegistrationDto;
import com.tbc.model.PurchaseTable;
import com.tbc.model.ReferralTable;
import com.tbc.model.ReferralTransactions;
import com.tbc.model.Role;
import com.tbc.model.TbcTransaction;
import com.tbc.model.User;
import com.tbc.repository.PurchaseTableRepository;
import com.tbc.repository.ReferTranRepo;
import com.tbc.repository.ReferralRepository;
import com.tbc.repository.TbcTransRepo;
import com.tbc.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private PurchaseTableRepository ptr;
	@Autowired
	private TbcTransRepo ttr;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ReferTranRepo rtr;
	@Autowired
	private ReferralRepository rr;
	@Autowired
	JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String sender;
	@Value("${stripe.apikey}")
	String stripeKey;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {

		Calendar date = Calendar.getInstance();
		Date e = new Date(date.getTimeInMillis() + (1 * 60 * 1000));
		System.out.println(date.getTime());
		Date s = date.getTime();
		String randomCode = RandomString.make(64);
		String myReferralCode = RandomString.make(6);

		User user = new User(registrationDto.getName(), passwordEncoder.encode(registrationDto.getPassword()),
				registrationDto.getMail(), registrationDto.getReferedByPk(), "", 0, 0, "", randomCode,
				"ROLE_UNVERIFIED", myReferralCode);
		user.setName(registrationDto.getName());
		user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
		user.setMail(registrationDto.getMail());
		user.setEnabled(false);
		user.setExpireAt(e);
		user.setCreatedAt(s);
		user.setVerificationCode(randomCode);
		user.setStatus("notUploaded");
		userRepository.save(user);
		return user;
	}

	@Override
	public void sendVerificationMail(User user, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		String toAddress = user.getMail();
		String senderName = "BITCOIN";
		String subject = "Please verify your registration";
		String content = "<p>Dear " + user.getName() + ",</p>";
		content += "<p>Please click the link below to verify your registration:</br>";
		String verifyURL = siteUrl + "/verify?code=" + user.getVerificationCode();
		content += "<h3><a href=\"" + verifyURL + "\">Verify</a></h3>";
		System.out.println(verifyURL);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(sender, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByMail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String roles) {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roles);
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public void saveUser(User user) {
		this.userRepository.save(user);
	}

	@Override
	public User getUserById(long id) {
		return null;
	}

	@Override
	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);
		if (user == null || user.isEnabled()) {
			return false;
		}
		Date current = Calendar.getInstance().getTime();
		long diff = TimeUnit.MILLISECONDS.toSeconds(current.getTime() - user.getExpireAt().getTime());
		System.out.println(diff);
		if (diff >= 0) {
			userRepository.delete(user);
			return false;
		} else {
			user.setEnabled(true);
			user.setRoles("ROLE_USER");
			String stripeId = RandomString.make(20);
			user.setStripeId(stripeId);
			userRepository.save(user);
			ReferralTable rt = new ReferralTable();
			rt.setReferralId(user.getMyReferralCode());
			user.setReferedByPk(user.getReferedByPk());
			rt.setUser(user);
			rr.save(rt);
			Stripe.apiKey = stripeKey;
			Map<String, Object> params = new HashMap<>();
			params.put("name", user.getName());
			params.put("email", user.getMail());
			params.put("id", stripeId);
			try {
				Customer customer = Customer.create(params);
			} catch (StripeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		}
	}

	public User loginDetails() {
		AbstractAuthenticationToken auth = (AbstractAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();
		User user = userRepository.findByMail(auth.getName());
		return user;
	}

	@Override
	public String[] generateToken() {
		String url = "http://ec2-52-39-192-220.us-west-2.compute.amazonaws.com:8000/generateWallet";
		RestTemplate restTemplate = new RestTemplate();
		Object apiReturnData = restTemplate.getForObject(url, Object.class);
		JSONObject obj = new JSONObject((Map) apiReturnData);
		System.out.println(obj);
		String address = obj.get("address").toString();
		System.out.println(address);
		String privateKey = obj.get("privateKey").toString();
		System.out.println(privateKey);
		String[] tokenData = { obj.get("address").toString(), obj.get("privateKey").toString() };
		System.out.println(tokenData);
		return tokenData;
	}

	@Override
	public String[] getTokenBalance(String address) {
		String url = "http://ec2-52-39-192-220.us-west-2.compute.amazonaws.com:8000/getTokenBalance/" + address;
		RestTemplate restTemplate = new RestTemplate();
		Object apiReturnData = restTemplate.getForObject(url, Object.class);
		JSONObject obj = new JSONObject((Map) apiReturnData);
		String[] balanceData = { obj.get("bnbBalance").toString(), obj.get("tokenBalance").toString() };
		System.out.println(address);
		System.out.println(balanceData[0]);
		System.out.println(balanceData[1]);
		return balanceData;
	}

	@Override
	public boolean transferToken(String address, long amount) {
		boolean status = false;
		String url = "http://ec2-52-39-192-220.us-west-2.compute.amazonaws.com:8000/transferToken";
		Map<String, String> map = new HashMap<>();
		map.put("address", address);
		map.put("amount", String.valueOf(amount));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> response = restTemplate.postForEntity(url, map, Void.class);
		if (response.getStatusCode() == HttpStatus.OK)
			status = true;
		else
			status = false;
		return status;
	}

	@Override
	public List<PurchaseTable> allPt() {
		return ptr.findAll();
	}

	@Override
	public List<TbcTransaction> tt() {
		User user = loginDetails();
		return ttr.findByUserId(user.getId());
	}

	@Override
	public List<TbcTransaction> alltt() {
		return ttr.findAll();
	}

	@Override
	public List<ReferralTransactions> rtr() {
		// TODO Auto-generated method stub
		User user = loginDetails();
		return rtr.findByUserId(user.getId());
	}

	@Override
	public List<PurchaseTable> pt() {
		// TODO Auto-generated method stub
		User user = loginDetails();
		return ptr.findByUserId(user.getId());
	}

}
