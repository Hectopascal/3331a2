import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeSet;

import org.w3c.dom.NodeList;

public class PathFinder {
	public State findPath(Graph g, String source, String dest) {
		
		PriorityQueue<Link> q = new PriorityQueue<Link>();
		State s = new State(0,source, dest);
		TreeSet<State> toVisit = new TreeSet<State>();
		HashMap<String, ArrayList<Link>> nodeList = g.getNodes();
		while(!q.isEmpty()) {
			 State cur = toVisit.pollFirst();
			 
			 ArrayList neighbours = nodeList.get(cur.current);
			 
		}
		
		
		/*
		 * function Dijkstra(Graph, source):
2      dist[source] ← 0                                    // Initialization
3
4      create vertex set Q
5
6      for each vertex v in Graph:           
7          if v ≠ source
8              dist[v] ← INFINITY                          // Unknown distance from source to v
9              prev[v] ← UNDEFINED                         // Predecessor of v
10
11         Q.add_with_priority(v, dist[v])
12
13
14     while Q is not empty:                              // The main loop
15         u ← Q.extract_min()                            // Remove and return best vertex
16         for each neighbor v of u:                      // only v that is still in Q
17             alt ← dist[u] + length(u, v) 
18             if alt < dist[v]
19                 dist[v] ← alt
20                 prev[v] ← u
21                 Q.decrease_priority(v, alt)
22
23     return dist[], prev[]

    21          public void searchPath(Move start, ArrayList<Move> requiredJobs){
    22                  jobs = requiredJobs; //point at same master list of jobs
    23
    24                  //Initialize
    25                  State s = new State(0,Integer.MAX_VALUE,start,null, requiredJobs, h); //gcost is zero, infinite/max fcost
    26                  TreeSet<State> toVisit = new TreeSet<State>(); //open list
    27                  LinkedList<State> visited = new LinkedList<State>(); //closed list
    28
    29
    30                  ArrayList<Move> req = null; //remaining jobs
    31                  toVisit.add(s);
    32                  int expanded = 0;
    33
    34
    35                  while(!toVisit.isEmpty()){ //TODO
    36
    37                          State cur = toVisit.pollFirst();
    38                          expanded++;
    39                          //System.out.println("************Expanding!!!! " + cur.currentCity());
    40                          req = cur.getRemain();
    41                          if(req.isEmpty()){ //this state has finished
    42                                  constructPath(cur,expanded);
    43                                  return;
    44                          }
    45                          visited.add(cur);
    46
    47
    48                          LinkedList<State> options = cur.getStateOptions();
    49                          while(!options.isEmpty()){
    50                                  State possibleState = options.removeFirst();
    51                                  if(visited.contains(possibleState)) continue; //state already evaluated
    52
    53                                  if(toVisit.contains(possibleState)){
    54
    55                                          Iterator<State> it = toVisit.iterator();
    56                                          while(it.hasNext()){
    57                                                  State toCheck = it.next();
    58                                                  if(toCheck.equals(possibleState) && toCheck.getF()<= possibleState.getF()) break;
    59                                                  if(toCheck.getF() > possibleState.getF()){
    60                                                          toVisit.remove(possibleState); //removes state with same city & same remainingjob
    61                                                          toVisit.add(possibleState); //adds new
    62
    63                                                          break;
    64                                                  }
    65                                          }
    66                                  } else {
    67                                          //System.out.println("Adding to open set");
    68                                          toVisit.add(possibleState);
    69                                  }
    70                          }
    71                  }
    72                  constructPath(null,expanded);
    73                  return;
    74
    75          }
		 */
	
		return s;
	}
	
	
	
}
