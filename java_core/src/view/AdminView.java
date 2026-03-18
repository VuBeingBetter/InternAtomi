package view;

import java.util.List;
import java.util.Scanner;

import dao.AdminDAO;
import dao.implement.AdminDAOImpl;
import models.AdminUser;
import models.ClientUser;
import util.ConsoleUtil;
import util.PasswordUtil;

public class AdminView {
    private static AdminDAO adminDAO = new AdminDAOImpl();
    private static Scanner scanner = new Scanner(System.in);

    private AdminUser admin;

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
                    updateClientProfile();     
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
        List<ClientUser> clients = adminDAO.getAllClients();
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

        adminDAO.createClient(new ClientUser(clientUsername, clientPassword, clientFirstName, clientLastName));

        System.out.println("[ADMIN] Client account created.");
    }

    private void deleteClient() {
        // Case 3: Delete a client account
        System.out.print("[ADMIN] Enter client username to delete: ");
        String deleteUsername = scanner.nextLine();

        if (!adminDAO.existsByUsername(deleteUsername)) {
            System.out.println("[ADMIN] Client not found.");
            return;
        }

        System.out.print("Are you sure you want to delete client " + deleteUsername + "? (Y/n):");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("[ADMIN] Client deletion cancelled.");
            return;
        }

        if (adminDAO.deleteClientByUsername(deleteUsername)) {
            System.out.println("[ADMIN] Client account deleted.");
        } else {
            System.out.println("[ADMIN] Cannot delete client account.");
        }
    }

    private void updateClientProfile() {
        // Case 4: Update a client account
        System.out.print("[ADMIN] Enter client username to update: ");
        String username = scanner.nextLine();

        if (!adminDAO.existsByUsername(username)) {
            System.out.println("[ADMIN] Client not found.");
            return;
        }

        ClientUser client = adminDAO.getClientByUsername(username);
        System.out.println("--- PROFILE UPDATE (leave the field blank if keep) ---");

        System.out.print("[ADMIN] First Name [" + client.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        if (!firstName.isBlank()) client.setFirstName(firstName);

        System.out.print("[ADMIN] Last Name [" + client.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        if (!lastName.isBlank()) client.setLastName(lastName);

        // Admin can change password of client
        System.out.print("[ADMIN] Enter old password: ");
        String oldPassword = scanner.nextLine();
        if (!PasswordUtil.verifyPassword(oldPassword, client.getPassword())) {
            System.out.println("[ADMIN] Old password is incorrect.");
            return;
        }
        System.out.print("[ADMIN] Enter new password: ");
        String newPassword = scanner.nextLine();
        if (!PasswordUtil.isValidPassword(newPassword)) {
            System.out.println("[ADMIN] Invalid password. Password not changed.");
        }
        else client.setPassword(PasswordUtil.hashPassword(newPassword));

        boolean success = adminDAO.updateClientProfile(username, client); 
        if (success) {
            System.out.println("[ADMIN] Client profile updated.");
        } else {
            System.out.println("[ADMIN] Cannot update client profile!");
        }
                               
    }
}
