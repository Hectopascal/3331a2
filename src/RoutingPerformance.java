import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class RoutingPerformance {
	String networkScheme;
	String routingScheme;
	int packetRate;
	TreeMap<Float,String> connections;
	public static void main(String[] args) throws FileNotFoundException {
		RoutingPerformance rp = new RoutingPerformance();
		rp.networkScheme = args[0];
		rp.routingScheme = args[1];
		
		String topologyFile = args[2];
		String workloadFile = args[3];
		
		
		String[] workloadFileArray = workloadFile.split("\n");
		int packetRate = Integer.parseInt(args[4]);
		rp.connections = rp.addConnections(workloadFile);
		
		
		
		//int packetRate = Integer.parseInt(args[4]);
		
		Graph g = new Graph(topologyFile);

		/* 3 algorithms to implement (Dijkstra's):
		*
		*1. least number of hops (take cost of edge to be = 1)
		*2. least delay (cost of edge = propagation delay)
		*
		*3. least loaded (pick route where the 'narrowest' link that can accommodate highest capacity)
		*/
		
		HashMap<String, String> s;
		PathFinder pf = new PathFinder();
		ArrayList<String> route = new ArrayList<String>();
		
		String currentNode;
		String destinationNode;
		while (!rp.connections.isEmpty()) {
			
			//if connection is add {
				//get the route from algo
			if (rp.connections.firstEntry().getValue().split(" ")[0] == "1")
				currentNode = rp.connections.firstEntry().getValue().split(" ")[1];
				destinationNode = rp.connections.firstEntry().getValue().split(" ")[2];
				s = pf.findPath(g, currentNode, destinationNode, rp.routingScheme);
				
				//update the map, add route onto another treemap
				
				//update statistics		
				
				rp.connections.remove(rp.connections.firstEntry().getKey());
			//else if the connection is end
				//update the map, delete route from treemap
			
		}		
		
		
		
		
		///////////////////////LOG//////////////////////////////////////////////////
		int numberOfConnections = workloadFileArray.length;
	}	
	
	public TreeMap<Float, String> addConnections(String workloadFile) throws FileNotFoundException {
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
			currentPacketTime = Float.parseFloat((currentPacket.split(" "))[0]);
			currentPacketSource = (currentPacket.split(" "))[1];
			currentPacketDestination = (currentPacket.split(" "))[2];
			currentPacketLength = Float.parseFloat((currentPacket.split(" "))[3]);
			currentPacketEndTime = currentPacketLength + currentPacketTime;
			connections.put(currentPacketTime, "S " + currentPacketSource + " " + currentPacketDestination + " " + connectionNumber);
			connections.put(currentPacketEndTime, "E " + currentPacketSource + " " + currentPacketDestination + " " + connectionNumber);
			connectionNumber++;
			System.out.println(connections.firstEntry());
			connections.remove(connections.firstEntry().getKey());
		}
		return connections;
		
	}
}
