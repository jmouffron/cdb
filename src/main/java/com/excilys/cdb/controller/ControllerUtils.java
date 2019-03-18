package com.excilys.cdb.controller;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class ControllerUtils {
	public static Scanner scan = new Scanner(System.in);

	private ControllerUtils() {
	}

	static Function<Long, Boolean> isStrictlyPositive = inputVal -> inputVal < 0 || inputVal == null;
	static Function<Long, Boolean> isNotNullLong = inputVal -> inputVal != null;
	static Function<String, Boolean> isNotNullString = inputVal -> inputVal.equals("") || inputVal == null;
	static Function<Long, Boolean> isPositive = inputVal -> inputVal <= 0 || inputVal == null;
	static Function<String, Boolean> isPorAorE = inputVal -> !inputVal.equals("P") || !inputVal.equals("A") || !inputVal.equals("E") || !inputVal.equals("")|| inputVal == null;

	public static long getLongInput(String message, Function<Long, Boolean> boolFunc) {
		Long value = null;
		boolean error;

		do {
			error = false;
			System.out.println(message);
			try {
				value = scan.nextLong();
			} catch (InputMismatchException e) {
				error = true;
			}
		} while ( error || boolFunc.apply(value) );

		return value;
	}

	public static String getStringInput(String message, Function<String, Boolean> boolFunc) {
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
		} while ( error || boolFunc.apply(value) );

		return value;
	}

	public static Timestamp getTimestampInput(String message) {
		Timestamp value = null;
		boolean error;
	 
		do {
			error = false;
			System.out.println(message);
			try {
				value = Timestamp.valueOf(scan.nextLine());
			} catch (InputMismatchException e) {
				error = true;
			}
		} while ( error );

		return value;
	}
}
