import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class NavigationGraph implements GraphADT<Location, Path> {
	//TODO: Implement all methods of GraphADT
	//List of locations
	private List<Location> locations = new ArrayList<Location>();
	
	//priority queue for Dijkstra's 
		private PriorityQueue<DijkstraPQEntry> dpq = new 
	PriorityQueue<DijkstraPQEntry>();
		
		// visited table for Dijkstra's 
		private boolean[] visited = new boolean[100];
		
		//List of edges to reconstruct shortest path
		private List<Path> route = new ArrayList<Path>();
		
		//Weight holder for Dijkstra's
		private double[] weight = new double[100];
	
		static double MAX_VALUE = Double.MAX_VALUE;
		
	//List of predecessors associated with each GraphNode in nodes list below
	private Location[] predecessor = new 
			Location[100];
	
	//List of GraphNodes
	private List<GraphNode<Location,Path>> nodes = new 
			ArrayList<GraphNode<Location,Path>>();
	
	//List of Edge Properties
	private String[] edgePropertyNames;
	
	
	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
	}

	
	public void addVertex(Location vertex) {
		//Check if location exists in list already
		for (int i=0; i < locations.size(); i++) {
			if (vertex.equals(locations.get(i))) {
				return;
			}
		}
		//add new Location to lists, use index in list as ID
		this.nodes.add(new GraphNode<Location,Path>(vertex, nodes.size()));
		this.locations.add(vertex);
	}
	
	
	public void addEdge(Location src, Location dest, Path edge) {
		for (int i= 0; i < nodes.size(); i++) {
			
			//Find source within list of GraphNodes
			if (nodes.get(i).getVertexData().equals(src)) {
				
				//Add out edge to source's edge list
				nodes.get(i).addOutEdge(edge);
			}
		}
	}
	
	
	public List<Location> getVertices() {
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
		//Returns the locations that this location is connected to
		//Return the locations within the edge list associated with this location
		return null;
	}
	
	
	public List<Path> getShortestRoute(Location src, Location dest,
			String edgePropertyName) {
		
				int propertyIndex = 0;
				String[] properties = this.getEdgePropertyNames();
				
				for (int i = 0; i < this.getEdgePropertyNames().length; i++) {
					if (properties[i].equalsIgnoreCase(edgePropertyName)) {
						propertyIndex = i;
					}
				}
		//set all visited to false, all predecessors to null, and weight to inf.
				for (int i = 0; i < this.locations.size(); i++){
					this.visited[i] = false;
					this.weight[i] = MAX_VALUE;
				}
				int srcIndex = 0;
				//Get graph node location index with from the locations maybe not
				if (locations.contains(src)){
					srcIndex = locations.indexOf(src);
				} else{
					return null;
				}
				weight[srcIndex] = 0;
				
				//ANYTHING TO DO WITH DijkstraPQEntry doesn't work
				//Most likely to do with Comparable implementation
				//Consistent with piazza
				
				DijkstraPQEntry<Location> firstEntry = new DijkstraPQEntry<Location>(weight[srcIndex],src);
				dpq.add(firstEntry);
				
				while(!dpq.isEmpty()){	
					DijkstraPQEntry<Location> curr;
					curr = dpq.remove();
					// curr is not changing for whatever reason
					System.out.println(curr.getLocation().toString());
					
					int currIndex = 0;
					for (int count = 0; count < locations.size(); count++) {
						if (locations.get(count).equals(curr.getLocation())) {
							currIndex = count;
						}
					}
					visited[currIndex] = true;
					
					
					// find unvisited successors
					//System.out.println("index of currN in nodes: " + locations.indexOf(curr.getLocation()));
					// The list is returning null because our currN isn't in location, so if the location is in there, do something
					// if its not, then we need to find another successor or end the process
					GraphNode<Location,Path> currN;
					if ( locations.indexOf(curr.getLocation()) > 0){
					currN = nodes.get(locations.indexOf(curr.getLocation()));
					
					for( int i = 0; i < currN.getOutEdges().size(); i++){
						// Predecessor list
						Path childPath = currN.getOutEdges().get(i);//get Child Path
						Location child = childPath.getDestination();// Child as a Location
						DijkstraPQEntry<Location> successor =  //Creates PQ entry
								new DijkstraPQEntry<Location>(childPath.getProperties().get(propertyIndex),child); // check the initialization of this path
					// if the node isn't visited, update the weight, insert into the PQ
						
						int childID = 0;
						for (int j= 0; j < nodes.size(); j++) {
							if (locations.get(j).equals(child)) {
								childID = j;
							}
						}
						
						if(!visited[childID]){
							// tempWeight is the potential new weight that needs to be compared to the old weight
							double tempWeight = successor.getWeight() + curr.getWeight();
							// if the new weight is less then the old weight, update the weight
							if (tempWeight <= successor.getWeight()){ 
								successor.setWeight(tempWeight);
								// update the predecessor list for successor
								
								//CAN'T DO THIS, ADDS EVERY PATH TO THE LIST
								//NEED TO RECONSTRUCT PATH BASED ON PREDECESSORS
								predecessor[childID] = curr.getLocation();
								
								
								//if successor is in queue, just update total weight
								if(!dpq.contains(successor)){
									System.out.println("successor:" + successor.getLocation().toString());
									dpq.add(successor);
									//System.out.println("added once");
								}
							}	
						}
					}
				}	
				 else{
				
		//src and dest
				
		//Construct list of paths from predecessors
		List<Path> boi = new ArrayList<Path>();
		List<Path> reverseBoi = new ArrayList<Path>();
		int destID = 0;
		Location destination = dest;
		Location source;
		boolean done = false;
		while (!done) {
			
			for (int count = 0; count < predecessor.length; count++) {
				if (destination.equals(predecessor[count])) {
					destID = count;
				}
			}
			//Assign source from predecessor list
			source = predecessor[destID];
			
			//Check if traced all the way back to original source Location
			if (source.equals(src)) {
				done = true;
			}
			//Add list to the list of paths
			boi.add(getEdgeIfExists(source, destination));
			
			//Switch source to destination and repeat
			destination = source;
			
		}
		//Get index of last element in list
		int track = boi.size() - 1;
		for (int i = 0; i < boi.size(); i++) {
			reverseBoi.add(i, boi.get(track));
			track = track -1;
		}
		//Return forward list of paths
		return reverseBoi;
				}	
	} return null;
	}

	
	
	
	public String[] getEdgePropertyNames() {
		return this.edgePropertyNames;
	}
	

	public String toString() {
		String out = "";
		for (int i = 0; i < nodes.size(); i++) {
			out = out+ nodes.get(i).toString() + "\n";
		}
		return out;
	}
	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {
		//TODO: implement correctly.
		Location temp = new Location(name);
		
		for (int i=0; i < locations.size(); i++) {
			
			//If locations match, return location
			if (temp.equals(locations.get(i))) {
				return locations.get(i);
			}
		}
		//if not found, return null
		return null;  
	}

}
