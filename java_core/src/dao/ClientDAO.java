package dao;

import models.ClientUser;

public interface ClientDAO extends UserDAO<ClientUser> {
    // Unique methods here
    public void register(ClientUser client);
}
