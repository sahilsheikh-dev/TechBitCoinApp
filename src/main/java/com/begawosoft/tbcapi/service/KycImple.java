package com.begawosoft.tbcapi.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.begawosoft.tbcapi.dao.KycDao;
import com.begawosoft.tbcapi.entity.KycTbl;

@Service
public class KycImple implements KycService {

	@Autowired
	private KycDao kycDao;

	@Override
	public KycTbl kycVerification(KycTbl kycTbl, MultipartFile aadharFront, MultipartFile aadharBack) {
		try {
			String aadharFrontFileName = uploadDoc(kycTbl.getKycPath(), aadharFront,
					(kycTbl.getUserPk() + "aadharfront"));
			String aadharBackFileName = uploadDoc(kycTbl.getKycPath(), aadharBack, (kycTbl.getUserPk() + "aadharback"));

			kycTbl.setAadharFrontName(aadharFrontFileName);
			kycTbl.setAadharBackName(aadharBackFileName);
			kycTbl.setStatus("Success");
			kycTbl.setOutputCode("Success");

			if (kycTbl.getStatus().equals("Success")) {
				kycDao.save(kycTbl);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			kycTbl.setStatus("Failure");
			kycTbl.setOutputCode("Failure");
		}
		return kycTbl;
	}

	@Override
	public String uploadDoc(String path, MultipartFile file, String userFileName) throws IOException {

		String fileName = userFileName + file.getOriginalFilename();
		String filePath = path + File.separator + fileName;

		File f = new File(path);
		if (!f.exists())
			f.mkdir();

		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName;
	}

	@Override
	public boolean checkKyc(String userId) {
		return kycDao.existsByUserPk(userId);
	}

}
