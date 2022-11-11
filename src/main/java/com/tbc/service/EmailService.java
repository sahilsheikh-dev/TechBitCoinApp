package com.tbc.service;

import com.tbc.dto.EmailDetailsDto;
import com.tbc.model.EmailDetails;

public interface EmailService {
	String sendSimpleMail(EmailDetailsDto details);
    String mailStatus(String receiver,String msg,String subject);
    String verifyMail();
}
