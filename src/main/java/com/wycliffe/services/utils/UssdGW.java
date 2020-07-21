package com.wycliffe.services.utils;

import com.wycliffe.services.model.Account;
import com.wycliffe.services.viewModel.UserView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

public class UssdGW {
    Account account = new Account();
    UserView userview = new UserView();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    /*ussd configuration */
    String url = "https://dtsvc.safaricom.com:8480/api/public/ussd/ussdgw"; //gateway connection url
    String user = "wybosoft_apiuser"; // your mysql user
    String apiKey = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3eWJvc29mdF9hcGl1c2VyIiwiYXVkIjoiQUNDRVNTIiwic2Nv" +
            "cGVzIjoiQURNSU4iLCJpc3MiOiJodHRwOi8vc2l4ZGVlLmNvbSIsImlhdCI6MTU4ODk2NTU0NSwiZXhwIjoxNTg4OTY5MTQ1fQ." +
            "chiaFNfzEta6TVoxJwXyT01qS-jRB4cQnXq-tFESpuu04a_woCAAgvkM1VhjV-X8V42RhU1gPe16zR7kdYIpPQ";
    String token = account.getTOKEN();
    String message = userview.getMessage();
    String MSISDN = account.getMSISDN();
    Timestamp Session = timestamp;

    public String sendPush() {
        try {
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            String data = apiKey + Session + message + MSISDN;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            rd.close();
            URL url = new URL("http://wybosoftbank.com/ussd/transactions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error USSD push timeout " + e);
            return "Error " + e;
        }
    }


}






