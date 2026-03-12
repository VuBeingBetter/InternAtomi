package models;

public enum UpdateField {
    USERNAME, PASSWORD, FIRST_NAME, LAST_NAME;

    public String getFieldNameSQL() {
        switch (this) {
            case USERNAME:
                return "username";
            case PASSWORD:
                return "password";
            case FIRST_NAME:
                return "firstName";
            case LAST_NAME:
                return "lastName";
            default:
                throw new IllegalArgumentException("Invalid field: " + this);
        }
    }

    
}
