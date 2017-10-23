import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;


public class PathFinder {
	
	public HashMap<String,String> findPath (Graph g, String source, String dest, String routingScheme) {
		//TODO
		if (routingScheme.equals("SHP")) {
			return findPathSHP(g,source,dest);
		} else if (routingScheme.equals("LLP")) {
			return findPathLLP(g,source,dest);
		}
		
		return findPathSDP(g, source, dest); //SDP
	}
	public HashMap<String,String> findPathSHP(Graph g, String sourceNode, String destNode) {
		HashMap<String,String> prevNode = new HashMap<String,String>();
		HashMap<String, Integer> nodeCosts = new HashMap<String, Integer>();
		HashMap<String,ArrayList<Link>> nodeList = new HashMap<String,ArrayList<Link>>(g.getNodes());
		
		for(String key : nodeList.keySet()) {
			nodeCosts.put(key, Integer.MAX_VALUE);
			prevNode.put(key, null);
		}
		nodeCosts.put(sourceNode, 0);
		
		//for every node
		while(!nodeList.isEmpty()) {
			String current = getMinimum(nodeList, nodeCosts);
			
			if(current == null) return null;
			ArrayList<Link> neighbours = new ArrayList<Link> (nodeList.get(current));
			nodeList.remove(current);
			
			//for every neighbour
			while(!neighbours.isEmpty()) {
				Link l = neighbours.get(0);
				neighbours.remove(0);
				if(!l.isAvailable()) continue;
				String neighbour = l.otherEnd(current);
				System.out.println("neighbour "+ neighbour + " of cur "+current);
				int optionCost = nodeCosts.get(current)+1;
				if(optionCost<nodeCosts.get(neighbour)) {
					nodeCosts.put(neighbour, optionCost);
					prevNode.put(neighbour, current);
			
				}
				
				
			}
			
		}
		printPath(prevNode, destNode);
		return prevNode;
	}
	
	public HashMap<String,String> findPathSDP(Graph g, String sourceNode, String destNode) {
		HashMap<String,String> prevNode = new HashMap<String,String>();
		HashMap<String, Integer> nodeCosts = new HashMap<String, Integer>();
		HashMap<String,ArrayList<Link>> nodeList = new HashMap<String,ArrayList<Link>>(g.getNodes());
		
		for(String key : nodeList.keySet()) {
			nodeCosts.put(key, Integer.MAX_VALUE);
			prevNode.put(key, null);
		}
		nodeCosts.put(sourceNode, 0);
		
		//for every node
		while(!nodeList.isEmpty()) {
			String current = getMinimum(nodeList, nodeCosts);
			
			if(current == null) return null;
			//if(current.equals(destNode)) return prevNode;
			ArrayList<Link> neighbours = new ArrayList<Link> (nodeList.get(current));
			nodeList.remove(current);
			
			//for every neighbour
			while(!neighbours.isEmpty()) {
				Link l = neighbours.get(0);
				neighbours.remove(0);
				if(!l.isAvailable()) continue;
				String neighbour = l.otherEnd(current);
				System.out.println("neighbour "+ neighbour + " of cur "+current);
				int optionCost = nodeCosts.get(current)+l.getDelay();
				if(optionCost<nodeCosts.get(neighbour)) {
					nodeCosts.put(neighbour, optionCost);
					prevNode.put(neighbour, current);
			
				}
				
				
			}
			
		}
		printPath(prevNode, destNode);
		return prevNode;
	}
	
	public HashMap<String,String> findPathLLP(Graph g, String sourceNode, String destNode) {
			HashMap<String, ArrayList<Link>> nodeList = new HashMap<String,ArrayList<Link>>(g.getNodes());
			PriorityQueue<State> q = new PriorityQueue<State>();
			HashMap<String,State> visited = new HashMap<String,State>();
			State first = new State(2,sourceNode,null);
			q.add(first);
			while(!q.isEmpty()) {
				State cur = q.poll();
				
				if(cur==null) return null; //no path
				if(cur.node.equals(destNode)) {return makePath(visited.get(cur.node));} //done
				
				LinkedList<Link> neighbours = new LinkedList<Link>(nodeList.get(cur.node));
				while(!neighbours.isEmpty()) {
					Link n = neighbours.removeFirst();

					if(!n.isAvailable()) {continue;} //link full
					
					String neighbourNode = n.otherEnd(cur.node);
					State option = new State(getRatio(cur,n),neighbourNode, cur);
					
					 //if hashmap already has this neighbour
					if(visited.containsKey(neighbourNode)) { //if we have visited this path before
						//check if our current option is better
						if(option.highest<visited.get(neighbourNode).highest) {
							visited.put(neighbourNode, option);
						}
						
					} else { //have not visited, add to queue to expand
						visited.put(neighbourNode, option);
						q.add(option);
					}
					 
				}
			}
			
			return null;
		}
	
	/*
	public HashMap<String,String> findPathSDP(Graph g, String sourceNode, String destNode) {
		HashMap<String,Integer> nodeCosts = new HashMap<String,Integer>();
		HashMap<String, String> prevNode = new HashMap<String,String>();
		HashMap<String, ArrayList<Link>> nodeList = new HashMap<String,ArrayList<Link>>(g.getNodes());
		
		//put in first node
		nodeCosts.put(sourceNode, 0);
		prevNode.put(sourceNode, null);
		
		while (!nodeList.isEmpty()) {
			//get minimum cost node from the list
			//System.out.println("source is " + source);
			 String curNode = getMinimum(nodeList, nodeCosts);
			 
			 if(curNode == null) {
				 System.out.println("NO available nodes left");
				 return null; //couldn't find anything else. No path!
			 }
			 
			 
			 //get neighbours
			 LinkedList<Link> neighbours = new LinkedList<Link>(nodeList.get(curNode));
			 //System.out.println("amount of neighbours is " + neighbours.size());
			 nodeList.remove(curNode);
			 
			 //get update distance/cost for each neighbour
			 while (!neighbours.isEmpty()) {
				 Link n = neighbours.removeFirst();

				 if(!n.isAvailable()) {
					 //System.out.println("NOT AVAILABLE");
					 continue;
				 }
				 String neighbourNode = n.otherEnd(curNode);
				 //System.out.println("neighbour is " + neighbourNode);
				 
				 //if hashmap already has this neighbour
				 if (nodeCosts.containsKey(neighbourNode)) {
					 //check if this new path has less cost, if yes, add.
					 if (nodeCosts.get(neighbourNode) > nodeCosts.get(curNode) + n.getDelay()) {
						 //System.out.println("cost less, adding " + neighbourNode);
						 nodeCosts.put(neighbourNode, nodeCosts.get(curNode) + n.getDelay());
						 prevNode.put(neighbourNode, curNode);
					 } else {
						 //System.out.println("doesnt cost less");
					 }
				 } else {
					 System.out.println("doesnt already have this neighbour, adding " + neighbourNode);
					 System.out.println("cost "+ nodeCosts.get(curNode) + " "+n.getDelay());
					 //make new entry and update cost
					 nodeCosts.put(neighbourNode, nodeCosts.get(curNode) + n.getDelay());
					 prevNode.put(neighbourNode, curNode);
				 }
				 
			 }
		}
		
		System.out.println("found");
		printPath(prevNode,destNode);
		return prevNode;
	}
	*/
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
				minimum = key;
				minCost = nodeCosts.get(key);
				//System.out.println(key+" "+nodeCosts.get(key)+ " vs "+minCost);
				
			}
		}
		System.out.println("Choosing min "+minimum+ " "+minCost);
		return minimum;
	}
	public HashMap<String,String> makePath(State s){
		State cur = s;
		HashMap<String,String> path = new HashMap<String,String>();
		while(cur.previous!=null) {
			
			path.put(cur.node, cur.previous.node);
			cur = cur.previous;
			
		}
		return path;
	}
	
	
	
	private double getRatio(State prev, Link toAdd) {
		double ratio = toAdd.ratio();
		if(prev.highest == 2.0) return ratio;
		if(prev.highest > ratio) return prev.highest;
		return ratio;
}
	
	
}