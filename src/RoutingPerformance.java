import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RoutingPerformance {
	String networkScheme;
	String routingScheme;
	int packetRate;
	
	public static void main(String[] args) {
		RoutingPerformance rp = new RoutingPerformance();
		rp.networkScheme = args[0];
		rp.routingScheme = args[1];
		
		String topologyFile = args[2];
		String workloadFile = args[3];
		
		//int packetRate = Integer.parseInt(args[4]);
		
		
		Graph g = new Graph(topologyFile);

		/* 3 algorithms to implement (Dijkstras):
		*
		*1. least number of hops (take cost of edge to be = 1)
		*2. least delay (cost of edge = propagation delay)
		*
		*3. least loaded (pick route where the 'narrowest' link that can accomodate highest capacity)
		*/
		
		
		
	}
	
	
}
