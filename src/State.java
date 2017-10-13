
public class State  implements Comparable<State>{
	public int cost;
	public String current;
	public State prev;
	
	
	public State(int cost, String current,State prev) {
		super();
		this.cost = cost;
		this.current = current;
		this.prev = prev;
	}

	@Override
	public boolean equals(Object object2) {
		State s = (State) object2;
	    return (current==s.current);
	}
	@Override
	public int compareTo(State o) {
		return this.cost - o.cost;
	}
}
