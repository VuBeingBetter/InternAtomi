package dao;

import java.util.List;

import models.AdminUser;
import models.ClientUser;

public interface AdminDAO extends UserDAO<AdminUser> {

    public ClientUser getClientByUsername(String clientUsername);

    public List<ClientUser> getAllClients();

    public void createClient(ClientUser client);

    public boolean updateClientProfile(String clientUsername, ClientUser newClientInfo);

    public boolean deleteClientByUsername(String clientUsername);

}
