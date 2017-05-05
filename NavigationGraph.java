/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          p5
// FILE:             Navigation Graph
//
// TEAM:    Team 56
// Authors: Tyler Davis and Cody Kairis
// Author1: Tyler Davis, tdavis5@wisc.edu, tdavis5, 002
// Author2: Cody Kairis, kairis@wisc.edu, kairis, 002
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class NavigationGraph implements GraphADT<Location, Path> {
	//TODO: Implement all methods of GraphADT
	//List of locations
	private List<Location> locations = new ArrayList<Location>();

	//priority queue for Dijkstra's 
	private PriorityQueue<DijkstraPQEntry<Location>> dpq = new 
			PriorityQueue<DijkstraPQEntry<Location>>();

	// visited table for Dijkstra's 
	private boolean[] visited = new boolean[100];

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

	/**
	 * Adds a vertex to the Graph
	 * 
	 * @param vertex
	 *            vertex to be added
	 */
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

	/**
	 * Creates a directed edge from src to dest
	 * 
	 * @param src
	 *            source vertex from where the edge is outgoing
	 * @param dest
	 *            destination vertex where the edge is incoming
	 * @param edge
	 *            edge between src and dest
	 */
	public void addEdge(Location src, Location dest, Path edge) {
		for (int i= 0; i < nodes.size(); i++) {

			//Find source within list of GraphNodes
			if (nodes.get(i).getVertexData().equals(src)) {

				//Add out edge to source's edge list
				nodes.get(i).addOutEdge(edge);
			}
		}
	}

	/**
	 * Getter method for the vertices
	 * 
	 * @return List of vertices of type V
	 */
	public List<Location> getVertices() {
		return locations;
	}

	/**
	 * Returns edge if there is one from src to dest vertex else null
	 * 
	 * @param src
	 *            Source vertex
	 * @param dest
	 *            Destination vertex
	 * @return Edge of type E from src to dest
	 */
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

	/**
	 * Returns the outgoing edges from a vertex
	 * 
	 * @param src
	 *            Source vertex for which the outgoing edges need to be obtained
	 * @return List of edges of type E
	 */
	public List<Path> getOutEdges(Location src) {
		List<Path> temp = null;
		for (int i= 0; i < nodes.size(); i++) {
			if (nodes.get(i).getVertexData().equals(src)) {
				temp = nodes.get(i).getOutEdges();
			}
		}	if( temp == null){
			System.out.println("Node is not a valid location");
			List<Path> empty = new ArrayList<Path>();
			return empty;
		}
		return temp;
	}

	/**
	 * Returns neighbors of a vertex
	 * 
	 * @param vertex
	 *            vertex for which the neighbors are required
	 * @return List of vertices(neighbors) of type V
	 */
	public List<Location> getNeighbors(Location vertex) {
		//Returns the locations that this location is connected to
		//Return the locations within the edge list associated with this location
		if ( vertex == null){
			System.out.println("Not a valid location");
			List<Location> empty = new ArrayList<Location>();
			return empty;
		}
		if( nodes.get(nodes.indexOf(vertex)).getOutEdges().isEmpty()){
			System.out.println( vertex.getName()+ " is not a valid location");
			List<Location> empty = new ArrayList<Location>();
			return empty;
		}
		List<Location> temp = new ArrayList<Location>();
		for (int i = 0; i < nodes.size(); i++) {
			GraphNode<Location, Path> node = nodes.get(i);
			for (int j = 0; j < nodes.get(i).getOutEdges().size(); j++) {
				temp.add(node.getOutEdges().get(j).getDestination());
			}
		}
		return temp;
	}

	/**
	 * Calculate the shortest route from src to dest vertex using
	 * edgePropertyName
	 * 
	 * @param src
	 *            Source vertex from which the shortest route is desired
	 * @param dest
	 *            Destination vertex to which the shortest route is desired
	 * @param edgePropertyName
	 *            edge property by which shortest route has to be calculated
	 * @return List of edges that denote the shortest route by edgePropertyName
	 */
	public List<Path> getShortestRoute(Location src, Location dest,
			String edgePropertyName) {
		// error handling stuff
		if (src == null || dest == null) {
			System.out.println(" Source and/or destinastion not valid Locations in the graph");
			List<Path> empty = new ArrayList<Path>();
			return empty;
		}
		if(src == dest){
			System.out.println("Location and destination are the same");
			List<Path> empty = new ArrayList<Path>();
			return empty;
		}
		// if index of edgeProperty name doesn't work
		/*for (int i = 0; i < this.edgePropertyNames.length; i++){
			if(!edgePropertyNames[i].equals(edgePropertyName)){
				System.out.println("Invalid option chosen");
				List<Path> empty = new ArrayList<Path>();
				return empty;
			}
		}*/
		//end error handling
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

		//Set source's weight to zero
		weight[srcIndex] = 0;

		//ANYTHING TO DO WITH DijkstraPQEntry doesn't work
		//Most likely to do with Comparable implementation
		//Consistent with piazza

		DijkstraPQEntry<Location> firstEntry = new 
				DijkstraPQEntry<Location>(weight[srcIndex],src);
		dpq.add(firstEntry);

		//CHANGE FROM INSIDE OF WHILE LOOP4
		DijkstraPQEntry<Location> curr;
		while(!dpq.isEmpty()){	

			curr = dpq.remove();


			//System.out.println(curr.getLocation().toString());

			int currIndex = 0;
			for (int count = 0; count < locations.size(); count++) {
				if (locations.get(count).equals(curr.getLocation())) {
					currIndex = count;
				}
			}

			//Set current node's index to true
			visited[currIndex] = true;


			// find unvisited successors
			//System.out.println("index of currN in nodes: " + locations.indexOf(curr.getLocation()));
			// The list is returning null because our currN isn't in location, so if the location is in there, do something
			// if its not, then we need to find another successor or end the process
			GraphNode<Location,Path> currN;
			//if ( locations.indexOf(curr.getLocation()) > 0){

			currN = nodes.get(currIndex);


			//Check each successor of the current node
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



					if (tempWeight <= weight[childID]){ 
						successor.setWeight(tempWeight);
						// update the predecessor list for successor
						weight[childID] = tempWeight;
						//Add current as the predecessor for the child
						predecessor[childID] = curr.getLocation();


						if(!dpq.contains(successor)){
							//System.out.println("successor:" + successor.getLocation().toString());
							dpq.add(successor);
							//System.out.println("added once");
						}

						//if successor is in queue, just update total weight

					}	
				}
			}
			//}
		}		


		//src and dest

		//Construct list of paths from predecessors
		List<Path> boi = new ArrayList<Path>();
		List<Path> reverseBoi = new ArrayList<Path>();
		int destID = 8;
		Location destination = null;
		Location source = null;
		boolean done = false;
		while (!done) {

			if (destination == null) {
				destination = dest;
			}
			for (int count = 0; count < locations.size(); count++) {
				if (destination.equals(locations.get(count))) {
					destID = count;
				}
			}
			//Assign source from predecessor list
			source = predecessor[destID];

			//Check if traced all the way back to original source Location
			if (source == null){
				System.out.println("No route exists");
				List<Path> empty = new ArrayList<Path>();
				return empty;
			}
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

	/**
	 * Getter method for edge property names
	 * 
	 * @return array of String that denotes the edge property names
	 */
	public String[] getEdgePropertyNames() {
		return this.edgePropertyNames;
	}
	
	/**
	 * Return a string representation of the graph
	 * 
	 * @return String representation of the graph
	 */
	@Override
	public String toString() {
		String out = "";
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.get(i).getOutEdges().size(); j++) {
				out = out + nodes.get(i).getOutEdges().get(j).toString()
						+ ",";
			}	
			out = out + "\n";
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
	class DijkstraPQEntry<V>{
		private double weight;
		private V location;

		public DijkstraPQEntry(double totalWeight, V name){
			this.weight = totalWeight;
			this.location = name;

		}

		public int compareTo(DijkstraPQEntry<V> other) {
			if(this.getLocation() == other.getLocation()){
				return 0;
			}
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
}
