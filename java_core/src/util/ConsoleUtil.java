package util;

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
}
