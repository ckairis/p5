

public class DijkstraPQEntry<V> implements Comparable<DijkstraPQEntry<V>> {
	private double weight;
	private V location;
	
	public DijkstraPQEntry(double totalWeight, V name){
		this.weight = totalWeight;
		this.location = name;
		
	}
	
	public int compareTo(DijkstraPQEntry<V> other) {
		if (this.weight < other.getWeight()) {
			return -1;
		}
		else if (this.weight == other.getWeight()) {
			return 0;
		}
		else {
			return 1;
		}
		
	}
	
	public void setWeight(double number){
		this.weight = number;
	}
	public void setLocation (V name){
		this.location = name;
	}
	public double getWeight(){
		return this.weight;
	}
	public V getLocation(){
		return this.location;
}
}
