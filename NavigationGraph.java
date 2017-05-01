import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class NavigationGraph implements GraphADT<Location, Path> {
//TODO: Implement all methods of GraphADT
	//List of locations22
	private List<Location> locations = new ArrayList<Location>();
	
	//priority queue for Dijkstra's 
	private PriorityQueue<DijkstraPQEntry> dpq = new 
				PriorityQueue<DijkstraPQEntry>();
	
	/*private PriorityQueue<GraphNode<Location,Path>> dpq = new 
			PriorityQueue<GraphNode<Location,Path>>();*/
	
	// visited table for Dijkstra's 
	private boolean[] visited;
	
	//Weight holder for Dijkstra's
	private double[] weight;
	
	//Predecessor List for Dijkstra's	
	private List<GraphNode<Location,Path>> predecessor = new 
			ArrayList<GraphNode<Location,Path>>();
	
	//List of GraphNodes
	private List<GraphNode<Location,Path>> nodes = new 
			ArrayList<GraphNode<Location,Path>>();
	
	//List of Edge Properties
	private String[] edgePropertyNames;
	// 
	static double MAX_VALUE;
	
	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
	}


	public void addVertex(Location vertex) {
		//add new Location to list, use index in list as ID
		this.nodes.add(new GraphNode<Location,Path>(vertex, nodes.size()));
	}
	
	
	public void addEdge(Location src, Location dest, Path edge) {
		for (int i= 0; i < nodes.size(); i++) {
			if (nodes.get(i).getVertexData().equals(src)) {
				
				//Add out edge to source's edge list
				nodes.get(i).addOutEdge(edge);
			}
		}
	}
	
	
	public List<Location> getVertices() {
		for (int i=0; i < nodes.size(); i++) {
			locations.add(nodes.get(i).getVertexData());
		}
		return locations;
	}
	

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
	
	
	public List<Path> getOutEdges(Location src) {
			List<Path> temp = null;
		for (int i= 0; i < nodes.size(); i++) {
			if (nodes.get(i).getVertexData().equals(src)) {
				temp = nodes.get(i).getOutEdges();
			}
		}	
		return temp;
	}
	
	
	public List<Location> getNeighbors(Location vertex) {
		return null;
	}
	

	public List<Path> getShortestRoute(Location src, Location dest,
			String edgePropertyName) {
		
		//set all visited to false, all predecessors to null, and weight to inf.
		for (int i = 0; i < this.locations.size(); i++){
			this.visited[i] = false;
			this.predecessor.add(null);
			this.weight[i] = MAX_VALUE;
		}
		int index = 0;
		//Get graph node with from the locations maybe not
		if (nodes.contains(src)){
			index = nodes.indexOf(src);
		} // else its an error
		weight[index] = 0;
		
		DijkstraPQEntry firstEntry = new DijkstraPQEntry(weight[index],src);
		dpq.add(firstEntry);
		
		while(!dpq.isEmpty()){	
			DijkstraPQEntry curr;
			curr = dpq.remove();
			visited[nodes.indexOf(curr)] = true;
			
			
			// find unvisited successors
			GraphNode<Location,Path> currN = nodes.get(nodes.indexOf(curr));	
			for( int i = 0; i < currN.getOutEdges().size(); i++){
				// Predecessor list
				Path childPath = currN.getOutEdges().get(i);//get Child Path
				Location child = childPath.getDestination();// Child as a Location
				DijkstraPQEntry successor =  //Creates PQ entry
						new DijkstraPQEntry(childPath.getProperties().get(i),child); // check the initialization of this path
			// if the node isn't visited, update the weight, insert into the PQ

				if(visited[nodes.indexOf(child)] = false){
					// tempWeight is the potential new weight that needs to be compared to the old weight
					double tempWeight = successor.getWeight() + curr.getWeight();
					// if the new weight is less then the old weight, update the weight
					if (tempWeight < successor.getWeight()){ 
						successor.setWeight(tempWeight);
						// update the predecessor list for successor
						predecessor.add(currN);
						//if successor is in queue, just update total weight
						if(!dpq.contains(successor)){
							dpq.add(successor);
						}
					}	
				}
			}
		}		
		return null;
	}
	

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
