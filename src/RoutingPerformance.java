import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RoutingPerformance {
	public static void main(String[] args) {
		String networkScheme = args[0];
		String routingScheme = args[1];
		String topologyFile = args[2];
		
		try {
			Scanner topologyScanner = new Scanner(new File(topologyFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String workloadFile = args[3];
		
		try {
			Scanner workloadScanner = new Scanner(new File(workloadFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int packetRate = Integer.parseInt(args[4]);
		
		
	}
}
