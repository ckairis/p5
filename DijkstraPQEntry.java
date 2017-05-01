
public class DijkstraPQEntry {
	private double weight;
	private Location location;
	
	public DijkstraPQEntry(double totalWeight, Location name){
		this.weight = totalWeight;
		this.location = name;
		
	}
	public void setWeight(double number){
		this.weight = number;
	}
	public void setLocation (Location name){
		this.location = name;
	}
	public double getWeight(){
		return this.weight;
	}
	public Location getLocation(){
		return this.location;
	}
}
