import java.util.HashMap;

public class State implements Comparable<State>{

	public double highest;
	public String node;
	public State previous;
	
	/**
	 * 
	 * @param cost
	 * @param current
	 * @param previous
	 */
	public State(double cost, String current, State previous) {
		super();
		this.highest = cost;
		this.node = current;
		this.previous = previous;
	}
	
	

	@Override
	public int compareTo(State s) {
		//if(this.lowest == s.lowest && this.node!=s.node) return -1;
		
		if(this.highest-s.highest >0) return 1;
		if(this.highest-s.highest <0) return -1; 
		
		return 0;
	}


}
