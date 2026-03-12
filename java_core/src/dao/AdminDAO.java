package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import models.ClientUser;
import models.UserRole;

public class AdminDAO extends UserDAO {

    public void createClient(ClientUser clientUser) {
        // Add the new client user to the database
        String sql = "INSERT INTO users (id, username, password, firstName, lastName, role) VALUES (?, ?, ?, ?, ?, 'CLIENT')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientUser.get_id());
            pstmt.setString(2, clientUser.getUsername());
            pstmt.setString(3, clientUser.getPassword());
            pstmt.setString(4, clientUser.getFirstName());
            pstmt.setString(5, clientUser.getLastName());    
            pstmt.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public boolean isClientExist(String clientUsername) {
        if (clientUsername == null || clientUsername.isEmpty()) {
            return false;
        }
        return this.isUserExist(clientUsername, UserRole.CLIENT);
    }

    public ClientUser getClient (String clientUsername) {
        // Code to retrieve a client user from the database
        return (ClientUser) this.getUserByUsername(clientUsername, UserRole.CLIENT);
    }

    public List<ClientUser> getAllClients() {
        // Code to retrieve all client users from the database
        List<ClientUser> clients = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'CLIENT'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new ClientUser(
                    rs.getString("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("firstName"),
                    rs.getString("lastName")
                ));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return clients;
    }

    public void deleteClient(String clientUsername) {
        String sql = "DELETE FROM users WHERE username = ? AND role = 'CLIENT'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientUsername);
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


}
