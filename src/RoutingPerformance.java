import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class RoutingPerformance {
	String networkScheme;
	String routingScheme;
	String topology;
	String workload;
	
	int totalConnections;
	int successfulConnections;
	float percentageSuccess;
	int failedConnections;
	float percentageFailed;
	
	ArrayList <Integer> allHops = new ArrayList<Integer>();
	float averageHops;
	ArrayList<Float> allDelay = new ArrayList<Float>();
	float averageDelay; 
	
	TreeMap<Float,String> connections;
	HashMap<String, Stack<String>> activePaths = new HashMap<String,Stack<String>>();
	Graph g;
	int packetRate;
	
	public static void main(String[] args) throws FileNotFoundException {
		RoutingPerformance rp = new RoutingPerformance();
		rp.networkScheme = args[0];
		rp.routingScheme = args[1];
		rp.topology = args[2];
		rp.workload = args[3];
		rp.packetRate = Integer.parseInt(args[4]);
		rp.connections = rp.addConnections(rp.workload);
		
		rp.g = new Graph(rp.topology);
		PathFinder pf = new PathFinder();
		
		/* 3 algorithms to implement (Dijkstra's):
		*
		*1. least number of hops (take cost of edge to be = 1)
		*2. least delay (cost of edge = propagation delay)
		*
		*3. least loaded (pick route where the 'narrowest' link that can accommodate highest capacity)
		*/
		
		String currentNode = "A";
		String destinationNode = "A";
		
		String[] packetInfo = rp.connections.firstEntry().getValue().split(" ");
		
		while (!rp.connections.isEmpty()) {//while there are still connections to be routed
			packetInfo = rp.connections.firstEntry().getValue().split(" ");
			//Format of connections = "S nodeA nodeB connectionnumber duration"
			if (packetInfo[0].equals("S")) {
				//if connection is add, get the path from pathfinder
				currentNode = packetInfo[1];
				//System.out.println("current is " + currentNode);
				destinationNode = packetInfo[2];
				//System.out.println("destination is " + destinationNode);

				//Calculate and update capacities
				HashMap<String, String> s = pf.findPath(rp.g, currentNode, destinationNode, rp.routingScheme);
				
				//Adding number of hops into arraylist
				//Adding delay of this path into arraylist
				String nodeA = destinationNode;
				String nodeB = nodeA;
				int hops = 0;
				float delay = 0;
				while(s.get(nodeA) != null) {
					hops++;
					nodeA = s.get(nodeA);
					delay = delay + rp.g.getLink(nodeA, nodeB).getDelay();
					nodeB = nodeA;
				}
				rp.allHops.add(hops);
				rp.allDelay.add(delay);
				
				//Updating the total number of connections
				rp.totalConnections++;
				
				if (true) { //////////////////////////need to determine whether the connection failed or not
					rp.successfulConnections++;
				} else {
					rp.failedConnections++;
				}
				
				rp.updateCapacities(s, destinationNode,currentNode,rp.connections.firstEntry().getValue().split(" ",2)[1]);
			} else if(packetInfo[0].equals("E")){
				Stack<String> stk = rp.activePaths.get(rp.connections.firstEntry().getValue().split(" ",2)[1]);
				if(stk != null) {
					String curNode = stk.pop();
					while(!stk.isEmpty()) {
						rp.g.getLink(curNode, stk.peek()).decreaseLink();
						curNode = stk.pop();
					}
					rp.activePaths.remove(rp.connections.firstEntry().getValue().split(" ",2)[1]);
				}
			} else {
				System.out.println("NANI!?");
			}
			rp.connections.remove(rp.connections.firstEntry().getKey());		}	
		
		
		///////////////////////LOG//////////////////////////////////////////////////
		rp.averageHops = rp.getAverageHops(rp.allHops);
		rp.averageDelay = rp.getAverageDelay(rp.allDelay);
		rp.percentageSuccess = (float)rp.successfulConnections / (float)rp.totalConnections;
		rp.percentageFailed = (float)rp.failedConnections / (float)rp.totalConnections;
		System.out.println("Total number of connections: " + rp.totalConnections);
		System.out.println("Number of sucessful routed connections: " + rp.successfulConnections);
		System.out.println("Percentage of successful routed connections: " + rp.percentageSuccess);
		System.out.println("Percentage of failed connections: " + rp.percentageFailed);
		System.out.println("Number of failed connections: " + rp.failedConnections);
		System.out.println("Average number of hops are: " + rp.averageHops);
		System.out.println("Average cumulative propagation delay per circuit: " + rp.averageDelay + "ms");
	}	
	
	
	public void updateCapacities (HashMap<String, String> s, String destinationNode,String sourceNode, String key) {
		String currentNode = destinationNode;  
		while (s.get(currentNode)!=null) { 
			//System.out.println(curNode + " "+ source);
			currentNode = s.get(currentNode);
		}
		System.out.println(currentNode + " "+ sourceNode);
		if(!currentNode.equals(sourceNode)) {
			//no path!!!!
			//System.out.println("NOPATH cur is "+curNode + " source is "+source);
			return;
		}
		
		currentNode= destinationNode;
		HashMap<String, ArrayList<Link>> nodes = this.g.getNodes();
		Stack<String> tempStringStack = new Stack<String>();
		tempStringStack.push(currentNode);
		while (s.get(currentNode)!=null) {  
			g.getLink(currentNode, s.get(currentNode)).increaseLink();
			currentNode = s.get(currentNode);
			tempStringStack.push(currentNode);
		}
		activePaths.put(key, tempStringStack); 
		
	}
	
		

	
	public TreeMap<Float, String> addConnections (String workloadFile) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(workloadFile));
		int connectionNumber = 0;
		String currentConnection;
		float currentConnectionTime;
		String currentConnectionSource;
		String currentConnectionDestination;
		float currentConnectionDuration;
		float currentConnectionEndTime;
		TreeMap<Float,String> connections = new TreeMap<Float,String>();  
		
		while (sc.hasNextLine()) {
			currentConnection = sc.nextLine();
			currentConnectionTime = Float.parseFloat((currentConnection.split(" "))[0]);
			currentConnectionSource = (currentConnection.split(" "))[1];
			currentConnectionDestination = (currentConnection.split(" "))[2];
			currentConnectionDuration = Float.parseFloat((currentConnection.split(" "))[3]);
			currentConnectionEndTime = currentConnectionDuration + currentConnectionTime;
			connections.put(currentConnectionTime, "S " + currentConnectionSource + " " + currentConnectionDestination + " " + connectionNumber + " " + currentConnectionDuration);
			connections.put(currentConnectionEndTime, "E " + currentConnectionSource + " " + currentConnectionDestination + " " + connectionNumber + " " + currentConnectionDuration);
			connectionNumber++;
		}
		return connections;
		
	}
	
	public float getAverageHops (ArrayList<Integer> hops) {
		float averageHops = 0;
		for (Integer hop : hops) {
			averageHops = averageHops + hop;
		}
		averageHops = averageHops / hops.size();
		return averageHops;
		
	}
	
	public float getAverageDelay (ArrayList<Float> delays) {
		float averageDelay = 0;
		for (Float hop : delays) {
			averageDelay = averageDelay + hop;
		}
		averageDelay = averageDelay / delays.size();
		return averageDelay;
	}
}
