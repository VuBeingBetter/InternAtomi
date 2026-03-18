package view;

import java.util.Scanner;

import dao.ClientDAO;
import dao.implement.ClientDAOImpl;
import models.ClientUser;
import util.ConsoleUtil;
import util.PasswordUtil;

public class ClientView {
    private ClientUser client;
    private static ClientDAO clientDAO = new ClientDAOImpl();
    private static Scanner scanner = new Scanner(System.in);
    
    public ClientView (ClientUser client) {
        this.client = client;
    }

    public void displayClientView() {
        boolean isRunning = true;
        while (isRunning) {
            ConsoleUtil.clearConsole();
            System.out.println(String.format("=== CLIENT DASHBOARD: %s ===", client.getUsername()));
            System.out.println("1. View profile");
            System.out.println("2. Update profile");
            System.out.println("3. Change password");
            System.out.println("0. Logout");
            System.out.print("[CLIENT] Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // View profile
                    System.out.println(client.toString());
                    ConsoleUtil.enter();
                    break;

                case 2:
                    // Update profile
                    handleProfileUpdate();
                    break;
                case 3:
                    // Change password
                    handleChangePassword();
                    break;
                case 0:
                    // Logout
                    System.out.println("[SYSTEM] Logging out...");
                    isRunning = false;
                    break;
                default:
                    break;
            }

        }
    }

    private void handleProfileUpdate() {
        ConsoleUtil.clearConsole();
        System.out.println("--- PROFILE UPDATE (leave the field blank if keep) ---");

        System.out.print("[CLIENT] First Name [" + client.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        if (!firstName.isBlank()) client.setFirstName(firstName);

        System.out.print("[CLIENT] Last Name [" + client.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        if (!lastName.isBlank()) client.setLastName(lastName);
        
        boolean success = clientDAO.updateProfile(client.getUsername(), client);
        if (success) {
            System.out.println("[CLIENT] Client profile updated.");
        } else {
            System.out.println("[CLIENT] Cannot update client profile!");
        }

        ConsoleUtil.enter();
    }

    private void handleChangePassword() {
        ConsoleUtil.clearConsole();
        System.out.println("--- CLIENT PASSWORD CHANGE ---");

        // Admin can change password of client
        System.out.print("[CLIENT] Enter old password: ");
        String oldPassword = scanner.nextLine();
        if (!PasswordUtil.verifyPassword(oldPassword, client.getPassword())) {
            System.out.println("[CLIENT] Old password is incorrect.");
            return;
        }
        System.out.print("[CLIENT] Enter new password: ");
        String newPassword = scanner.nextLine();
        while (!PasswordUtil.isValidPassword(newPassword)) {
            System.out.println("[CLIENT] Invalid password. Password needs to be at least 8 characters long.");
            System.out.print("[CLIENT] Enter new password: ");
            newPassword = scanner.nextLine();
        }
        
        client.setPassword(PasswordUtil.hashPassword(newPassword));

        boolean success = clientDAO.updateProfile(client.getUsername(), client); 
        if (success) {
            System.out.println("[ADMIN] Client profile updated.");
        } else {
            System.out.println("[ADMIN] Cannot update client profile!");
        }
        ConsoleUtil.enter();
    }
}
