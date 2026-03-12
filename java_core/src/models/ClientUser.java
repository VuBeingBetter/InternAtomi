package models;

public class ClientUser extends User {
    public ClientUser(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName, UserRole.CLIENT);
    }
    
    public ClientUser(String _id, String username, String password, String firstName, String lastName) {
        super(_id, username, password, firstName, lastName, UserRole.CLIENT);
    }
}
