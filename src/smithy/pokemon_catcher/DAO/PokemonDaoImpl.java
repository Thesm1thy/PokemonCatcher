package smithy.pokemon_catcher.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import smithy.pokemon_catcher.connection.ConnectionManager;

public class PokemonDaoImpl implements PokemonDao {
	private Connection connection = null;
	private static Optional<Pokemon> currPokemon;
	

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
	public List<Pokemon> getAllPokemon() {
		
		List<Pokemon> pokemonList = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pokemon")){

            while(rs.next()) {
                int id = rs.getInt("pokemon_id");
                String pokemonName = rs.getString("pokemon_name");
                String pokemonType1 = rs.getString("pokemon_type1");
                String pokemonType2 = rs.getString("pokemon_type2");

                Pokemon pokemon = new Pokemon(id, pokemonName, pokemonType1, pokemonType2);
                pokemonList.add(pokemon);

            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return pokemonList;
	}
	
	
	public Optional<Pokemon> getCurrPokemon() {
		return currPokemon;
	}
	
	

	@Override
	public List<Pokemon> catchPokemon() {
		List<Pokemon> spawnedPokemonList = new ArrayList<>();
		Random rand = new Random();
		//random pokemon 1-151
		int randInt = rand.nextInt(71);
		
		
		try
			(PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM pokemon WHERE pokemon_id = ?")) {
            pstmt.setInt(1, randInt);
            
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("pokemon_id");
                String pokemonName = rs.getString("pokemon_name");
                String pokemonType1 = rs.getString("pokemon_type1");
                String pokemonType2 = rs.getString("pokemon_type2");

                Pokemon pokemon = new Pokemon(id, pokemonName, pokemonType1, pokemonType2);
                Optional<Pokemon> pokemonFound = Optional.of(pokemon);
                spawnedPokemonList.add(pokemon);
                currPokemon = pokemonFound;
         

            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
		
		
		return spawnedPokemonList;
	}

	@Override
	public boolean addPokemonToPokeDex(int trainer_id, int pokemon_id) {
		try(PreparedStatement pstmt = connection.prepareStatement("INSERT INTO trainer_pokemon (trainer_id, pokemon_id, catch_status) VALUES (?, ?, 'CAUGHT')")) {
            pstmt.setInt(1, trainer_id);
            pstmt.setInt(2, pokemon_id);
            int count=pstmt.executeUpdate();

            if(count>0) {
                return true;
            }
           }
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("This Pokemon is already in your PokeDex!");
		}
		 catch(SQLException e){
            e.printStackTrace();
        }
		
		return false;
	}



	public List<Pokemon> caughtPokemon(int trainer_id) {
		List<Pokemon> trainerPokemonList = new ArrayList<>();
	
		
		try
			(PreparedStatement pstmt = connection.prepareStatement("select pokemon.pokemon_id, pokemon_name, pokemon_type1, pokemon_type2, trainer_pokemon.catch_status from pokemon left join trainer_pokemon on pokemon.pokemon_id = trainer_pokemon.pokemon_id where catch_status = 'CAUGHT' AND trainer_id = ?;")){
				
				pstmt.setInt(1,trainer_id);
				ResultSet rs = pstmt.executeQuery();
				
				
				
				while(rs.next()) {
					
                int id = rs.getInt("pokemon_id");
                String pokemonName = rs.getString("pokemon_name");
                String pokemonType1 = rs.getString("pokemon_type1");
                String pokemonType2 = rs.getString("pokemon_type2");
                //String pokemonStatus = rs.getString("catch_status");
                
                Pokemon pokemon = new Pokemon(id, pokemonName, pokemonType1, pokemonType2);
                trainerPokemonList.add(pokemon);
				}
				
				
		
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
		
		
		return trainerPokemonList;
	}	
	
	@Override
	public boolean addPokemonByAdmin(int pokemon_id, String pokemon_name, String pokemon_type1, String pokemon_type2) {
		
		try(PreparedStatement pstmt = connection.prepareStatement("insert into pokemon(pokemon_id, pokemon_name, pokemon_type1, pokemon_type2) values(?, ?, ?, ? )")) {
            pstmt.setInt(1, pokemon_id);
            pstmt.setString(2, pokemon_name);
            pstmt.setString(3, pokemon_type1);
            //If pokemon has 1 type
            if (pokemon_type2 == "") {
            	pstmt.setNull(4, Types.NULL);;
            }
            else {
            	pstmt.setString(4, pokemon_type2);
            }
            

            int count=pstmt.executeUpdate();

            if(count>0) {
            	
                return true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
	}
	

}
