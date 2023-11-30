package smithy.pokemon_catcher.DAO;

import java.sql.SQLException;
import java.util.List;

public interface PokemonDao {
	
	// needed for later so we make sure that the connection manager gets called
    public void establishConnection() throws ClassNotFoundException, SQLException;

    // as well, this method will help with closing the connection
    public void closeConnection() throws SQLException;
    
    //lists all pokemon for pokedex
    public List<Pokemon> getAllPokemon();
    
    //wild pokemon spawns
    public List<Pokemon> catchPokemon();
    
    //adds pokemon info to users pokedex after catch
    public boolean addPokemonToPokeDex(int trainer_id, int pokemon_id);
    
    //Add pokemon to database
    public boolean addPokemonByAdmin(int pokemon_id, String pokemon_name, String pokemon_type1, String Pokemon_type2);
    


	

}
