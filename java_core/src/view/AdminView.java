package view;

import java.util.List;
import java.util.Scanner;

import models.AdminUser;
import models.ClientUser;
import models.UpdateField;
import util.ConsoleUtil;

public class AdminView {
    private AdminUser admin;
    private Scanner scanner = new Scanner(System.in);

    public AdminView (AdminUser admin) {
        this.admin = admin;
    }

    public void displayAdminView() {
        boolean isRunning = true;
        
        while (isRunning) {
            ConsoleUtil.clearConsole();
            System.out.println(String.format("--- ADMIN DASHBOARD: %s ---", admin.getUsername()));
            System.out.println("1. View all client accounts");
            System.out.println("2. Create a new client account");
            System.out.println("3. Delete a client account");
            System.out.println("4. Update a client account");
            System.out.println("0. Logout");
            System.out.print("[ADMIN] Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    viewAllClients();
                    ConsoleUtil.enter();
                    break;

                case 2:
                    createNewClient();
                    ConsoleUtil.enter();
                    break;

                case 3:
                    deleteClient();
                    ConsoleUtil.enter();
                    break;

                case 4:
                    updateClient();     
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

    private void viewAllClients() {
        // Case 1: View all client accounts
        List<ClientUser> clients = admin.getAllClients();
        if (clients != null) {
            clients.forEach(System.out::println);
        } else {
            System.out.println("No clients found.");
        }
    }

    private void createNewClient() {
        // Case 2: Create a new client account
        System.out.print("[ADMIN] Enter client username: ");
        String clientUsername = scanner.nextLine();
        System.out.print("[ADMIN] Enter client password: ");
        String clientPassword = scanner.nextLine();
        System.out.print("[ADMIN] Enter client first name: ");
        String clientFirstName = scanner.nextLine();
        System.out.print("[ADMIN] Enter client last name: ");
        String clientLastName = scanner.nextLine();
        admin.createClient(clientUsername, clientPassword, clientFirstName, clientLastName);

        System.out.println("[ADMIN] Client account created.");
    }

    private void deleteClient() {
        // Case 3: Delete a client account
        System.out.print("[ADMIN] Enter client username to delete: ");
        String deleteUsername = scanner.nextLine();
        System.out.print("Are you sure you want to delete client " + deleteUsername + "? (Y/n):");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("[ADMIN] Client deletion cancelled.");
            return;
        }
        admin.deleteClient(deleteUsername);
        System.out.println("[ADMIN] Client account deleted.");
        ConsoleUtil.enter();
    }

    private void updateClient() {
        // Case 4: Update a client account
        System.out.print("[ADMIN] Enter client username to update: ");
        String username = scanner.nextLine();
        ClientUser client = admin.getClient(username);
        if (client != null) {
            System.out.println(client.toString());
        } else {
            System.out.println("[ADMIN] Client not found.");
        }
        System.out.print("[ADMIN] Enter field to update, case-sensitive (username, password, firstName, lastName): ");
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

        System.out.print("[ADMIN] Enter new value: ");
        String newValue = scanner.nextLine();

        admin.updateClientField(username, updateField, newValue);

        // Determine the newly updated client
        String queryUsername = (updateField == UpdateField.USERNAME) ? newValue : username;
        
        ClientUser updatedClient = admin.getClient(queryUsername);
        if (updatedClient != null) {
            System.out.println("[ADMIN] Client updated:");
            System.out.println(updatedClient.toString());
        } else {
            System.out.println("[ERROR] Could not retrieve information");
        }
                               
    }
}
