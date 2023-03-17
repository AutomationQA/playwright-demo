package io.github.lawrencez.boilerplate.utils.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JDate {
	/**
	 * Utility for date time functions
	 */
	private static final String DEFAULT_DAY_WITH_TIME_FORMAT = "MM/dd/yyyy HH:mm";

	// get TODAY with TIME as STRING
	public static String getDateWithTime() {
		return getDateWithTime(0, 0, 0);
	}

	public static String getDateWithTime(String format) {
		return getDateWithTime(0, 0, 0, format);
	}

	public static String getDateWithTime(int addDays, int addHours, int addMinutes) {
		return getDateWithTime(addDays, addHours, addMinutes, DEFAULT_DAY_WITH_TIME_FORMAT);
	}

	public static String getPastDateWithTime(int minusDays) {
		return getPastDateWithTime(minusDays, 0, 0, DEFAULT_DAY_WITH_TIME_FORMAT);
	}

	public static String getPastDateWithTime(int minusDays, String format) {
		return getPastDateWithTime(minusDays, 0, 0, format);
	}

	public static String getDateWithTime(int addDays, int addHours, int addMinutes, String format) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
		LocalDateTime today = LocalDateTime.now();
		today = today.plusDays(addDays);
		today = today.plusHours(addHours);
		today = today.plusMinutes(addMinutes);
		return today.format(fmt);
	}

	public static String getPastDateWithTime(int minusDays, int minusHours, int minusMinutes, String format) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
		LocalDateTime today = LocalDateTime.now();
		today = today.minusDays(minusDays);
		today = today.minusHours(minusHours);
		today = today.minusMinutes(minusMinutes);
		return today.format(fmt);
	}
}