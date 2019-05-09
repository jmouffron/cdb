package com.excilys.cdb.core.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.Optional;

public class DateUtils {
	private static DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private DateUtils() {}

	public static Timestamp stringToTimestamp(String toBeParsed) {
		if (toBeParsed == null || toBeParsed.isEmpty() ) {
			throw new RuntimeException("Null String !");
		}
		return Timestamp.valueOf(toBeParsed);
	}

	public static String stringToDateString(Timestamp toBeParsed) {
		return toBeParsed.toLocalDateTime().format(dateFormatter);
	}

	public static Timestamp getNowTimestamp() {
		LocalDateTime dateTime = LocalDateTime.now();

		String textDate = dateTime.format(timestampFormatter);

		LocalDateTime now = LocalDateTime.parse(textDate, timestampFormatter);

		return Timestamp.valueOf(now);
	}

	public static Timestamp getAfterNowTimestamp(TemporalAmount amountToAdd) {
		LocalDateTime dateTime = LocalDateTime.now().plus(amountToAdd);

		String textDate = dateTime.format(timestampFormatter);

		LocalDateTime now = LocalDateTime.parse(textDate, timestampFormatter);

		return Timestamp.valueOf(now);
	}

	public static Timestamp getBeforeNowTimestamp(TemporalAmount amountToTake) {
		LocalDateTime dateTime = LocalDateTime.now().minus(amountToTake);

		String textDate = dateTime.format(timestampFormatter);

		LocalDateTime now = LocalDateTime.parse(textDate, timestampFormatter);

		return Timestamp.valueOf(now);
	}

	public static Date timestampToDate(String timeString) {
		if( "".equals(timeString)) {
			throw new RuntimeException("Null input string for timestamp to date");
		}
		Optional<Instant> instant = Optional.ofNullable(stringToTimestamp(timeString).toInstant());
		if (instant.isPresent()) {
			return Date.from(instant.get());
		} else {
			throw new RuntimeException("Bad Input for timestamp to date");
		}
	}
}
