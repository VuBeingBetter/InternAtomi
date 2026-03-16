package dao;

import java.util.List;

import models.ClientUser;
import models.UpdateField;

public interface AdminDAO extends UserDAO {

    public boolean isClientExist(String clientUsername);

    public ClientUser getClient (String clientUsername);

    public List<ClientUser> getAllClients();

    public boolean deleteClientByUsername(String clientUsername);

    public void updateClientInfo(String clientUsername, UpdateField field, String newValue);

}
