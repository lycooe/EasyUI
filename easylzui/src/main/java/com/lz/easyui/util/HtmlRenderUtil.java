package com.lz.easyui.util;

import android.net.MailTo;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import com.lz.easyui.EasyUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRenderUtil {

    public final static String ProtocolKey_ACTION_DIAL = "tel:";

    private final static Pattern patternTagTitle = Pattern.compile("(?ms)(.*?)(<.*?>)");
    private final static Pattern patternTelNo = Pattern.compile("((\\d{8,14})|(\\d{3,4}-)?\\d{7,8})|(13[0-9]{9})|(\\d{2,4}-\\d{2,4}-\\d{2,4})");
    private final static Pattern patternEmail = Pattern.compile("\\w+(\\.\\w+)*@(\\w)+((\\.\\w{2,3})+)");
    private final static Pattern patternURL = Pattern.compile("(?i)http[s]?://[\\w\\.\\-/:?=&%]+");


    public static String renderHtml(String bodyHtml) {

        if (bodyHtml == null) {
            return "";
        }
        bodyHtml = HtmlRenderUtil.renderUrl(bodyHtml);
        bodyHtml = HtmlRenderUtil.renderEmail(bodyHtml);
        bodyHtml = HtmlRenderUtil.renderTelephone(bodyHtml);
        return bodyHtml;
    }


    private static String renderTelephone(String bodyHtml) {
        StringBuffer bodyStringBuffer = new StringBuffer();

        Matcher matcherTelContent = patternTagTitle.matcher(bodyHtml);
        while (matcherTelContent.find()) {

            String processContent = matcherTelContent.group(1);
            String htmlContent = matcherTelContent.group(2);
            if (htmlContent.equalsIgnoreCase("</script>")
                    || htmlContent.equalsIgnoreCase("</a>")) {
                matcherTelContent.appendReplacement(bodyStringBuffer, processContent + htmlContent);
            } else {
                String telContentHasProcessed = makeTelNoHerf(processContent);
                matcherTelContent.appendReplacement(bodyStringBuffer, telContentHasProcessed + htmlContent);
            }
        }
        matcherTelContent.appendTail(bodyStringBuffer);
        return bodyStringBuffer.toString();
    }

    private static String renderEmail(String bodyHtml) {
        StringBuffer bodyStringBuffer = new StringBuffer();

        Matcher matcherEmailContent = patternTagTitle.matcher(bodyHtml);
        while (matcherEmailContent.find()) {

            String processContent = matcherEmailContent.group(1);
            String htmlContent = matcherEmailContent.group(2);

            if (htmlContent.equalsIgnoreCase("</script>")
                    || htmlContent.equalsIgnoreCase("</a>")) {
                matcherEmailContent.appendReplacement(bodyStringBuffer, processContent + htmlContent);
            } else {
                String emailContentHasProcessed = makeEmailHerf(processContent);
                matcherEmailContent.appendReplacement(bodyStringBuffer, emailContentHasProcessed + htmlContent);
            }


        }
        matcherEmailContent.appendTail(bodyStringBuffer);
        return bodyStringBuffer.toString();
    }

    private static String renderUrl(String bodyHtml) {
        StringBuffer bodyStringBuffer = new StringBuffer();

        Matcher matcherUrlContent = patternTagTitle.matcher(bodyHtml);
        while (matcherUrlContent.find()) {

            String processContent = matcherUrlContent.group(1);
            String htmlContent = matcherUrlContent.group(2);

            if (htmlContent.equalsIgnoreCase("</script>")
                    || htmlContent.equalsIgnoreCase("</a>")) {
                matcherUrlContent.appendReplacement(bodyStringBuffer, processContent + htmlContent);
            } else {
                String urlContentHasProcessed = makeUrlHerf(processContent);
                matcherUrlContent.appendReplacement(bodyStringBuffer, urlContentHasProcessed + htmlContent);
            }
        }
        matcherUrlContent.appendTail(bodyStringBuffer);
        return bodyStringBuffer.toString();
    }


    private static String makeTelNoHerf(String telContent) {
        if (telContent.trim().length() == 0) {
            return telContent;
        }
        StringBuffer telNoStringBuffer = new StringBuffer();

        Matcher matcherTelNo = patternTelNo.matcher(telContent);
        while (matcherTelNo.find()) {

            String telNo = matcherTelNo.group();
            String telNoToHerf = "<a href=\"" + ProtocolKey_ACTION_DIAL + telNo + "\">" + telNo + "</a>";
            matcherTelNo.appendReplacement(telNoStringBuffer, telNoToHerf);

        }
        matcherTelNo.appendTail(telNoStringBuffer);
        return telNoStringBuffer.toString();
    }


    private static String makeEmailHerf(String content) {
        if (content.trim().length() == 0) {
            return content;
        }
        StringBuffer emailStringBuffer = new StringBuffer();

        Matcher matcherEmail = patternEmail.matcher(content);
        while (matcherEmail.find()) {

            String email = matcherEmail.group();
//            System.out.println("email:" + email);
            String emailToHref = "<a href=\"" + MailTo.MAILTO_SCHEME + email + "\">" + email + "</a>";
            matcherEmail.appendReplacement(emailStringBuffer, emailToHref);

        }
        matcherEmail.appendTail(emailStringBuffer);
        return emailStringBuffer.toString();
    }


    private static String makeUrlHerf(String content) {
        if (content.trim().length() == 0) {
            return content;
        }
        StringBuffer urlStringBuffer = new StringBuffer();

        Matcher matcherUrl = patternURL.matcher(content);
        while (matcherUrl.find()) {

            String url = matcherUrl.group();
//            System.out.println("URL:" + url);
            String urlToHref = "<a href=\"" + url + "\">" + url + "</a>";
            matcherUrl.appendReplacement(urlStringBuffer, urlToHref);

        }
        matcherUrl.appendTail(urlStringBuffer);
        return urlStringBuffer.toString();
    }

    /**
     * 01061234567 010-61234567 13611111111 400-400-4000
     *
     * @param phoneNumStr phoneNum
     * @return boolean
     */
    public static boolean isPhoneNumber(String phoneNumStr) {
        if (phoneNumStr == null || phoneNumStr.trim().length() == 0) {
            return false;
        }
        Matcher matcherTelNo = patternTelNo.matcher(phoneNumStr);
        return matcherTelNo.find();
    }


    public static Spanned fromHtml(String str) {
        String inStr = str;

        TextView textView = new TextView(EasyUI.ctx);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setAutoLinkMask(Linkify.WEB_URLS);
        textView.setText(Html.fromHtml(inStr));

        CharSequence text = textView.getText();

        URLSpan[] urls = ((Spannable) text).getSpans(0, text.length(), URLSpan.class);
        for (URLSpan url : urls) {
            String itemUrl = url.getURL();
            if (itemUrl.contains("http://") || itemUrl.contains("https://") || itemUrl.contains("rtsp://")) {
                inStr = inStr.replace(itemUrl + " ", "<a href='" + itemUrl + "'>" + itemUrl + "</a> ");
            }
        }
        return Html.fromHtml(inStr);
    }
}
