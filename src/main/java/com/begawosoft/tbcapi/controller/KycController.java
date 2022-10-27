package com.begawosoft.tbcapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.begawosoft.tbcapi.entity.KycTbl;
import com.begawosoft.tbcapi.service.KycService;
import com.begawosoft.tbcapi.utilities.Utilities;

@RestController
public class KycController {

	@Autowired
	private KycService kycService;

	@Value("${project.kycdoc}")
	private String path;

	@PostMapping("/kyc")
	public KycTbl aadharFrontUpload(@RequestParam("userPk") String userPk,
			@RequestParam("aadharFront") MultipartFile aadharFront,
			@RequestParam("aadharBack") MultipartFile aadharBack, @RequestParam("address") String address) {
		return kycService.kycVerification(new KycTbl(Utilities.generateRandomId(30), userPk, path, address),
				aadharFront, aadharBack);
	}

	@GetMapping("/checkkyc/{userId}")
	public boolean checkKyc(@PathVariable("userId") String userId) {
		return kycService.checkKyc(userId);
	}
}
