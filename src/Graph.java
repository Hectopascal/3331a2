import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {
	HashMap<String, ArrayList<Link>> nodeList;
	ArrayList<Link> links;
	
	public Graph(String topologyFile) {
		nodeList = new HashMap<String, ArrayList<Link>>();
		String line;
		try {
			Scanner sc = new Scanner(new File(topologyFile));
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				String[] parts = line.split(" ",-1);
				//System.out.println(parts[0] + "#" + parts[1] + "#" + parts[2] + "#" + parts[3]);

				Link newLink = new Link(parts[0],parts[1],Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),0);
				if(!nodeList.containsKey(parts[0])) nodeList.put(parts[0],  new ArrayList<Link>());
				if(!nodeList.containsKey(parts[1])) nodeList.put(parts[1],  new ArrayList<Link>());
				nodeList.get(parts[0]).add(newLink);
				nodeList.get(parts[1]).add(newLink);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		printGraph();
	}
	
	public HashMap<String, ArrayList<Link>> getNodes(){
		return nodeList;
	}
	
	public ArrayList<Link> getLinks(){
		return links;
	}
	/**
	 * Prints graph
	 */
	public void printGraph() {
		for(String node : nodeList.keySet()) {
			ArrayList<Link> links = nodeList.get(node);
			System.out.println("Links on Node "+node+":");
			for(Link l : links) {
				l.printLink();
			}
		}
	}
}
