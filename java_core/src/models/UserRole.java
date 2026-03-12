package models;

public enum UserRole {
    ADMIN("ADMIN"),
    CLIENT("CLIENT");

    private String description;

    private UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
