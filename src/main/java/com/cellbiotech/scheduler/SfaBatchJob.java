package com.cellbiotech.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SfaBatchJob implements Job {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("::::::::::::::::::::::::::: SfaBatchJob 실행 :::::::::::::::::::::::::::::::::");
        try {

            // 판매현황, 입고현황을 월별로 가져오기
//            String url = "http://localhost:8080/sfaBatch";
            String url = "http://www.cellbiotechint.com:9080/sfaBatch";

            URL obj = new URL(url);

            HttpURLConnection con  = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            String timeStamp1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("[" + timeStamp1 +"] Duolac Server (" + url + "), Response Code : " + responseCode);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
