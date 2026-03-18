package models;
public class AdminUser extends User {

    public AdminUser(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName, UserRole.ADMIN);
    }

    public AdminUser(String id, String username, String password, String firstName, String lastName) {
        super(id, username, password, firstName, lastName, UserRole.ADMIN);
    }

    public AdminUser(String id, String username, String password, String firstName, String lastName, UserRole role) {
        super(id, username, password, firstName, lastName, role);
    }
    
}
