package smithy.pokemon_catcher.DAO;

public class Pokemon {
	private int pokemon_id;
	private String pokemon_name;
	private String pokemon_type1;
	private String pokemon_type2;
	
	
	public Pokemon(int pokemon_id, String pokemon_name, String pokemon_type1, String pokemon_type2) {
		this.pokemon_id = pokemon_id;
		this.pokemon_name = pokemon_name;
		this.pokemon_type1 = pokemon_type1;
		this.pokemon_type2 = pokemon_type2;
	}

	

	public int getPokemon_id() {
		return pokemon_id;
	}




	public void setPokemon_id(int pokemon_id) {
		this.pokemon_id = pokemon_id;
	}




	public String getPokemon_name() {
		return pokemon_name;
	}




	public void setPokemon_name(String pokemon_name) {
		this.pokemon_name = pokemon_name;
	}




	public String getPokemon_type1() {
		return pokemon_type1;
	}




	public void setPokemon_type1(String pokemon_type1) {
		this.pokemon_type1 = pokemon_type1;
	}




	public String getPokemon_type2() {
		return pokemon_type2;
	}




	public void setPokemon_type2(String pokemon_type2) {
		this.pokemon_type2 = pokemon_type2;
	}




	@Override
	public String toString() {
		return "|#" + pokemon_id + " " + pokemon_name + " Type:"
				+ pokemon_type1 + " Type:" + pokemon_type2 + "|";
	}
	
	
	
	
}
