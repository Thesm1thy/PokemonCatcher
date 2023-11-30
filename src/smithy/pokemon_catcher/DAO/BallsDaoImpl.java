package smithy.pokemon_catcher.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import smithy.pokemon_catcher.connection.ConnectionManager;

public class BallsDaoImpl implements BallsDao {
	
	private Connection connection = null;

	@Override
	public void establishConnection() throws ClassNotFoundException, SQLException {
		
		if(connection == null) {
            try {
                connection = ConnectionManager.getConnection();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
	}

	@Override
	public void closeConnection() throws SQLException {
		connection.close();
		
	}

	@Override
	public boolean throwBall(int ball_id, int ball_qty) {
		//subtract from qty
		return false;
	}
	
	
}
