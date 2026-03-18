package util;

import java.security.MessageDigest;

public class EncodingUtil {
    public static String bytesToHex(byte[] hash) {
        // This function is to convert hash string to hex string
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String sha256Encoding(String password) {
        try {
            // Hashing
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            // Convert hash to hex
            return bytesToHex(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
