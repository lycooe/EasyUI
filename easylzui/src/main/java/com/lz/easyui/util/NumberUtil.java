package com.lz.easyui.util;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NumberUtil {

    private static final String TAG = NumberUtil.class.getSimpleName();

    public static BigDecimal long2Decimal(long money) {
        return BigDecimal.valueOf(money, 2);
    }

    public static Long decimal2Long(BigDecimal money) {
        return (money.multiply(BigDecimal.valueOf(100))).longValue();
    }

    public static String getMoneyYuanStr(long moneyFen) {
        return long2Decimal(moneyFen).toString();
    }

    public static Float getFloatFromFloatRoundHalfUp(float sourceNum, int scale) {
        BigDecimal bigDecimal = new BigDecimal(sourceNum);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static Float getDoubleFromDoubletRoundHalfUp(double sourceNum, int scale) {
        BigDecimal bigDecimal = new BigDecimal(sourceNum);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static Double getDoubleFromDoubletRoundHalfUp1(double sourceNum, int scale) {
        BigDecimal bigDecimal = new BigDecimal(sourceNum);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int convertFloatToInt(float sourceNum) {
        BigDecimal bigDecimal = new BigDecimal(sourceNum);
        return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static int convertDoubleToInt(double sourceNum) {
        BigDecimal bigDecimal = new BigDecimal(sourceNum);
        return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static int parseInt(String str, int def) {

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.d(TAG, "String to Int Error! Use def value! ");
        }
        return def;
    }

    public static long parseLong(String str, long def) {

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            Log.d(TAG, "String to Long Error! Use def value!");
        }
        return def;
    }

    public static float parseFloat(String str, float def) {

        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            Log.d(TAG, "String to Float Error! Use def value!");
        }
        return def;
    }

    public static Double parseDouble(String str, double def) {

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            Log.d(TAG, "String to Double Error! Use def value!");
        }
        return def;
    }

    /*
    * 格式化 数字
    * */
    public static String getToFormatNum(int num) {
        String numStr = "" + num;
        if (num < 10 && num != 0) {
            numStr = "0" + num;
        }
        return numStr;
    }

    public static int getRandom(int startNum, int endNum) {
        int num = (int) (Math.round(Math.random() * (endNum - startNum) + startNum));
        return num;
    }

    public static int[] getRandom(int startNum, int endNum, int needNum) {
        int nums[] = new int[needNum];
        Set<Integer> integerSet = new HashSet<Integer>();
        while (integerSet.size() < needNum){
            int num = (int) (Math.round(Math.random() * (endNum - startNum) + startNum));
            integerSet.add(num);
        }
        Object[] ints = integerSet.toArray();
        for (int i = 0; i < ints.length; i++) {
            nums[i] = Integer.parseInt(ints[i].toString());
        }
        return nums;
    }

    public static int[] getMachineSelectionNum(int size, int numbers[]) {
        int length = numbers.length;
        int num[] = new int[size];
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < size) {
            int number = numbers[random.nextInt(length)];
            int m = 0;
//            for (int hava_num : havaNum) {
//                if (hava_num == number) {
//                    m++;
//                }
//            }
            if (m == 0) {
                set.add(number);
            }
        }
        Object[] ints = set.toArray();
        for (int i = 0; i < ints.length; i++) {
            num[i] = Integer.parseInt(ints[i].toString());
        }
        return num;
    }

    public static int[] getMachineSelectionNumNo(int size, int numbers[]) {
        int length = numbers.length;
        int num[] = new int[size];
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();
        while (list.size() < size) {
            int number = numbers[random.nextInt(length)];
            list.add(number);
        }

        for (int i = 0; i < list.size(); i++) {
            num[i] = list.get(i);
        }
        return num;
    }

    public static int[] getUseNums(int allNums[], int havaNums[]) {
        List<Integer> integers = new ArrayList<Integer>();
        for (int allNum : allNums) {
            int n = 0;
            for (int havaNum : havaNums) {
                if (allNum == havaNum) {
                    n++;
                }
            }
            if (n == 0) {
                integers.add(allNum);
            }
        }
        int numbers[] = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            numbers[i] = integers.get(i);
        }
        return numbers;
    }

    public static int dip2px(float dpValue) {
        final float scale = DeviceUtil.getDensity();
        return (int) (dpValue * scale + 0.5f);
    }

    public static void main(String[] args) {
        System.out.println(getMoneyYuanStr(123456789));
        System.out.println(getMoneyYuanStr(0));
        System.out.println(getMoneyYuanStr(10));
        System.out.println(getMoneyYuanStr(000));
        System.out.println(getMoneyYuanStr(123));
        System.out.println(getMoneyYuanStr(1234));
        System.out.println(getMoneyYuanStr(1));
    }


}
