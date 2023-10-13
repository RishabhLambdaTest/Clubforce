package com.clubforce.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailServerUtil {
    private static final Logger logger = LogManager.getLogger();

    public static String getForgotPasswordKeyFromMailtrapTEST() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441220/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
//        logger.info("Mails " +allMailtrapMails);
        String mailID = allMailtrapMails.substring(24, 34);

        logger.info("Getting the most recent using previous mail ID");
        logger.info("MailID " +mailID);
        String recoveryMailBody = HttpConnectionUtil.sendRequest("https://mailtrap.io/api/v1/inboxes/1441220/messages/"+mailID+"/body.txt",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        logger.info("recoveryMailBody " +recoveryMailBody);   // we see the mail pretty

        String tokenCode = "";
        Pattern regexPattern = Pattern.compile("Your verification code is \\w\\w\\w\\w\\w\\w");
        Matcher match = regexPattern.matcher(recoveryMailBody);
        if (match.find()) {
            tokenCode = match.group();
        }
        //trim and return
        return tokenCode.substring(26, 32);
    }

    public static String getForgotPasswordKeyFromMailtrapSBX() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441221/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
//        logger.info("Mails " +allMailtrapMails);
        String mailID = allMailtrapMails.substring(24, 34);

        logger.info("Getting the most recent using previous mail ID");
        logger.info("MailID " +mailID);
        String recoveryMailBody = HttpConnectionUtil.sendRequest("https://mailtrap.io/api/v1/inboxes/1441221/messages/"+mailID+"/body.txt",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        logger.info("recoveryMailBody " +recoveryMailBody);   // we see the mail pretty

        String tokenCode = "";
        Pattern regexPattern = Pattern.compile("Your verification code is \\w\\w\\w\\w\\w\\w");
        Matcher match = regexPattern.matcher(recoveryMailBody);
        if (match.find()) {
            tokenCode = match.group();
        }
        //trim and return
        return tokenCode.substring(26, 32);
    }

    public static String getActivateAccountCodeFromMailtrapTEST() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441220/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
//        logger.info("Mails " +allMailtrapMails);
        String mailID = allMailtrapMails.substring(24, 34);

        logger.info("Getting the most recent using previous mail ID");
        logger.info("MailID " +mailID);
        String recoveryMailBody = HttpConnectionUtil.sendRequest("https://mailtrap.io/api/v1/inboxes/1441220/messages/"+mailID+"/body.txt",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        logger.info("recoveryMailBody " +recoveryMailBody);   // we see the mail pretty

        String tokenCode = "";
        Pattern regexPattern = Pattern.compile("Your verification code is \\w\\w\\w\\w\\w\\w");
        Matcher match = regexPattern.matcher(recoveryMailBody);
        if (match.find()) {
            tokenCode = match.group();
        }
        //trim and return
        return tokenCode.substring(26, 32);
    }

    public static String getActivateAccountCodeFromMailtrapSBX() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441221/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
//        logger.info("Mails " +allMailtrapMails);
        String mailID = allMailtrapMails.substring(24, 34);

        logger.info("Getting the most recent using previous mail ID");
        logger.info("MailID " +mailID);
        String recoveryMailBody = HttpConnectionUtil.sendRequest("https://mailtrap.io/api/v1/inboxes/1441221/messages/"+mailID+"/body.txt",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        logger.info("recoveryMailBody " +recoveryMailBody);   // we see the mail pretty

        String tokenCode = "";
        Pattern regexPattern = Pattern.compile("Your verification code is \\w\\w\\w\\w\\w\\w");
        Matcher match = regexPattern.matcher(recoveryMailBody);
        if (match.find()) {
            tokenCode = match.group();
        }
        //trim and return
        return tokenCode.substring(26, 32);
    }

    public static String getMailTextFromMailtrapTEST() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441220/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
//        logger.info("Mails " +allMailtrapMails);
        String mailID = allMailtrapMails.substring(24, 34);
        logger.info("Getting the most recent using previous mail ID");
        logger.info("MailID " +mailID);
        String mailBody = HttpConnectionUtil.sendRequest("https://mailtrap.io/api/v1/inboxes/1441220/messages/"+mailID+"/body.txt",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        logger.info("recoveryMailBody " +mailBody);   // we see the mail pretty
        return mailBody;
    }

    public static String getMailTextFromMailtrapSBX() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441221/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
//        logger.info("Mails " +allMailtrapMails);
        String mailID = allMailtrapMails.substring(24, 34);

        logger.info("Getting the most recent using previous mail ID");
        logger.info("MailID " +mailID);
        String mailBody = HttpConnectionUtil.sendRequest("https://mailtrap.io/api/v1/inboxes/1441221/messages/"+mailID+"/body.txt",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        logger.info("recoveryMailBody " +mailBody);   // we see the mail pretty
        return mailBody;
    }

    public static String getAllMailsFromMailtrapTEST() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441220/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        return allMailtrapMails;
    }

    public static String getAllMailsFromMailtrapSBX() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441221/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        return allMailtrapMails;
    }

    public static String getForgotPasswordKeyFromTestMailApp() throws IOException {
        logger.info("Going to testmail app by HttpGet");

        String allMailsTestmailApp = HttpConnectionUtil.sendSimpleGETRequest(
                "https://api.testmail.app/api/json?apikey=2a835032-6507-4e9e-8da9-153c8a33d44f&namespace=wnzt4&pretty=true").toString();

        String code = StringUtils.substringBetween(allMailsTestmailApp, "Your verification code is ", "</p>");
        logger.info("code: " +code);
        return code;
    }

    public static String getDownloadAppMailFromMailtrap() {
        logger.info("Getting mailtrap all mails so we can get mail ID for most recent mail");
        String allMailtrapMails = HttpConnectionUtil.sendRequest(
                "https://mailtrap.io/api/v1/inboxes/1441220/messages?search=&page=1&last_id=",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
//        logger.info("Mails " +allMailtrapMails);
        String mailID = allMailtrapMails.substring(24, 34);

        logger.info("Getting the most recent using previous mail ID");
        logger.info("MailID " +mailID);
        String downloadAppMailBody = HttpConnectionUtil.sendRequest("https://mailtrap.io/api/v1/inboxes/1441220/messages/"+mailID+"/body.txt",
                "GET", "", "Api-Token", "5b496d04c4965412d9c42f4a85bd33cc").toString();
        return downloadAppMailBody;
    }

    public static String getMailMessageFromTestMail() throws Exception {
        logger.info("Going to TestMail app by HttpGet");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("https://api.testmail.app/api/json?apikey=2a835032-6507-4e9e-8da9-153c8a33d44f&namespace=wnzt4&pretty=true");
        HttpResponse httpresponse = httpclient.execute(httpget);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
            sb.append(sc.next());
        }
        String mailMessageWholeBody = sb.toString();
        System.out.println(mailMessageWholeBody);
        mailMessageWholeBody = mailMessageWholeBody.replaceAll("<[^>]*>", ""); //Removing the HTML tags
        System.out.println("Contents of the web page: "+mailMessageWholeBody);
        String mailMessageTrimmedBody = matchStandardMessage(mailMessageWholeBody);
        logger.info("Getting the message");
        logger.info("MessageTrimmedBody: " + mailMessageTrimmedBody);
        return mailMessageTrimmedBody;
    }

    public static String matchStandardMessage(String mailMessageBody) {
        Pattern regexPattern = Pattern.compile("Automation Standard Message Body CF Sports Club (TEST ONLY)");
        Matcher matcher = regexPattern.matcher(mailMessageBody);
        String mailMessageTrimmedBody = "";
        if (matcher.find()) {
            mailMessageTrimmedBody = matcher.group();
        }
        //trim and return
        return mailMessageTrimmedBody;
    }
}
