import java.util.Scanner;

import models.UserRole;
import util.ConsoleUtil;
import view.AdminLoginView;
import view.ClientLoginView;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // User Management System in console
        while (true) {
            ConsoleUtil.clearConsole();
            System.out.println("=== USER MANAGEMENT SYSTEM ===");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Client");
            System.out.println("3. Client Register");
            System.out.println("0. Exit");

            System.out.print("[SYSTEM] Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                handleLogin(UserRole.ADMIN);
            }
            else if (choice == 2) {
                handleLogin(UserRole.CLIENT);
            }
            else if (choice == 3) {
                System.out.println("[SYSTEM] Under development...");
            }
            else if (choice == 0) {
                System.out.println("[SYSTEM] Exiting...");
                break;
            }
            else {
                System.out.println("[SYSTEM] Invalid choice. Please try again.");
            }
        }
    }

    private static void handleLogin(UserRole role) {

        if (role == UserRole.ADMIN) {
            AdminLoginView adminLoginView = new AdminLoginView();
            adminLoginView.displayAdminLoginView();
        }
        else if (role == UserRole.CLIENT) {
            ClientLoginView clientLoginView = new ClientLoginView();
            clientLoginView.displayClientLoginView();
        }
    }

}
