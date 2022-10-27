package com.begawosoft.tbcapi.utilities;

import java.util.Random;

public class Utilities {
	
private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	public static String generateRandomId(int size) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length()-1)));
		}
		return sb.toString();
	}
	
	public static boolean isEmpty(String s) {
        return ((s == null) || ("".equals(s)) || ("null".equals(s)));
    }
	
	public static void main(String[] args) {
		System.out.println("OUTPUT::"+generateRandomId(30));
	}
	
}
