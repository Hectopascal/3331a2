import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;


public class RoutingPerformance {
	
	String networkScheme;
	String routingScheme;
	
	int totalConnections;
	int successfulConnections;
	float percentageSuccess;
	int failedConnections;
	float percentageFailed;
	
	int packetRate;
	
	ArrayList <Integer> allHops = new ArrayList<Integer>();
	float averageHops;
	
	ArrayList<Float> allDelay = new ArrayList<Float>();
	float averageDelay; 
	
	TreeMap<Float,String> connections;
	HashMap<String, Stack<String>> activePaths = new HashMap<String,Stack<String>>();
	Graph g;
	
	public static void main(String[] args) throws FileNotFoundException {
		RoutingPerformance rp = new RoutingPerformance();
		rp.networkScheme = args[0];
		rp.routingScheme = args[1];
		String topologyFile = args[2];
		String workloadFile = args[3];
		rp.packetRate = Integer.parseInt(args[4]);
		String[] workloadFileArray = workloadFile.split("\n");
		rp.connections = rp.addConnections(workloadFile);
		
		 rp.g = new Graph(topologyFile);

		/* 3 algorithms to implement (Dijkstra's):
		*
		*1. least number of hops (take cost of edge to be = 1)
		*2. least delay (cost of edge = propagation delay)
		*
		*3. least loaded (pick route where the 'narrowest' link that can accommodate highest capacity)
		*/
		
		PathFinder pf = new PathFinder();
		
		String currentNode = "A";
		String destinationNode = "A";
		
		String[] packetInfo = rp.connections.firstEntry().getValue().split(" ");
		
		while (!rp.connections.isEmpty()) {//while there are still connections to be routed
			packetInfo = rp.connections.firstEntry().getValue().split(" ");
			//System.out.println(packetInfo[0]);
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
				if(stk!=null) {
					String curNode = stk.pop();
					while(!stk.isEmpty()) {
						rp.g.getLink(curNode, stk.peek()).decreaseLink();
						curNode = stk.pop();
					}
					rp.activePaths.remove(rp.connections.firstEntry().getValue().split(" ",2)[1]);
				}
			} else {
				System.out.println("lahblah");
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
	
	
	public void updateCapacities (HashMap<String, String> s, String dest,String source, String key) {
		String curNode = dest;  
		while (s.get(curNode)!=null) { 
			//System.out.println(curNode + " "+ source);
			curNode = s.get(curNode);
		}
		System.out.println(curNode + " "+ source);
		if(!curNode.equals(source)) {
			//no path!!!!
			//System.out.println("NOPATH cur is "+curNode + " source is "+source);
			return;
		}
		
		curNode= dest;
		HashMap<String, ArrayList<Link>> nodes = this.g.getNodes();
		Stack<String> st = new Stack<String>();
		st.push(curNode);
		while (s.get(curNode)!=null) {  
			g.getLink(curNode, s.get(curNode)).increaseLink();
			curNode = s.get(curNode);
			st.push(curNode);
		}
		activePaths.put(key, st); 
		
	}
	
		

	
	public TreeMap<Float, String> addConnections (String workloadFile) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(workloadFile));
		int connectionNumber = 0;
		String currentPacket;
		float currentPacketTime;
		String currentPacketSource;
		String currentPacketDestination;
		float currentPacketLength;
		float currentPacketEndTime;
		TreeMap<Float,String> connections = new TreeMap<Float,String>();  
		
		while (connectionNumber < 10) {
			currentPacket = sc.nextLine();
			//System.out.println("currentpacket is " + currentPacket);
			currentPacketTime = Float.parseFloat((currentPacket.split(" "))[0]);
			currentPacketSource = (currentPacket.split(" "))[1];
			currentPacketDestination = (currentPacket.split(" "))[2];
			currentPacketLength = Float.parseFloat((currentPacket.split(" "))[3]);
			currentPacketEndTime = currentPacketLength + currentPacketTime;
			connections.put(currentPacketTime, "S " + currentPacketSource + " " + currentPacketDestination + " " + connectionNumber + " " + currentPacketLength);
			connections.put(currentPacketEndTime, "E " + currentPacketSource + " " + currentPacketDestination + " " + connectionNumber + " " + currentPacketLength);
			connectionNumber++;
			//System.out.println(connections.firstEntry());
			//connections.remove(connections.firstEntry().getKey());
		}
		return connections;
		
	}
	
	public float getAverageHops (ArrayList<Integer> hops) {
		float averageHops = 0;
		for (Integer hop : hops) {
			averageHops = averageHops + hop;
		}
		//System.out.println("HOPS1 HOPS HOPS ARE " + averageHops + " " + hops.size());
		averageHops = averageHops / hops.size();
		//System.out.println("HOPS HOPS HOPS ARE " + averageHops);
		return averageHops;
		
	}
	
	public float getAverageDelay (ArrayList<Float> delays) {
		float averageDelay = 0;
		for (Float hop : delays) {
			averageDelay = averageDelay + hop;
		}
		//System.out.println("HOPS1 HOPS HOPS ARE " + averageHops + " " + hops.size());
		averageDelay = averageDelay / delays.size();
		//System.out.println("HOPS HOPS HOPS ARE " + averageHops);
		return averageDelay;
	}
}
