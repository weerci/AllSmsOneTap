package com.production.kriate.allsmsonetap.tools;

import android.util.Base64;

/**
 * Created by dima on 01.07.2017.
 */

public class Encryption {

    public static String decrypt(String message, String salt) {
        return xor(new String(Base64.decode(message, 0)), salt);
    }

    public static String encrypt(String message, String salt) {
        return new String(Base64.encode(xor(message, salt).getBytes(), 0));
    }

    private static String xor(String message, String salt) {
        final char[] m = message.toCharArray();
        final int ml = m.length;

        final char[] s = salt.toCharArray();
        final int sl = s.length;

        final char[] res = new char[ml];
        for (int i = 0; i < ml; i++) {
            res[i] = (char) (m[i] ^ s[i % sl]);
        }
        return new String(res);
    }

}
