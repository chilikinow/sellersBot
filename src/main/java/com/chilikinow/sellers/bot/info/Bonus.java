package com.chilikinow.sellers.bot.info;

import com.chilikinow.sellers.bot.settings.BotData;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Bonus {

    private String urlBase;
    private String bonusUserName;
    private String bonusPassword;
    private String tokenValue;
    private String tokenKey;

    private CloseableHttpClient httpСlient;
    private SSLConnectionSocketFactory socketFactory;
    private CookieStore httpCookieStore;

    {
        this.urlBase = BotData.bonusBaseURI;
        this.bonusUserName = BotData.bonusUserName;
        this.bonusPassword = BotData.bonusPassword;


        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Object trustManager = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certificate, String str) {
                    }
                    public void checkServerTrusted(X509Certificate[] certificate, String str) {
                    }
                }
        };
        try {
            context.init(null, (TrustManager[])trustManager, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        this.socketFactory = new SSLConnectionSocketFactory(context,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        this.httpCookieStore = new BasicCookieStore();

    }

    public String getInfoPhoneNumber(String phoneNumber){

        authorization();
        String cardNumber = searchCard(phoneNumber);
        String info = getInfo(cardNumber);

        return info;
    }

    public String getInfoCardNumber(String cardNumber){

        authorization();
        String info = getInfo(cardNumber);

        return info;
    }

    private void authorization() {

        this.httpСlient = HttpClientBuilder
                .create()
                .setSSLSocketFactory(this.socketFactory)
                .setDefaultCookieStore(this.httpCookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        HttpGet httpGetRequest = new HttpGet(this.urlBase + "/site/login");
        try {

            HttpResponse httpResponse = this.httpСlient.execute(httpGetRequest);

            String httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));
            Document document = Jsoup.parse(httpResponseString);
            Elements elements = document.select("input");

            this.tokenKey = "YII_CSRF_TOKEN";
            this.tokenValue = null;
            for (Element element : elements) {
                if (element.attr("name").equals(tokenKey)) {
                    this.tokenValue = element.attr("value");
                }
            }

            HttpPost httpPostRequest = new HttpPost(urlBase + "/site/login");
            StringEntity body = new StringEntity(this.tokenKey + "=" + this.tokenValue
                    + "&LoginForm[username]=" + this.bonusUserName
                    + "&LoginForm[password]=" + this.bonusPassword);
            httpPostRequest.setEntity(body);
            httpPostRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
            this.httpСlient.execute(httpPostRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String searchCard(String phoneNumber){

        String validPhoneNumber = "+7("
                + phoneNumber.substring(0, 3)
                + ")"
                + phoneNumber.substring(3, 6)
                + "-"
                + phoneNumber.substring(6);

        this.httpСlient = HttpClientBuilder
                .create()
                .setSSLSocketFactory(socketFactory)
                .setDefaultCookieStore(httpCookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        try {
            HttpPost httpPostRequest = new HttpPost(urlBase + "/card/search");
            httpPostRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");

            StringEntity body = new StringEntity(this.tokenKey + "=" + this.tokenValue
                    + "&CardSearchFormModel[phone]=" + validPhoneNumber + "&yt0=Найти");
            httpPostRequest.setEntity(body);
            HttpResponse httpResponse = this.httpСlient.execute(httpPostRequest);
            String httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));

            Document document = Jsoup.parse(httpResponseString);
            Elements elements = document.select("form");

            for (Element element : elements) {
                if (element.attr("target").equals("_blank")) {
                    validPhoneNumber = element.attr("id");
                }
            }
            validPhoneNumber = validPhoneNumber.replace("cardForm_", "");
        //Получили номер карты

        } catch (IOException e) {
            e.printStackTrace();
        }

        return validPhoneNumber;
    }

    private String getInfo(String validCardNumber){

        this.httpСlient = HttpClientBuilder
                .create()
                .setSSLSocketFactory(socketFactory)
                .setDefaultCookieStore(httpCookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        StringBuilder info = new StringBuilder();

        try {
            HttpPost httpPostRequest = new HttpPost(this.urlBase + "/card/info");
            httpPostRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");

            StringEntity body = new StringEntity(this.tokenKey + "=" + this.tokenValue
                    + "&CardInfoFormModel[search]=" + validCardNumber);
            httpPostRequest.setEntity(body);
            HttpResponse httpResponse = this.httpСlient.execute(httpPostRequest);
            String httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));
            Document document = Jsoup.parse(httpResponseString);
            Elements elements = document.select("label");

            int counterLine = 0;
            for (Element element : elements) {

                Pattern pattern = Pattern.compile(">(.+)</");
                Matcher matcher = pattern.matcher(element.toString());
                while (matcher.find()){
                    String matcherBuffer = matcher.group(1).trim();

                    if (matcherBuffer.startsWith("<")
                            || matcherBuffer.startsWith("Поиск")
                            || matcherBuffer.startsWith("Программа"))
                        continue;
                    info.append(matcher.group(1).trim() + " ");
                    counterLine++;
                    if (counterLine % 2 == 0)
                        info.append("\n");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return info.toString();
    }
}
