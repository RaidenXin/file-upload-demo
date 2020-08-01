package com.raiden.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 除了静态解析方法，其余方法都有可能返回空
 */
public final class LocalDateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd'T'HH:mm:ss",
            YYYY_MM_DD,
            "yyyyMMdd",
            YYYY_MM_DD_HH_MM_SS,
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "HH:mm",
            "yyyy-MM-dd'T'HH:mm:ss+08:00",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd'T'HH:mm",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy.MM.dd",
            "yyyy年MM月dd日"
    };

    private static final String ZERO = "0";

    public static final String GET_TIME = "LocalDateUtils.getTime";

    private LocalDateUtils() {
    }

    public static Date parseDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return DateUtils.parseDate(date, PARSE_PATTERNS);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static LocalTime parseLocalTime(String date) {
        LocalDateTime localDateTime = parseLocalDateTime(date);
        if (null == localDateTime) {
            return null;
        }
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
    }


    public static LocalDateTime parseLocalDateTime(String date) {
        Date time = parseDate(date);
        return parseLocalDateTime(time);
    }

    public static LocalDateTime parseLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime;
    }

    public static boolean isValid(String endDate) {
        LocalDateTime localEndDate = parseLocalDateTime(endDate);
        return localEndDate == null || localEndDate.compareTo(LocalDateTime.now()) >= 0;
    }

    public static LocalDate parseLocalDate(String date) {
        if(StringUtils.isNotBlank(date)){
            date = date.trim();
        }
        LocalDateTime localDateTime = parseLocalDateTime(date);
        return null == localDateTime ? null : localDateTime.toLocalDate();
    }

    public static LocalDate parseLocalDate(Date date) {
        LocalDateTime localDateTime = parseLocalDateTime(date);
        return null == localDateTime ? null : localDateTime.toLocalDate();
    }


    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }

    public static String format(LocalDate localDate, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDate);
    }

    public static String format(LocalDateTime localDateTime, String pattern, String defaultValue) {
        if (null == localDateTime) {
            return defaultValue;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }


    public static String format(LocalDate localDate, String pattern, String defaultValue) {
        if (null == localDate) {
            return defaultValue;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDate);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String format(Date date, String pattern, String defaultValue) {
        if (null == date) {
            return defaultValue;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String format(LocalTime time, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return time.format(df);
    }

    public static String getTime(String date){
        if (StringUtils.isBlank(date)){
            return ZERO;
        }
        Date time = parseDate(date);
        if (time == null){
            return ZERO;
        }
        return Long.toString(time.getTime() / 1000L);
    }
}
