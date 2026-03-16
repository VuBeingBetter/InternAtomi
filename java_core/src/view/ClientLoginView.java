package view;

import java.util.Map;

import dao.ClientDAO;
import dao.implement.ClientDAOImpl;
import models.ClientUser;
import util.ConsoleUtil;

public class ClientLoginView extends LoginView {
    private static ClientDAO clientDAO = new ClientDAOImpl();
    public ClientLoginView() {
        super();
    }

    public void displayClientLoginView() {
        ConsoleUtil.clearConsole();
        System.out.println("=== CLIENT LOGIN ===");
        LoginView loginView = new LoginView();
        Map.Entry<String, String> credentials = loginView.displayLoginView();
        String username = credentials.getKey();
        String password = credentials.getValue();

        ClientUser client = (ClientUser)clientDAO.login(username, password);

        if (client != null) {
            System.out.println("[SYSTEM] Login successful...");
            ClientView clientView = new ClientView(client);
            clientView.displayClientView();
        }
        else {
            System.out.println("[SYSTEM] Login failed. Invalid username or password.");
        }

    }
}
