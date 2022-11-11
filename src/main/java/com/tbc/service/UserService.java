package com.tbc.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.tbc.dto.UserRegistrationDto;
import com.tbc.model.PurchaseTable;
import com.tbc.model.ReferralTransactions;
import com.tbc.model.TbcTransaction;
import com.tbc.model.User;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
    User getUserById(long id);
    void saveUser(User user);
    void  sendVerificationMail(User user,String siteUrl) throws UnsupportedEncodingException, MessagingException;
	boolean verify(String verificationCode);
	String[] generateToken();
	List<PurchaseTable> pt();
	List<PurchaseTable> allPt();
	List<TbcTransaction> tt();
	List<TbcTransaction> alltt();
	List<ReferralTransactions> rtr();
	String[] getTokenBalance(String address);
	boolean transferToken(String address, long amount);
}