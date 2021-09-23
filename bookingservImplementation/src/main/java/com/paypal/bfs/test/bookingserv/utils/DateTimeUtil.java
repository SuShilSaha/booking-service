package com.paypal.bfs.test.bookingserv.utils;

import com.paypal.bfs.test.bookingserv.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sushil Saha
 *
 */

public class DateTimeUtil {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	private DateTimeUtil() {	}

	public static Date getDate(String dateString) {
		if (StringUtils.isBlank(dateString)) {
			throw new ApplicationException("date attribute is empty");
		}

		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			throw new ApplicationException("dateTime format is not valid, use yyyy-MM-dd HH:mm:ss");
		}
	}

	public static String getDate(Date date) { return dateFormat.format(date); }

	public static String getDate(long uuid) { return dateFormat.format(uuid); }

}