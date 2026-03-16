package util;

import java.security.MessageDigest;
import java.util.Scanner;

public class ConsoleUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static void enter() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("[SYSTEM] Unable to clear console.");
        }
    }

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
