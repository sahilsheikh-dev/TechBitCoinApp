package com.begawosoft.tbcapi.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.begawosoft.tbcapi.entity.KycTbl;

public interface KycService {
	
	
	KycTbl kycVerification(KycTbl kycTbl, MultipartFile aadharFront, MultipartFile aadharBack);
	String uploadDoc(String path, MultipartFile file, String userFileName) throws IOException;
	boolean checkKyc(String userId);

}
