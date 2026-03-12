import java.util.Scanner;

import dao.UserDAO;
import models.User;
import models.AdminUser;
import models.ClientUser;
import models.UpdateField;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) throws Exception {
        // User Management System in console
        while (true) {
            System.out.println("=== USER MANAGEMENT SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");

            System.out.print("[SYSTEM] Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                handleLogin();
            }
            else if (choice == 2) {
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

    private static void handleLogin() {
        System.out.println("=== LOGIN ===");
        System.out.print("[SYSTEM] Enter username: ");
        String username = scanner.nextLine();
        System.out.print("[SYSTEM] Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = userDAO.login(username, password);
        
        if (loggedInUser != null) {
            System.out.println("[SYSTEM] Login successful!");
            System.out.println("[SYSTEM] Welcome, " + loggedInUser.getUsername() + "!");

            // Authentication
            if (loggedInUser instanceof AdminUser) {
                AdminUser admin = (AdminUser) loggedInUser;
                showAdminMenu(admin);
            } else {
                ClientUser client = (ClientUser) loggedInUser;
                showClientMenu(client);
            }
        }

        else {
            System.out.println("[SYSTEM] Login failed. Please check your username and password.");
        }
    }

    private static void showAdminMenu(AdminUser admin) {
        boolean isLoggedOut = false;
        while (!isLoggedOut) {
            System.out.println("--- ADMIN MENU ---");
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
                    // View all client accounts
                    admin.getAllClients().forEach(System.out::println);
                    break;
                case 2:
                    // Create a new client account
                    System.out.print("[ADMIN] Enter client username: ");
                    String clientUsername = scanner.nextLine();
                    System.out.print("[ADMIN] Enter client password: ");
                    String clientPassword = scanner.nextLine();
                    System.out.print("[ADMIN] Enter client first name: ");
                    String clientFirstName = scanner.nextLine();
                    System.out.print("[ADMIN] Enter client last name: ");
                    String clientLastName = scanner.nextLine();
                    admin.createClient(clientUsername, clientPassword, clientFirstName, clientLastName);
                    break;
                case 3:
                    // Delete a client account
                    System.out.print("[ADMIN] Enter client username to delete: ");
                    String deleteUsername = scanner.nextLine();
                    admin.deleteClient(deleteUsername);
                    break;
                case 4:
                    // Update a client account
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

                    // If update username, after update we need to get the client by new username to show the updated info
                    boolean isUpdateUsername = field.equals("username");
                    String updateUsername = isUpdateUsername ? username : null;

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

                    System.out.println("[ADMIN] Client account after update: " 
                                + (isUpdateUsername ? 
                                    admin.getClient(updateUsername).toString() : 
                                    admin.getClient(username).toString()));
                    break;
                case 0:
                    // Logout
                    System.out.println("[SYSTEM] Logging out...");
                    isLoggedOut = true;
                    break;

                default:
                    break;
            }
        }
    }

    private static void showClientMenu(ClientUser client) {
        boolean isLoggedOut = false;
        while (!isLoggedOut) {
            System.out.println("--- CLIENT MENU ---");
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
                    userDAO.updateSpecificField(client.getUsername(), updateField, newValue);
                    break;
                case 0:
                    // Logout
                    System.out.println("[SYSTEM] Logging out...");
                    isLoggedOut = true;
                    break;
                default:
                    break;
            }
        }
    }

}
