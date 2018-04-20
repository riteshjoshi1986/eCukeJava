package com.myCompany.util;

import org.apache.commons.codec.binary.Base64;

public class Security {
		
	public static String encoded(String value) {
		return new String(Base64.encodeBase64(value.getBytes()));
	}
	
	public static String decoded(String value) {
		return new String(Base64.decodeBase64(value));
	}
}
