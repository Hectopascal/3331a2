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
		rp.connections = rp.addConnections(workloadFile);
		
		Graph g = new Graph(topologyFile);

		/* 3 algorithms to implement (Dijkstra's):
		*
		*1. least number of hops (take cost of edge to be = 1)
		*2. least delay (cost of edge = propagation delay)
		*
		*3. least loaded (pick route where the 'narrowest' link that can accommodate highest capacity)
		*/
		
		HashMap<String, String> s;
		HashMap<String, ArrayList<String>> addedPaths = null;
		PathFinder pf = new PathFinder();
		
		String currentNode = "A";
		String destinationNode = "A";
		String tempNode = "A";
		String tempNode2 = "A";
		String[] packetInfo = rp.connections.firstEntry().getValue().split(" ");
		ArrayList<String> path = new ArrayList<String>();
		
		while (!rp.connections.isEmpty()) {//while there are still connections to be routed
			packetInfo = rp.connections.firstEntry().getValue().split(" ");
			System.out.println(packetInfo[0]);
			if (packetInfo[0].equals("S")) {
				//if connection is add {
				//get the route from algo
				System.out.println("ehueuheu");
				currentNode = packetInfo[1];
				System.out.println("current is " + currentNode);
				destinationNode = packetInfo[2];
				System.out.println("destination is " + destinationNode);
				s = pf.findPath(g, currentNode, destinationNode, rp.routingScheme);
				
				tempNode = destinationNode;
				path.add(destinationNode);
				while (!s.isEmpty()) {
					tempNode2 = s.get(tempNode);
					path.add(tempNode2);
					s.remove(tempNode);
					tempNode = tempNode2;
				}
				addedPaths.put(rp.connections.firstEntry().getValue(), path);

			} else {
				System.out.println("lahblah");
			}
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
			//System.out.println("currentpacket is " + currentPacket);
			currentPacketTime = Float.parseFloat((currentPacket.split(" "))[0]);
			currentPacketSource = (currentPacket.split(" "))[1];
			currentPacketDestination = (currentPacket.split(" "))[2];
			currentPacketLength = Float.parseFloat((currentPacket.split(" "))[3]);
			currentPacketEndTime = currentPacketLength + currentPacketTime;
			connections.put(currentPacketTime, "S " + currentPacketSource + " " + currentPacketDestination + " " + connectionNumber);
			connections.put(currentPacketEndTime, "E " + currentPacketSource + " " + currentPacketDestination + " " + connectionNumber);
			connectionNumber++;
			//System.out.println(connections.firstEntry());
			//connections.remove(connections.firstEntry().getKey());
		}
		return connections;
		
	}
}
