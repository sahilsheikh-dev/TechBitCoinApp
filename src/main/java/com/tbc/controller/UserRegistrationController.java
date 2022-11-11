package com.tbc.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tbc.config.Utility;
import com.tbc.dto.UserRegistrationDto;
import com.tbc.model.User;
import com.tbc.repository.ReferralRepository;
import com.tbc.repository.UserRepository;
import com.tbc.service.UserService;
import com.tbc.service.UserServiceImpl;

@Controller
public class UserRegistrationController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	ReferralRepository rr;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserRegistrationDto());
		return "registration";
	}

	@PostMapping("/processRegister")
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		String siteUrl = Utility.getSiteUrl(request);
		User findUser = userRepository.findByMail(registrationDto.getMail());
		System.out.println("in process");
		System.out.println(registrationDto.getReferedByPk() + "fhiwojwwjfo");
		System.out.println(rr.findByReferralId(registrationDto.getReferedByPk()) + "find");
		if (findUser != null) {
			return "redirect:/registration?wrongMail";
		}

		else if (registrationDto.getReferedByPk().length() > 0
				&& rr.findByReferralId(registrationDto.getReferedByPk()) == null) {
			return "redirect:/registration?wrongReferral";
		} else {
			User user = userService.save(registrationDto);
			userService.sendVerificationMail(user, siteUrl);
			return "redirect:/registration?success";
		}

	}

	@GetMapping("/processRegister/verify")
	public String verifyUser(@Param("code") String code, Model model) {
		System.out.println("verify api hit");
		boolean verified = userService.verify(code);
		if(verified==true)
		{
			String[] token=userService.generateToken();
			System.out.println(token);
			User user=userRepository.findByVerificationCode(code);
			user.setWalletAddress(token[0]);
			userRepository.save(user);
			return "redirect:/login?verified";
		}
		else {
			return "redirect/login?failed";
		}
	}
}