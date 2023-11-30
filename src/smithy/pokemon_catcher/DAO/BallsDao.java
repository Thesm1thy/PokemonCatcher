package smithy.pokemon_catcher.DAO;

import java.sql.SQLException;

public interface BallsDao {
	
	
	// needed for later so we make sure that the connection manager gets called
    public void establishConnection() throws ClassNotFoundException, SQLException;

    // as well, this method will help with closing the connection
    public void closeConnection() throws SQLException ;
    
    
    

	public boolean throwBall(int ball_id, int ball_qty);
    
    
    //public boolean switchBall(String ball_name);

}
