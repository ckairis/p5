import java.util.List;

public class NavigationGraph implements GraphADT<Location, Path> {

	//TODO: Implement all methods of GraphADT
	
	public NavigationGraph(String[] edgePropertyNames) {
		
	}

	@Override
	public void addVertex(V vertex) {
		
	}
	
	@Override
	public void addEdge(V src, V dest, E edge) {
		
	}
	
	@Override
	public List<V> getVertices() {
		return null;
	}
	
	@Override
	public E getEdgeIfExists(V src, V dest) {
		
	}
	
	@Override
	public List<E> getOutEdges(V src) {
		
	}
	
	@Override
	public List<V> getNeighbors(V vertex) {
		
	}
	
	@Override
	public List<E> getShortestRoute(V src, V dest, String edgePropertyName) {
		
	}
	
	@Override
	public String[] getEdgePropertyNames() {
		return null;
	}
	
	@Override
	public String toString() {
		return null;
	}
	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {
		return null; //TODO: implement correctly. 
	}

}
