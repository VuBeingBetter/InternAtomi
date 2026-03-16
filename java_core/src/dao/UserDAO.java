package dao;

import models.ClientUser;
import models.UpdateField;
import models.User;
import models.UserRole;

public interface UserDAO {

    public void createClient(ClientUser clientUser);
    
    public User getUserByUsername(String username, UserRole role);

    public boolean isUserExist(String username, UserRole role);

    public void updateSpecificField(String username, UpdateField field, String newValue);

    // Login are pushed to Admin and Client separately 
    public User login (String username, String password);

    public void register (User user);
}
