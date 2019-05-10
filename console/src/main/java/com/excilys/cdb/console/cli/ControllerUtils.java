package com.excilys.cdb.console.cli;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerUtils {
	public static final Scanner scan = new Scanner(System.in);
	private static final Logger logger = LoggerFactory.getLogger(ControllerUtils.class);

	private ControllerUtils() {
	}

	static LongPredicate isStrictlyPositive = inputVal -> inputVal < 0 ;
	static Predicate<String> isNotNullString = inputVal -> inputVal.equals("") || inputVal == null;
	static LongPredicate isPositive = inputVal -> inputVal <= 0;
	static Predicate<String> isPorAorE = inputVal -> !inputVal.equals("P") || !inputVal.equals("A")
			|| !inputVal.equals("E") || !inputVal.equals("") || inputVal == null;

	public static long getLongInput(String message, LongPredicate boolFunc) {
		Long value = null;
		boolean error;

		do {
			error = false;
			logger.info(message);
			try {
				value = scan.nextLong();
			} catch (InputMismatchException|NumberFormatException e) {
				logger.error(e.getMessage());
				error = true;
			}
		} while (error || boolFunc.test(value));

		return value;
	}

	public static String getStringInput(String message, Predicate<String> boolFunc) {
		String value = "";
		boolean error;

		do {
			
			error = false;
			logger.info(message);
			try {
				value = scan.next();
			} catch (InputMismatchException e) {
				logger.error(e.getMessage());
				error = true;
				scan.reset();
			}
		} while (error || boolFunc.test(value));

		return value;
	}

	public static Timestamp getTimestampInput(String message) {
		Timestamp value = null;
		boolean error;

		do {
			
			error = false;
			logger.info(message);
			try {
				value = Timestamp.valueOf(scan.next() + " 00:00:00");
			} catch (InputMismatchException | IllegalArgumentException e) {
				logger.error(e.getMessage());
				error = true;
				scan.reset();
			}
			
		} while (error);

		return value;
	}
}