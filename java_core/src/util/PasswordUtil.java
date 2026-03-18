package util;

public class PasswordUtil {
    public static String hashPassword(String password) {
        return EncodingUtil.sha256Encoding(password);
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashedInput = EncodingUtil.sha256Encoding(password);
        return hashedInput != null && hashedInput.equals(hashedPassword);
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        return true;
        // TODO: Password must contain special char, number
    }

    public static void main(String[] args) {
        System.out.println(hashPassword("admin123"));
    }
}
