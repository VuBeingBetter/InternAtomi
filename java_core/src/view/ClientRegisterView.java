package view;

import java.util.Scanner;

import dao.ClientDAO;
import dao.implement.ClientDAOImpl;
import models.ClientUser;
import util.ConsoleUtil;
import util.PasswordUtil;

public class ClientRegisterView {
    // Client Register View
    private static ClientDAO clientDAO = new ClientDAOImpl();
    private static Scanner scanner = new Scanner(System.in);

    public void displayClientRegisterView() {
        ConsoleUtil.clearConsole();
        System.out.println("=== CLIENT REGISTER ===");
        System.out.print("[SYSTEM] Enter username: ");
        String username = scanner.nextLine();
        System.out.print("[SYSTEM] Enter password: ");
        String password = scanner.nextLine();
        System.out.print("[SYSTEM] Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("[SYSTEM] Enter last name: ");
        String lastName = scanner.nextLine();

        if (!clientDAO.existsByUsername(username)) {
            while (!PasswordUtil.isValidPassword(password)) {
                System.out.println("[SYSTEM] Password must be at least 8 characters long.");
                System.out.print("[SYSTEM] Enter password: ");
                password = scanner.nextLine();
            }

            password = PasswordUtil.hashPassword(password);
            clientDAO.register(new ClientUser(username, password, firstName, lastName));

            System.out.println("[SYSTEM] Client account created.");
            ConsoleUtil.enter();
        } else {
            System.out.println("[SYSTEM] Client account already exists.");
            ConsoleUtil.enter();
        }
    }
}
