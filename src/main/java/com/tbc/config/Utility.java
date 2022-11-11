package com.tbc.config;

import javax.servlet.http.HttpServletRequest;

public class Utility {
	public static String getSiteUrl(HttpServletRequest request) {
		String siteURL=request.getRequestURL().toString();
		return siteURL;
	}
}
