
public class State  implements Comparable<State>{
	private int cost;
	private String destination;
	private String current;
	
	
	public State(int cost, String destination, String current) {
		super();
		this.cost = cost;
		this.destination = destination;
		this.current = current;
	}


	@Override
	public int compareTo(State o) {
		return this.cost - o.cost;
	}
}
