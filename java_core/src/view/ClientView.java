package view;

import java.util.Scanner;

import dao.ClientDAO;
import dao.implement.ClientDAOImpl;
import models.ClientUser;
import models.UpdateField;
import util.ConsoleUtil;

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
                    System.out.print("[CLIENT] Enter field to update, case-sensitive (username, password, firstName, lastName): ");
                    String field = scanner.nextLine();
                    UpdateField updateField;
                    switch (field) {
                        case "username":
                            updateField = UpdateField.USERNAME;
                            break;
                        case "password":
                            updateField = UpdateField.PASSWORD;
                            break;
                        case "firstName":
                            updateField = UpdateField.FIRST_NAME;
                            break;
                        case "lastName":
                            updateField = UpdateField.LAST_NAME;
                            break;
                        default:
                            System.out.println("Invalid field.");
                            return;
                    }
                    System.out.print("[CLIENT] Enter new value: ");
                    String newValue = scanner.nextLine();
                    clientDAO.updateInfo(client.getUsername(), updateField, newValue);
                    System.out.println("[CLIENT] Profile updated.");
                    ConsoleUtil.enter();
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
}
