
public class State  implements Comparable<State>{
	private int cost;
	public String destination;
	public String current;
	
	
	public State(int cost, String current,String destination) {
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
