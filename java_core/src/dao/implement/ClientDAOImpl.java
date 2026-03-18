package dao.implement;

import dao.ClientDAO;
import models.ClientUser;

public class ClientDAOImpl extends UserDAOImpl<ClientUser> implements ClientDAO {

    @Override
    public void register(ClientUser client) {
        super.createUser(client);
    }
    
}
