package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnection;
import models.AdminUser;
import models.ClientUser;
import models.UpdateField;
import models.User;
import models.UserRole;

public class UserDAO {

    protected User getUserByUsername(String username, UserRole role) {
        // Code to retrieve a user with the given username from the database
        String sql = "SELECT * FROM users WHERE username = ?";
        if (role != null) {
            sql += " AND role = ?";
        }
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            if (role != null) {
                pstmt.setString(2, role.toString());
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(String.format("[SUCCESS] User %s found in database.", username));
                // Get the role from database
                UserRole userRole = UserRole.valueOf(rs.getString("role"));
                if (userRole == UserRole.ADMIN) {
                    return new AdminUser(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                    );
                } else if (userRole == UserRole.CLIENT) {
                    return new ClientUser(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                    );
                }
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserExist(String username, UserRole role) {
        // Code to check if a user with the given username exists in the database
        return getUserByUsername(username, role) != null;    
    }

    public void updateSpecificField(String username, UpdateField field, String newValue) {
        // Code to update a specific field of a user in the database
        String sql = "UPDATE users SET " + field.getFieldNameSQL() + " = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newValue);
            pstmt.setString(2, username);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println(String.format("[SUCCESS] User field %s updated for username: %s", field.getFieldNameSQL(), username));
            } else {
                System.out.println(String.format("[ERROR] Failed to update user field for username: %s", username));
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public User login (String username, String password) {
        // TODO: code to handle user login
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Get info
                    String id = rs.getString("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    UserRole role = UserRole.valueOf(rs.getString("role"));

                    // Check role to return correct user type
                    if (role == UserRole.ADMIN) {
                        return new AdminUser(id, username, password, firstName, lastName);  
                    } else if (role == UserRole.CLIENT) {
                        return new ClientUser(id, username, password, firstName, lastName);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public void register(User user) {
        // TODO: code to handle user registration
        
    }
}
