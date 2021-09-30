package com.anhnc2.ehealicords.util;

import java.util.Random;

public class NumberUtil {
    private NumberUtil() {}

    static final Random rd = new Random(System.currentTimeMillis());

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+")) {
            return phoneNumber;
        } else if (phoneNumber.startsWith("0")) {
            return "+84" + phoneNumber.substring(1);
        } else {
            return "+84" + phoneNumber;
        }
    }

    public static String randomVerifyCode() {
        int code = 100000 + rd.nextInt(900000);
        return String.valueOf(code);
    }
}
