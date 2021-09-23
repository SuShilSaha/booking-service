package com.paypal.bfs.test.bookingserv.utils;

import com.paypal.bfs.test.bookingserv.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author Sushil Saha
 *
 */

public class DateUtil {

    private final static SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

    private DateUtil() {	}

    public static Date stringToDate(String stringDate){
        if (StringUtils.isBlank(stringDate)) {
            throw new ApplicationException("date attribute is empty");
        }
        try {
            return sdf.parse(stringDate.trim());
        } catch (ParseException e) {
            throw new ApplicationException("date format is not valid, use yyyy-MM-dd");
        }
    }

    public static String getStringFromDate(Date date) {
        if (Objects.isNull(date)) {
            throw new ApplicationException("date attribute is empty");
        }

        return sdf.format(date);
    }

    public static String getDate(long uuid) { return sdf.format(uuid); }

    public static void main(String[] args) {
        System.out.println(stringToDate("2020-01-29"));
    }
}