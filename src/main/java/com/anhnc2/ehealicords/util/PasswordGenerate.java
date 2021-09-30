package com.anhnc2.ehealicords.util;

import java.security.SecureRandom;

public class PasswordGenerate {
    private PasswordGenerate() {}

    public static String random() {
        int maxSize = 8;
        int randomNumberOrigin = 48;
        int randomNumberBound = 123;
        SecureRandom random = new SecureRandom();

        return random.ints(randomNumberOrigin, randomNumberBound)
                .filter(i -> Character.isAlphabetic(i) || Character.isDigit(i))
                .limit(maxSize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}
