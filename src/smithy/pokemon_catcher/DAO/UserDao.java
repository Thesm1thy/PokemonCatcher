package smithy.pokemon_catcher.DAO;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    // needed for later so we make sure that the connection manager gets called
    public void establishConnection() throws ClassNotFoundException, SQLException;

    // as well, this method will help with closing the connection
    public void closeConnection() throws SQLException ;

    public Optional<User> getUsernameAndPassword(User user);
    
    public boolean createUser(String username, String password);
    
    //Admin create
    public boolean createUser(String username, String password, String permission);
    
    public boolean deleteUser(int user_id);
    
    public boolean updateUser(String username, String newUsername, String newPassword, String newPermission);
}
