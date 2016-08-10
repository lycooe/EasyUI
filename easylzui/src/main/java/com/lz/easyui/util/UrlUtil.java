package com.lz.easyui.util;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

//    private static String regex = "((http://)?([a-z]+[.])|(www.))\\w+[.]([a-z]{2,4})?[[.]([a-z]{2,4})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?)";
//    private static String regex = "(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)";
    private static String regex = "http://[\\w\\.\\-/:]+";
    private static String A1 = " <a href={0}>";
    private static String A2 = " </a>";
    public static String toHref(String title) {
        StringBuffer sb = new StringBuffer(title);
        Pattern pat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher mat = pat.matcher(title);
        int index = 0;
        int index1 = 0;
        while (mat.find()) {
            String url = mat.group();
            //System.out.println(url);
            if (url.indexOf("http://") != 0)
                url = "http://" + url;
            Object obj[] = { "'" + url + "'" };
            String a = MessageFormat.format(A1, obj);
            int l = a.length();
            index += index1;
            sb.insert(mat.start() + index, a);
            index += l;
            sb.insert((mat.end()) + index, A2);
            index1 = A2.length();
        }
        return sb.toString();
    }

    public static String getUrlFileName(String url) {

        if(url==null){
            return null;
        }

        if (!isURL(url)) {
            return null;
        }

        if (!isFileUrl(url)) {
            return null;
        }

        String[] urlArr = url.split("/");
        return urlArr[urlArr.length - 1];
    }


    public static boolean isFileUrl(String url) {

        int lastIndex = url.lastIndexOf("/");
        if (lastIndex < url.length()-1) {
            return true;
        }
        return false;
    }

    public static boolean isURL(String url) {

        if (url.startsWith("http://") || url.startsWith("https://")) {
            return true;
        }

        return false;
    }
}
