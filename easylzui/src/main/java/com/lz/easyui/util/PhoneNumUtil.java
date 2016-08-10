package com.lz.easyui.util;

public class PhoneNumUtil {

    public static boolean isPhoneNum(String phoneNum){
        return phoneNum.matches("^1\\d{10}$") || phoneNum.matches("^+861\\d{10}$") || phoneNum.matches("^861\\d{10}$");
    }
}
