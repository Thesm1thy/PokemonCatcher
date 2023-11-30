package smithy.pokemon_catcher.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import smithy.pokemon_catcher.connection.ConnectionManager;

public class RegionDaoImpl implements RegionDao {

	private Connection connection = null;
	private static int currRegion;
	// colors
	public static final String COLOR_RESET = "\u001B[0m";
	public static final String COLOR_GREEN = "\u001B[32m";
	public static final String COLOR_RED = "\u001B[31m";

	@Override
	public void establishConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
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
	public List<Regions> getAllRegions() {

		List<Regions> regionList = new ArrayList<>();

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM regions")) {

			while (rs.next()) {
				int id = rs.getInt("region_id");
				String regionName = rs.getString("region_name");

				Regions region = new Regions(id, regionName);
				regionList.add(region);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return regionList;

	}

	public boolean addTrainerToRegion(int trainer_id, int region_id) {

		try (PreparedStatement pstmt = connection
				.prepareStatement("INSERT INTO trainer_region (trainer_id, region_id) VALUES (?, ?)")) {
			pstmt.setInt(1, trainer_id);
			pstmt.setInt(2, region_id);
			int count = pstmt.executeUpdate();

			if (count > 0) {
				return true;
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(COLOR_GREEN + "Trainer is ready to go in this region!" + COLOR_RESET);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	// not correct
	@Override
	public int currRegion(int trainer_id) {
		try (PreparedStatement pstmt = connection
				.prepareStatement("SELECT region_id FROM trainer_region WHERE trainer_id = ?")) {

			pstmt.setInt(1, trainer_id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int userRegion = rs.getInt(1);

				currRegion = userRegion;
				return userRegion;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public boolean changeRegion(int region_id) {
		try (PreparedStatement pstmt = connection
				.prepareStatement("SELECT region_id FROM regions WHERE region_id = ?")) {

			pstmt.setInt(1, region_id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int userRegion = rs.getInt(1);

				currRegion = userRegion;
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateTrainerRegion(int trainer_id, int region_id) {
		try (PreparedStatement pstmt = connection
				.prepareStatement("UPDATE trainer_region SET region_id = ? WHERE trainer_id = ?")) {

			pstmt.setInt(1, region_id);
			pstmt.setInt(2, trainer_id);

			int rs = pstmt.executeUpdate();

			if (rs > 0)
				return true;
			else
				System.out.println("Region was not found. Please try again.");
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(COLOR_RED + "This region has not been added" + COLOR_RESET);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
