package dao.implement;

import dao.ClientDAO;
import models.ClientUser;
import models.UpdateField;
import models.UserRole;

public class ClientDAOImpl extends UserDAOImpl implements ClientDAO {

    @Override
    public ClientUser getClientByUsername(String username) {
        return (ClientUser) getUserByUsername(username, UserRole.CLIENT);
    }

    @Override
    public void updateInfo(String username, UpdateField field, String newValue) {
        this.updateSpecificField(username, field, newValue);
        System.out.println("Client information updated successfully.");
        System.out.println(getClientByUsername(username).toString());
    }
    
}
