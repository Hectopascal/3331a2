import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;


public class PathFinder {
	
	public HashMap<String,String> findPath (Graph g, String source, String dest, String routingScheme) {
		//TODO
		if (routingScheme == "SHP") {
			return findPathSHP(g,source,dest);
		} else if (routingScheme == "LLP") {
			
		}
		
		return findPathSDP(g, source, dest); //SDP
	}
	public HashMap<String,String> findPathSHP(Graph g, String source, String dest){
		HashMap<String,Integer> nodeCosts = new HashMap<String,Integer>();
		HashMap<String, String> prevNode = new HashMap<String,String>();
		HashMap<String, ArrayList<Link>> nodeList = new HashMap<String,ArrayList<Link>>(g.getNodes());
		
		//put in first node
		nodeCosts.put(source, 0);
		prevNode.put(source, null);
		
		while (!nodeList.isEmpty()) {
			//get minimum cost node from the list
			//System.out.println("source is " + source);
			 String cur = getMinimum(nodeList, nodeCosts);
			 
			 //get neighbours
			 LinkedList<Link> neighbours = new LinkedList<Link>(nodeList.get(cur));
			 //System.out.println("amount of neighbours is " + neighbours.size());
			 nodeList.remove(cur);
			 
			 //get update distance/cost for each neighbour
			 while (!neighbours.isEmpty()) {
				 Link n = neighbours.removeFirst();

				 if(!n.isAvailable()) {
					 System.out.println("NOT AVAILABLE");
					 continue;
				 }
				 String neighbourNode = n.otherEnd(cur);
				 
				 if (nodeCosts.containsKey(neighbourNode)) {
					 //check if this new path has less cost, if yes, add.
					 if (nodeCosts.get(neighbourNode) > nodeCosts.get(cur) + 1) {
						 
						 nodeCosts.put(neighbourNode, nodeCosts.get(cur) + 1);
						 prevNode.put(neighbourNode, cur);
					 } else {
						 //System.out.println("doesnt cost less");
					 }
				 } else {
					 //make new entry and update cost
					 nodeCosts.put(neighbourNode, nodeCosts.get(cur) + 1);
					 prevNode.put(neighbourNode, cur);
				 }
				 
			 }
		}
		
		System.out.println("found");
		printPath(prevNode,dest);
		return prevNode;
	}
	public HashMap<String,String> findPathSDP(Graph g, String source, String dest) {
		HashMap<String,Integer> nodeCosts = new HashMap<String,Integer>();
		HashMap<String, String> prevNode = new HashMap<String,String>();
		HashMap<String, ArrayList<Link>> nodeList = new HashMap<String,ArrayList<Link>>(g.getNodes());
		
		//put in first node
		nodeCosts.put(source, 0);
		prevNode.put(source, null);
		
		while (!nodeList.isEmpty()) {
			//get minimum cost node from the list
			//System.out.println("source is " + source);
			 String cur = getMinimum(nodeList, nodeCosts);
			 
			 if(cur == null) {
				 System.out.println("NO available nodes left");
				 return prevNode; //couldn't find anything else. No path!
			 }
			 
			 //get neighbours
			 LinkedList<Link> neighbours = new LinkedList<Link>(nodeList.get(cur));
			 //System.out.println("amount of neighbours is " + neighbours.size());
			 nodeList.remove(cur);
			 
			 //get update distance/cost for each neighbour
			 while (!neighbours.isEmpty()) {
				 Link n = neighbours.removeFirst();

				 if(!n.isAvailable()) {
					 //System.out.println("NOT AVAILABLE");
					 continue;
				 }
				 String neighbourNode = n.otherEnd(cur);
				 //System.out.println("neighbour is " + neighbourNode);
				 
				 //if hashmap already has this neighbour
				 if (nodeCosts.containsKey(neighbourNode)) {
					 //check if this new path has less cost, if yes, add.
					 if (nodeCosts.get(neighbourNode) > nodeCosts.get(cur) + n.getDelay()) {
						 //System.out.println("cost less, adding " + neighbourNode);
						 nodeCosts.put(neighbourNode, nodeCosts.get(cur) + n.getDelay());
						 prevNode.put(neighbourNode, cur);
					 } else {
						 //System.out.println("doesnt cost less");
					 }
				 } else {
					 //System.out.println("doesnt already have this neighbour, adding " + neighbourNode);
					 //make new entry and update cost
					 nodeCosts.put(neighbourNode, nodeCosts.get(cur) + n.getDelay());
					 prevNode.put(neighbourNode, cur);
				 }
				 
			 }
		}
		
		System.out.println("found");
		printPath(prevNode,dest);
		return prevNode;
	}
	
	public void printPath(HashMap<String,String> path,String destination){
		String key = destination;
		System.out.println("Printing path " );

		System.out.print(key+" " );
		while(path.get(key) != null) {
			System.out.print(path.get(key) + " ");
			key = path.get(key);
		}
		System.out.println("");
	}
	private String getMinimum(HashMap<String, ArrayList<Link>> nodeList, HashMap<String, Integer> nodeCosts) {
		String minimum = null;
		int minCost = Integer.MAX_VALUE;
		for (String key : nodeList.keySet()) {
			if (nodeCosts.containsKey(key) && nodeCosts.get(key)<minCost) {
				//System.out.println(key+" ");
				minimum = key;
				minCost = nodeCosts.get(key);
			}
		}
		
		return minimum;
	}
	
	
	
}
