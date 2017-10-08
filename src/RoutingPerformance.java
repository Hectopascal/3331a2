import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RoutingPerformance {
	
	public static void main(String[] args) {
		
		String networkScheme = args[0];
		String routingScheme = args[1];
		String topologyFile = args[2];
		//String workloadFile = args[3];
		//int packetRate = Integer.parseInt(args[4]);
		
		
		Graph g = new Graph(topologyFile);

		
		
	}
	
	
}
