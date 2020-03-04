
package com.vow.util;

import java.util.Random;

public class CommonUtil {

	public static String generateOtp(int len) {

		int randomPin = (int) (Math.random() * 900000) + 100000;
		String otp = String.valueOf(randomPin);
		return otp;
	}

}
