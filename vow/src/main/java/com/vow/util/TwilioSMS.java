package com.vow.util;

//Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS {
	  // Find your Account Sid and Token at twilio.com/user/account
	  public static final String ACCOUNT_SID = "AC8a1a257f4ac9452973aa3292123a228d";
	  public static final String AUTH_TOKEN = "460af4f1dd3e43732e0017cde6f93cb0";
	  public static String SmsSend(String mobileNumber,String msg) {
		  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		  Message message = Message.creator(new PhoneNumber("+91"+mobileNumber),
			        new PhoneNumber("+16034713165"), 
			        msg).create();
		  System.out.println("messagebody"+message.toString());
		  return message.getSid();
	  }

	  public static void main(String[] args) {
		/*
		 * Twilio.init(ACCOUNT_SID, AUTH_TOKEN); TwilioSMS twilioSMS = new TwilioSMS();
		 * String mobile = "+918608782968"; String msg =
		 * "Twilio OTP testing. OTP :785412 "; String message =
		 * twilioSMS.SmsSend(mobile,msg); System.out.println("message"+message);
		 */
	    
	  
	  
	   
	  }
	}