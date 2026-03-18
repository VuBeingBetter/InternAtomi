package dao;

import models.User;

public interface UserDAO <T extends User>{
    // Use generic to separate Client and Admin
    public T login (String username, String password, Class<T> type);

    public void createUser(User user);
    
    public T getUserByUsername(String username, Class<T> type);

    public boolean existsByUsername(String username);

    public boolean updateProfile(String username, T newUserInfo);

    public boolean updateUsername(String username, String newUsername);

    public boolean updatePassword(String username, String newPassword);
    
}
