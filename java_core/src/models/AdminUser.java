package models;

import java.util.List;

import dao.AdminDAO;
import dao.implement.AdminDAOImpl;

public class AdminUser extends User {
    private AdminDAO adminDAO = new AdminDAOImpl();

    public AdminUser(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName, UserRole.ADMIN);
    }

    public AdminUser(String _id, String username, String password, String firstName, String lastName) {
        super(_id, username, password, firstName, lastName, UserRole.ADMIN);
    }
    
    public void createClient(String username, String password, String firstName, String lastName) {
        if (adminDAO.isUserExist(username, null)) {
            System.out.println(String.format("The username %s already exists.", username));
            return;
        }
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }
        adminDAO.createClient(new ClientUser(username, password, firstName, lastName));
        System.out.println(String.format("Client %s created successfully.", username));
    }

    public ClientUser getClient(String clientUsername) {
        return adminDAO.getClient(clientUsername);
    }

    public List<ClientUser> getAllClients() {
        return adminDAO.getAllClients();
    }

    public void updateClientField(String clientUsername, UpdateField field, String newValue) {
        // Password must satisfy the requirement:
        // - At least 8 characters
        if (field == UpdateField.PASSWORD) {
            if (newValue == null || newValue.isEmpty()) {
                System.out.println("Password cannot be empty.");
                return;
            } else if (newValue.length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                return;
            }
        }
        adminDAO.updateSpecificField(clientUsername, field, newValue);
    }

    public boolean deleteClient(String clientUsername) {
        boolean isDeleted = adminDAO.deleteClientByUsername(clientUsername);
        if (isDeleted) {
            System.out.println(String.format("Client %s deleted successfully.", clientUsername));
        } else {
            System.out.println(String.format("Failed to delete client %s.", clientUsername));
        }
        return isDeleted;
    }

    
}
