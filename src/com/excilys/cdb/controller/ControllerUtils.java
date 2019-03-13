package com.excilys.cdb.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class ControllerUtils {

	static long getLongInput(String message, Function<Long, Boolean> boolFunc) {
		Scanner scan = new Scanner(System.in);
		long value = 0L;
		boolean error;
		do {
			error = false;
			System.out.println(message);
			try {
				value = scan.nextLong();
			} catch (InputMismatchException e) {
				error = true;
			}
		} while (error || boolFunc.apply(value));
		scan.close();
		return value;
	}

	static String getStringInput(String message, Function<String, Boolean> boolFunc) {
		Scanner scan = new Scanner(System.in);
		String value = "";
		boolean error;
		do {
			error = false;
			System.out.println(message);
			try {
				value = scan.next();
			} catch (InputMismatchException e) {
				error = true;
			}
		} while (error || boolFunc.apply(value));
		scan.close();
		return value;
	}

	static Timestamp getTimestampInput(String message) {
		Scanner scan = new Scanner(System.in);
		Timestamp value = null;
		SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		boolean error;
		do {
			error = false;
			System.out.println(message);
			try {
				value = Timestamp.valueOf(scan.next());
				timestampFormatter.format(value);
			} catch (InputMismatchException e) {
				error = true;
			}
		} while (error);
		scan.close();
		return value;
	}
}
