package view;

import java.util.Map;

import dao.AdminDAO;
import dao.implement.AdminDAOImpl;
import models.AdminUser;
import util.ConsoleUtil;

public class AdminLoginView extends LoginView {
    private static AdminDAO adminDAO = new AdminDAOImpl();
    public AdminLoginView() {
        super();
    }
    
    public void displayAdminLoginView() {
        ConsoleUtil.clearConsole();
        System.out.println("=== ADMIN LOGIN ===");;
        Map.Entry<String, String> credentials = super.displayLoginView();
        String username = credentials.getKey();
        String password = credentials.getValue();

        AdminUser admin = (AdminUser) adminDAO.login(username, password);

        if (admin != null) {
            System.out.println("[SYSTEM] Login successful...");
            AdminView adminView = new AdminView(admin);
            adminView.displayAdminView();
        }
        else {
            System.out.println("[SYSTEM] Login failed. Invalid username or password.");
        }
        
    }
}
