package smithy.pokemon_catcher.DAO;

import java.sql.SQLException;
import java.util.List;

public interface RegionDao {
	
	// needed for later so we make sure that the connection manager gets called
    public void establishConnection() throws ClassNotFoundException, SQLException;

    // as well, this method will help with closing the connection
    public void closeConnection() throws SQLException;
    
    
    public List<Regions> getAllRegions();
    
    public int currRegion(int trainer_id);
    
    
    public boolean changeRegion(int region_id);

}
