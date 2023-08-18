//package com.cjt.consultant.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.http.ResponseEntity;
//
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.cert.X509Certificate;
//import java.util.Properties;
//
//
//class SignupControllerTest {
//
//    @Test
//    public void signup() throws Exception {
//
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.ssl.enable", "true");
//
//        TrustManager[] trustAllCerts = new TrustManager[] {
//                new X509TrustManager() {
//                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
//                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
//                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
//                }
//        };
//        // Install the custom TrustManager
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, trustAllCerts, new SecureRandom());
//
//        Session session = Session.getInstance(properties);
//        session.setDebug(true); // Enable debugging to see the SMTP communication in the console
//        Transport transport = session.getTransport("smtp");
//        transport.connect("smtp.gmail.com", "jasonthaaa@gmail.com", "tsgelrcngofthvih");
//
//    }
//
//}