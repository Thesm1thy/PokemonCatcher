package smithy.pokemon_catcherMain;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import smithy.pokemon_catcher.DAO.Pokemon;
import smithy.pokemon_catcher.DAO.PokemonDao;
import smithy.pokemon_catcher.DAO.PokemonDaoImpl;
import smithy.pokemon_catcher.DAO.RegionDaoImpl;
import smithy.pokemon_catcher.DAO.Regions;
import smithy.pokemon_catcher.DAO.User;
import smithy.pokemon_catcher.DAO.UserDao;
import smithy.pokemon_catcher.DAO.UserDaoImpl;

public class Main {

	public static UserDaoImpl userDao = new UserDaoImpl();
	public static PokemonDaoImpl PokemonDao = new PokemonDaoImpl();
	public static RegionDaoImpl regionDao = new RegionDaoImpl();

	public static int user_id = -1;
	public static int pokemon_id = -1;
	public static String Username = null;
	public static int Region = 1;
	
	//Colors
	public static final String COLOR_RESET = "\u001B[0m";
	//public static final String COLOR_BLACK = "\u001B[30m";
	//public static final String COLOR_WHITE = "\u001B[37m";
	public static final String COLOR_CYAN = "\u001B[36m";
	public static final String COLOR_YELLOW = "\u001B[33m";
	public static final String COLOR_RED = "\u001B[31m";
	public static final String COLOR_GREEN = "\u001B[32m";

	public static void main(String[] args) {

		try {
			userDao.establishConnection();
			PokemonDao.establishConnection();
			regionDao.establishConnection();
		} catch (Exception e) {
			System.out.println(COLOR_RED + "ERROR: Could establish a connection" + COLOR_RESET);
		}

		Scanner sc = new Scanner(System.in);
		boolean exit = false;

		while (true) {

			try {
				// Main Menu
				System.out.println();
				System.out.println("-------------------------------------------------------------");
				System.out.println(COLOR_CYAN + "Welcome to Smithy's " + COLOR_YELLOW + "Pokemon" + COLOR_CYAN + " Catcher, Please Choose an Option:" + COLOR_RESET);
				System.out.println("-------------------------------------------------------------");
				System.out.println("1: Login");
				System.out.println("2: Admin Login");
				System.out.println("3: Sign Up");
				System.out.println("4: Quit");

				int userOption = sc.nextInt();
				sc.nextLine();

				switch (userOption) {
				case 1:
					login(sc);
					break;
				case 2:
					adminLogin(sc);
					break;
				case 3:
					signup(sc);
					break;
				case 4:
					exit = true;
					break;
				default:
					System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
				}

				if (exit) {
					sc.close();
					System.out.println();
					System.out.println(COLOR_RED + "Goodbye! ~");
					System.exit(0);
				}
			} catch (InputMismatchException e) {
				System.out.println(COLOR_RED + "Invalid Input. Please try again." + COLOR_RESET);
				sc.nextLine();
			}
		}
	}

	// Main Menu Options

	static void signup(Scanner sc) {
		System.out.println();
		System.out.println("Hello Trainer, Please Enter Your Name:");
		String username = sc.nextLine();

		System.out.println("Enter a secure password:");
		String password = sc.nextLine();

		boolean signedup = userDao.createUser(username, password);

		if (signedup)
			System.out.println();
			System.out.println(COLOR_GREEN + "Successfully created user " + username + "." + COLOR_RESET);
	}

	static void login(Scanner sc) {

		System.out.println();
		System.out.println("Trainer Name:");
		String username = sc.nextLine();

		System.out.println("Password:");
		String password = sc.nextLine();

		User user = new User(username, password, "USER");

		try {
			userDao.getUsernameAndPassword(user);

			// Check if user is USER
			if (userDao.getCurrUser().get().getPermission().equals("USER")) {

				user_id = userDao.getCurrUser().get().getUser_id();
				Username = userDao.getCurrUser().get().getUsername();
				regionDao.addTrainerToRegion(user_id, 1);
				userMenu(sc);
			} else {
				System.out.println(COLOR_RED + "Admin cannot log into user menu." + COLOR_RESET);
			}
			// was invalidLogin exception
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
	}

	// Admin
	static void adminLogin(Scanner sc) {

		System.out.println();
		System.out.println("Please Enter Your Username:");
		String username = sc.nextLine();

		System.out.println("Please Enter Your Password:");
		String password = sc.nextLine();

		User Admin = new User(username, password, "ADMIN");

		try {
			userDao.getUsernameAndPassword(Admin);

			// Check if user is ADMIN
			if (userDao.getCurrUser().get().getPermission().equals("ADMIN")) {

				user_id = userDao.getCurrUser().get().getUser_id();
				Username = userDao.getCurrUser().get().getUsername();
				adminMenu(sc);
			} else {
				System.out.println(COLOR_RED + "Invalid admin Login." + COLOR_RESET);
			}
			// was invalid login
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
	}

	// User Menu

	static void userMenu(Scanner sc) {

		boolean userLoggedIn = true;

		while (userLoggedIn) {

			try {
				System.out.println("\nWelcome Back, " + Username + ". Please choose an option:");
				System.out.println("-----------------------------------------");
				System.out.println("1: CATCH POKEMON!");
				System.out.println("2: Open PokeDex");
				System.out.println("3: View Regions");
				System.out.println("4: Log Out");

				int userOption2 = sc.nextInt();

				switch (userOption2) {
				case 1:
					catchPokemon(sc);
					break;
				case 2:
					pokeDexMenu(sc);
					break;
				case 3:
					regionMenu(sc);
					break;
				case 4:
					System.out.println("\nLogging Out....");
					TimeUnit.SECONDS.sleep(2);
					userLoggedIn = false;
					break;
				default:
					System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
				}
			} catch (InputMismatchException | InterruptedException e) {
				System.out.println(COLOR_RED + "Invalid Input. Please try again." + COLOR_RESET);
				sc.nextLine();
			}

		}

	}

	// User Menu Options

	static void catchPokemon(Scanner sc) {

		boolean pokemonSearch = true;
		String currPokemon = null;

		while (pokemonSearch) {

			try {
				System.out.println();
				System.out.println("Searching for Wild Pokemon...");
				TimeUnit.SECONDS.sleep(4);

				PokemonDao.catchPokemon();
				currPokemon = PokemonDao.getCurrPokemon().get().getPokemon_name();
				pokemon_id = PokemonDao.getCurrPokemon().get().getPokemon_id();

				System.out.println("------------------------------------");
				System.out.println("Oh, a wild " + currPokemon + " has appeared");
				System.out.println("------------------------------------");
				System.out.println();
				System.out.println("What would you like to do?");
				System.out.println("--------------------------");
				System.out.println("1: Throw PokeBall");
				System.out.println("2: Run Away");

				int userOption4 = sc.nextInt();
				sc.nextLine();

				switch (userOption4) {
				case 1:
					throwPokeball();
					PokemonDao.addPokemonToPokeDex(user_id, pokemon_id);
					break;
				case 2:
					System.out.println();
					System.out.println("You got away safely.");
					pokemonSearch = false;
					break;
				default:
					System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
				}
			} catch (InputMismatchException | InterruptedException e) {
				System.out.println(COLOR_RED + "Invalid Input. Please try again." + COLOR_RESET);
				sc.nextLine();
			}
		}

	}

	// Catch Pokemon Options

	static void throwPokeball() {
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println();
			System.out.println(".");
			TimeUnit.SECONDS.sleep(1);
			System.out.println();
			System.out.println("..");
			TimeUnit.SECONDS.sleep(1);
			System.out.println();
			System.out.println("...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(COLOR_GREEN + "The Pokemon has been caught!" + COLOR_RESET);
	}

	// PokeDex Menu

	static void pokeDexMenu(Scanner sc) {

		boolean userLoggedIn = true;

		while (userLoggedIn) {

			try {
				System.out.println();
				System.out.println(Username + "'s Pokedex:");
				System.out.println("-----------------------------");
				System.out.println("1: Caught Pokemon");
				System.out.println("2: All Pokemon");
				System.out.println("3: Exit");

				int userOption3 = sc.nextInt();
				sc.nextLine();

				switch (userOption3) {
				case 1:
					getCaughtPokemon(PokemonDao.caughtPokemon(user_id));
					break;
				case 2:
					getPokemonList(PokemonDao.getAllPokemon());
					break;
				case 3:
					userLoggedIn = false;
					break;
				default:
					System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
				}
			} catch (InputMismatchException e) {
				System.out.println(COLOR_RED + "Invalid Input. Please try again." + COLOR_RESET);
				sc.nextLine();
			}
		}

	}

	// PokeDex Menu Options

	static void getCaughtPokemon(List<Pokemon> trainerPokemonList) {
		System.out.println();
		System.out.println("Caught Pokemon:");
		System.out.println("------------------------------");

		for (Pokemon p : trainerPokemonList) {
			System.out.println(p.toString());
		}
	}

	static void getPokemonList(List<Pokemon> pokemonList) {
		System.out.println();
		System.out.println("All Pokemon:");
		System.out.println("-------------------------------------");

		for (Pokemon p : pokemonList) {
			System.out.println(p.toString());
		}
		System.out.println();
	}

	// Region Menu

	static void regionMenu(Scanner sc) {

		boolean regions = true;

		// Region = regionDao.currRegion(Region);

		while (regions) {

			try {
				System.out.println();
				System.out.println("Regions");
				System.out.println("---------------");
				getRegionList(regionDao.getAllRegions());
				System.out.println("Current region: " + regionDao.currRegion(user_id));
				System.out.println("---------------");
				System.out.println("1: Change region");
				System.out.println("2: Exit");

				int userOption5 = sc.nextInt();
				sc.nextLine();

				switch (userOption5) {
				case 1:
					changeRegion(sc);
					break;
				case 2:
					regions = false;
					break;
				default:
					System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
				}
			} catch (InputMismatchException e) {
				System.out.println(COLOR_RED + "Invalid Input. Please try again." + COLOR_RESET);
				sc.nextLine();
			}

		}
	}

	// Region Menu Options

	static void getRegionList(List<Regions> regionList) {
		for (Regions r : regionList) {
			System.out.println(r.toString());
		}
		System.out.println();
	}

	static void changeRegion(Scanner sc) {
		System.out.println();
		System.out.println("Enter the region number of the region you want to travel to: ");
		int regionChoice = sc.nextInt();

		//regionDao.addTrainerToRegion(user_id, pokemon_id);
		regionDao.changeRegion(regionChoice);
		regionDao.updateTrainerRegion(user_id, regionChoice);
		//might delete
		Region = regionChoice;
	}

	// Admin Menu

	static void adminMenu(Scanner sc) {

		boolean userLoggedIn = true;

		while (userLoggedIn) {

			try {
				System.out.println("\nWelcome, " + Username + ". Please choose an option:");
				System.out.println("-----------------------------------------");
				System.out.println("1: Add User");
				System.out.println("2: Update User");
				System.out.println("3: Delete User");
				System.out.println("4: Add Pokemon");
				System.out.println("5: Log out");

				int userOption2 = sc.nextInt();
				sc.nextLine();

				switch (userOption2) {
				case 1:
					createUser(sc);
					break;
				case 2:
					updateUser(sc);
					break;
				case 3:
					deleteUser(sc);
					break;
				case 4:
					addPokemon(sc);
					break;
				case 5:
					userLoggedIn = false;
					break;
				default:
					System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
				}
			} catch (InputMismatchException e) {
				System.out.println(COLOR_RED + "Invalid Input. Please try again." + COLOR_RESET);
				sc.nextLine();
			}
		}

	}

	// Admin Menu Options

	static void createUser(Scanner sc) {

		System.out.println();
		System.out.println("Please enter trainers username:");
		String username = sc.nextLine();

		System.out.println("Please enter trainers password:");
		String password = sc.nextLine();

		System.out.println("Select permission to assign:");
		System.out.println("1. USER");
		System.out.println("2. ADMIN");

		int permission = sc.nextInt();
		boolean created = false;

		switch (permission) {
		case 1:
			created = userDao.createUser(username, password, "USER");
			break;
		case 2:
			created = userDao.createUser(username, password, "ADMIN");
			break;
		default:
			System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
		}

		if (created)
			System.out.println("Successfully created user " + username + ".");
	}

	static void updateUser(Scanner sc) {

		System.out.println();
		System.out.println("Please enter the trainers current username:");
		String username = sc.nextLine();

		System.out.println("Please enter the trainers new username:");
		String newUsername = sc.nextLine();

		System.out.println("Please enter the trainers new password:");
		String newPassword = sc.nextLine();

		// Prevent Current Admin from changing it's own permissions.
		if (!username.equals(Username)) {
			System.out.println("Select permission to assign:");
			System.out.println("1. USER");
			System.out.println("2. ADMIN");

			int permission = sc.nextInt();
			boolean updated = false;

			switch (permission) {
			case 1:
				updated = userDao.updateUser(username, newUsername, newPassword, "USER");
				break;
			case 2:
				updated = userDao.updateUser(username, newUsername, newPassword, "ADMIN");
				break;
			default:
				System.out.println(COLOR_RED + "Invalid Option. Please choose from the following options." + COLOR_RESET);
			}

			if (updated)
				System.out.println("Successfully updated user: " + newUsername + ".");
		} else {
			// Current Admin being updated
			boolean updated = userDao.updateUser(username, newUsername, newPassword, "ADMIN");

			if (updated) {
				System.out.println("Successfully updated trainer: " + newUsername + ".");
				Username = newUsername;
			}
		}
	}

	static void deleteUser(Scanner sc) {

		System.out.println();
		System.out.println("Please enter a trainers username to delete:");
		String username = sc.nextLine();

		int user_id_to_delete = userDao.getIdByUsername(username);

		// Prevent from deleting current Admin
		if (user_id_to_delete == user_id) {
			System.out.println(COLOR_RED + "Cannot delete " + username + ". Admin is currently logged in." + COLOR_RESET);
		} else {
			boolean deleted = userDao.deleteUser(user_id_to_delete);

			if (deleted)
				System.out.println("Successfully deleted user " + username + ".");
		}
	}

// Add pokemon to all pokemon list
	static void addPokemon(Scanner sc) {
		try {
			System.out.println();
			System.out.println("Please enter the id of the pokemon:");
			int pokemon_id = sc.nextInt();
			sc.nextLine();

			System.out.println("Please enter the name of the pokemon: ");
			String pokemon_name = sc.nextLine();

			System.out.println("Please enter the 1st type of the pokemon:");
			String pokemon_type1 = sc.nextLine();

			System.out.println("Please enter the 2nd type of the pokemon:");
			String pokemon_type2 = sc.nextLine();

			PokemonDao.addPokemonByAdmin(pokemon_id, pokemon_name, pokemon_type1, pokemon_type2);

			System.out.println("Successfully added " + pokemon_name + " to the PokeDex.");

		} catch (InputMismatchException e) {
			System.out.println(COLOR_RED + "Invalid Input. Please try again." + COLOR_RESET);
			sc.nextLine();
		}

	}

}