import java.util.ArrayList;
import java.util.List;

public class NavigationGraph implements GraphADT<Location, Path> {

	//List of locations
	private List<Location> locations = new ArrayList<Location>();
	
	//TODO: Implement all methods of GraphADT
	private List<GraphNode<Location,Path>> predecessor = new 
			ArrayList<GraphNode<Location,Path>>();
	
	//List of GraphNodes
	private List<GraphNode<Location,Path>> nodes = new 
			ArrayList<GraphNode<Location,Path>>();
	
	//List of Edge Properties
	private String[] edgePropertyNames;
	
	
	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
	}

	@Override
	public void addVertex(Location vertex) {
		//add new Location to list, use index in list as ID
		this.nodes.add(new GraphNode<Location,Path>(vertex, nodes.size()));
	}
	
	@Override
	public void addEdge(Location src, Location dest, Path edge) {
		for (int i= 0; i < nodes.size(); i++) {
			if (nodes.get(i).getVertexData().equals(src)) {
				
				//Add out edge to source's edge list
				nodes.get(i).addOutEdge(edge);
			}
		}
	}
	
	@Override
	public List<Location> getVertices() {
		for (int i=0; i < nodes.size(); i++) {
			locations.add(nodes.get(i).getVertexData());
		}
		return locations;
	}
	
	@Override
	public Path getEdgeIfExists(Location src, Location dest) {
		
		GraphNode<Location,Path> temp = null;
		
		for (int i=0; i < nodes.size(); i++) {
			if (nodes.get(i).getVertexData().equals(src)) {
				temp = nodes.get(i);
			}
		}
		for (int j = 0; j < temp.getOutEdges().size(); j++) {
			if (temp.getOutEdges().get(j).getDestination().equals(dest)) {
				return temp.getOutEdges().get(j);
			}
		}
		return null;
	}
	
	@Override
	public List<Path> getOutEdges(Location src) {
			List<Path> temp = null;
		for (int i= 0; i < nodes.size(); i++) {
			if (nodes.get(i).getVertexData().equals(src)) {
				temp = nodes.get(i).getOutEdges();
			}
		}	
		return temp;
	}
	
	@Override
	public List<Location> getNeighbors(Location vertex) {
		return null;
	}
	
	@Override
	public List<Path> getShortestRoute(Location src, Location dest,
			String edgePropertyName) {
		return null;
	}
	
	@Override
	public String[] getEdgePropertyNames() {
		return this.edgePropertyNames;
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
