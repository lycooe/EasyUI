package com.lz.easyui.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckTool {

    public static boolean isEmpty(String str){
        if(str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(List list){
        if(list == null || list.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Map map){
        if(map == null || map.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Object object){
        if(object == null)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Object object[]){
        if(object == null || object.length == 0)
            return true;
        else
            return false;
    }

    public static boolean isUserName_login(String strUserName) {
        String strPattern = "^(?!_)(?!.*?_$)([a-zA-Z0-9_]|[\u4E00-\u9FA5\uf900-\ufa2d])+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strUserName);
        return m.matches();
    }

    public static boolean isMobile(String strMoible) {
        String strPattern = "1[3,4,8,5]{1}+[0-9]{9}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strMoible);
        return m.matches();
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}
