package dao.implement;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import dao.AdminDAO;
import models.AdminUser;
import models.ClientUser;

public class AdminDAOImpl extends UserDAOImpl<AdminUser> implements AdminDAO {

    @Override
    public ClientUser getClientByUsername(String clientUsername) {
        // Code to retrieve a client user with the given username from the database
        String sql = "SELECT * FROM users WHERE username = ? AND role = 'CLIENT'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, clientUsername);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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

    @Override
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

    @Override
    public void createClient(ClientUser client) {
        // Code to create a new client user in the database
        super.createUser(client);  
    }

    @Override
    public boolean updateClientProfile(String clientUsername, ClientUser newClientInfo) {
        // Code to update the profile of a client in the database
        // TODO: Admin can update client password
        String sql = "UPDATE users SET firstName = ?, lastName = ?, password = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newClientInfo.getFirstName());
            pstmt.setString(2, newClientInfo.getLastName());
            pstmt.setString(3, clientUsername);
            
            System.out.println(pstmt.toString());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteClientByUsername(String clientUsername) {
        String sql = "DELETE FROM users WHERE username = ? AND role = 'CLIENT'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, clientUsername);

            return pstmt.executeUpdate() > 0;
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }
}
