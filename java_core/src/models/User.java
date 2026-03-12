package models;

import java.util.UUID;

public abstract class User {
    protected String _id;
    protected String username, password;
    protected String firstName, lastName;
    protected UserRole role;

    public User(String username, String password, String firstName, String lastName, UserRole role) {
        this._id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String _id, String username, String password, String firstName, String lastName, UserRole role) {
        // Khi get user từ database, phải truyền lại _id nếu không constructor trên sẽ tự gen 1 _id mới
        this._id = _id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String get_id() {
        return _id;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | [%s] | Username: %s | Name: %s %s", _id, role.getDescription(), username, firstName, lastName);
    }


}
