
public class State  implements Comparable<State>{
	public int cost;
	public String destination;
	public String current;
	public State prev;
	
	
	public State(int cost, String current,String destination,State prev) {
		super();
		this.cost = cost;
		this.destination = destination;
		this.current = current;
		this.prev = prev;
	}


	@Override
	public int compareTo(State o) {
		return this.cost - o.cost;
	}
}
