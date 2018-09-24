package com.vankien96.mooview.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 12/16/17.
 */

public final class DateTimeUtils {

    private DateTimeUtils() {
        //No-op
    }

    public static String getStrDateTimeFormatted(Date soure, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(soure);
    }

    public static Date convertStringToDate(String date, String format) {
        SimpleDateFormat parser = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
