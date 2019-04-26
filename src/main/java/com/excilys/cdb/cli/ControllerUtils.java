package com.excilys.cdb.cli;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerUtils {
	public static Scanner scan = new Scanner(System.in);
	private static Logger logger = LoggerFactory.getLogger(ControllerUtils.class);
	private ControllerUtils() {}

	static Function<Long, Boolean> isStrictlyPositive = inputVal -> inputVal == null || inputVal < 0 ;
	static Function<Long, Boolean> isNotNullLong = inputVal -> inputVal != null;
	static Function<String, Boolean> isNotNullString = inputVal -> "".equals(inputVal);
	static Function<Long, Boolean> isPositive = inputVal -> inputVal == null || inputVal <= 0;
	static Function<String, Boolean> isPorAorE = inputVal -> "".equals(inputVal) || !inputVal.equals("P") || !inputVal.equals("N")
			|| !inputVal.equals("E");


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
		} while (error || boolFunc.apply(value));

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
				logger.error("StringScan: " + value);
			} catch (InputMismatchException e) {
				error = true;
			}
		} while (error || boolFunc.apply(value));
		logger.error("Passed value: " + value);
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
		} while (error);

		return value;
	}
}
