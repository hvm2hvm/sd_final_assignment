package services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Voicu Hodrea
 * @Date: 28.05.2017
 */
public class Tools {
    public static String byteArrayToHex(byte[] data) {
        String result[] = new String[data.length];
        for (int i=0; i<data.length; i++) {
            result[i] = String.format("%02X", data[i]);
        }
        return String.join("", result);
    }

    public static String hashString(String s, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(s.getBytes("UTF-8"));
            byte[] hash = digest.digest();
            return byteArrayToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String hashString(String s) {
        return hashString(s, "SHA-256");
    }
}
