package dao;

import models.ClientUser;
import models.UpdateField;

public interface ClientDAO extends UserDAO {
    
    public ClientUser getClientByUsername(String username);

    public void updateInfo(String username, UpdateField field, String newValue);

    
}
