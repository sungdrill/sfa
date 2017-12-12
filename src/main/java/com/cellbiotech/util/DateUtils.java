package com.cellbiotech.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 날짜 유틸 클래스
 */
public class DateUtils {

    /**
     * 년도 가져오기
     * @return
     */
    public static String getYearFilePath() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date();

        return formatter.format(date);

    }

    /**
     * 월 가져오기
     * @return
     */
    public static String getMonthFilePath() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        Date date = new Date();

        return formatter.format(date);

    }

    /**
     * 문자를 Date형식으로 치환
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date getStringtoDate(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        return formatter.parse(strDate.replace("/", ""));
    }

    /**
     * 문자를 Date형식으로 치환
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date getStringtoDate(String strDate, String sep) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        return formatter.parse(strDate.replace(sep, ""));
    }

    /**
     * 입력 날짜에 대한 마지막 일 구하기
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static String getEndDay(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date tdate = formatter.parse(strDate.replace("-", "").toString());

        Calendar cal = Calendar.getInstance();

        cal.setTime(tdate);

        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return String.valueOf(endDay);
    }
}
