package dao.implement;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import dao.AdminDAO;
import models.ClientUser;
import models.UpdateField;
import models.UserRole;

public class AdminDAOImpl extends UserDAOImpl implements AdminDAO {

    @Override
    public boolean isClientExist(String clientUsername) {
        if (clientUsername == null || clientUsername.isEmpty()) {
            return false;
        }
        return this.isUserExist(clientUsername, UserRole.CLIENT);
    }

    @Override
    public ClientUser getClient (String clientUsername) {
        // Code to retrieve a client user from the database
        return (ClientUser) this.getUserByUsername(clientUsername, UserRole.CLIENT);
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

    @Override
    public void updateClientInfo(String clientUsername, UpdateField field, String newValue) {
        // Code to update a specific field of a client user in the database
        this.updateSpecificField(clientUsername, field, newValue);
    }

}
