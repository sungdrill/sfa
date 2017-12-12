package com.cellbiotech.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 공통유티 클래스
 */
public class CommonUtils {
	public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 파리미터를 문자 0 값으로 치환
     * @param param
     * @return
     */
    public static String null2zero(Object param) {
        String str = new String();

        if (param == null) {
            return "0";
        }

        if (param instanceof String) {
            str = (String) param;
        } else if (param instanceof String[]) {
            str = ((String[]) param)[0];
        } else if (param instanceof Date) {
            str = ((Date)param).toString();
        } else {
            str = String.valueOf(param);
        }

        if (str.equals("")) {
            return "0";
        } else {
            return str.trim();
        }
    }

    /**
     * 파라미터에 대한 값을 천단위로 콤마 표시
     * @param junsu
     * @return
     */
    public static String Comma_won(String junsu) {
        String[] junsuArray = junsu.split("\\.");
        int inValues = 0;
        String result_int = "";
        if (junsuArray.length > 1) {
            inValues = Integer.parseInt(junsuArray[0]);
            DecimalFormat Commas = new DecimalFormat("#,###");
            result_int = (String)Commas.format(inValues);
            String decimalValue = junsuArray[1].replaceAll("0","");
            if (decimalValue.length() > 0) {
                result_int += "."+decimalValue;
            }
        } else {
            inValues = Integer.parseInt(junsu);
            DecimalFormat Commas = new DecimalFormat("#,###");
            result_int = (String)Commas.format(inValues);
        }

        return result_int;
    }


    public static String getDecimal2String(String val, int index) {
        String rtn = "";
        if (val != null) {
            rtn = val.substring(0, val.length()-index);
            if (rtn.equals("0")) {
                rtn = "";
            } else {

            }
        } else {

        }
        return rtn;
    }

}
