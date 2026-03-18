package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnection;
import dao.UserDAO;
import models.AdminUser;
import models.ClientUser;
import models.User;
import models.UserRole;
import util.PasswordUtil;

public class UserDAOImpl<T extends User> implements UserDAO<T> {

    @Override
    public T login (String username, String password, Class<T> type) {
        // TODO: code to handle user login
        password = PasswordUtil.hashPassword(password);
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Get info
                    UserRole role = UserRole.valueOf(rs.getString("role"));
                    if (type.equals(AdminUser.class) && role != UserRole.ADMIN) {
                        System.out.println("[SECURITY] Unauthorized access attempt: Admin login with Client account.");
                        return null;
                    }
                    if (type.equals(ClientUser.class) && role != UserRole.CLIENT) {
                        System.out.println("[SECURITY] Unauthorized access attempt: Client login with Admin account.");
                        return null;
                    }
                    
                    return type.getConstructor(
                        String.class, String.class, String.class, String.class, String.class, UserRole.class
                    ).newInstance(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        role
                    );  
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createUser(User user) {
        // Add the new user to the database
        String sql = "INSERT INTO users (id, username, password, firstName, lastName, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getRole().toString());

            pstmt.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public T getUserByUsername(String username, Class<T> type) {
        // Code to retrieve a user with the given username from the database
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Get the role from database
                    return type.getConstructor(
                        String.class, String.class, String.class, String.class, String.class, UserRole.class
                    ).newInstance(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"), // This one is not secure
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        UserRole.valueOf(rs.getString("role"))
                    );
                }
            }

            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        // Code to retrieve a user with the given username from the database
        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
  
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProfile(String username, T newUserInfo) {
        // Code to update a specific field of a user in the database
        String sql = "UPDATE users SET firstName = ?, lastName = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newUserInfo.getFirstName());
            pstmt.setString(2, newUserInfo.getLastName());
            pstmt.setString(3, username);
            
            System.out.println(pstmt.toString());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUsername(String username, String newUsername) {
        if (existsByUsername(newUsername)) {
            System.out.println(String.format("The username %s already exists.", newUsername));
            return false;
        }
        // Code to update username of a user in the database
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, newUsername);
            pstmt.setString(2, username);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        if (!PasswordUtil.isValidPassword(newPassword)) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }
        // Code to update password of a user in the database
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, PasswordUtil.hashPassword(newPassword));
            pstmt.setString(2, username);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

}
