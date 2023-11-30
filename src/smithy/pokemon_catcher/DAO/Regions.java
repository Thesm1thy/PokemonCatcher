package smithy.pokemon_catcher.DAO;

public class Regions {
	
	
	private int regionId;
	private String regionName;
	
	
	
	public Regions(int regionId, String regionName) {
		this.regionId = regionId;
		this.regionName = regionName;
	}
	
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	
	@Override
	public String toString() {
		return "Region " + regionId + ": " + regionName;
	}

}
