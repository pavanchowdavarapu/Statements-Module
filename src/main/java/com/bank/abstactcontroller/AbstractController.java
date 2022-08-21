package com.bank.abstactcontroller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractController {

	public String getUserWorkSpace() {
		String user;
		Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (details instanceof UserDetails) {
			user = ((UserDetails) details).getUsername();
		} else {
			user = details.toString();
		}
		return user;

	}

	public Date parseStringToDate(String stringDate) throws ParseException {

		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
		return date;

	}

	public Date addDays(Date date, int days) {
		if (date == null) {
			return null;
		}
		GregorianCalendar gc = convert(date);
		gc.add(Calendar.DATE, days);
		return convert(gc);

	}

	public GregorianCalendar convert(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc;

	}

	public Date convert(GregorianCalendar date) {
		return date == null ? null : new Date(date.getTime().getTime());
	}

	public static String encryptAccountNumber(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
