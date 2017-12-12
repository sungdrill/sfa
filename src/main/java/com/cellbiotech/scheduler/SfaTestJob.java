package com.cellbiotech.scheduler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SfaTestJob implements Job {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
		/*
         * Job Interface를 implements하여
         * execute 메소드에 로직을 넣는다.
         * */
        System.out.println("--------------------------job수행---------------------------");

        /*
        try {

        // TODO 기초재고용으로 만들기

            String url = "http://localhost:8080/exmallBatch";

            URL obj = new URL(url);

            HttpURLConnection con  = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            String timeStamp1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("[" + timeStamp1 +"] Server (" + url + "), Response Code : " + responseCode);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        */


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
//        cal.add(Calendar.DATE, 2);
//        cal.add(Calendar.MONTH, 2);

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = df.format(cal.getTime());
        System.err.println(strDate);
    }

}
