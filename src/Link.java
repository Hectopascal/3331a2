
public class Link {
	private int delay;
	private int capacity;
	private String end1;
	private String end2;
	
	public Link(String end1, String end2, int delay, int capacity) {
		super();
		this.delay = delay;
		this.capacity = capacity;
		this.end1 = end1;
		this.end2 = end2;
	}
	
	public boolean isConnectedTo(String end) {
		return (end == end1 || end == end2);
	}
	
	/**
	 * returns the node on the other end of the link, given this node end
	 * @param thisEnd
	 * @return otherEnd
	 */
	public String otherEnd(String thisEnd) {
		if(end1 !=thisEnd) { 
			if(end2!= thisEnd) return ""; //not connected
			
			return end1;
		} else {
			return end2;
		}
	}
	public boolean isEdgeBetween(String start, String end) {
		if(start == end1 && end == end2) return true;
		if(start == end2 && end == end1) return true;
		
		return false;
	}
	
	public void printLink() {
		System.out.println(end1 + " " + end2 + " "+ delay + " "+capacity);
	}
	
	
	/***************GETTERS AND SETTERS****************/
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getEnd1() {
		return end1;
	}

	public void setEnd1(String end1) {
		this.end1 = end1;
	}

	public String getEnd2() {
		return end2;
	}

	public void setEnd2(String end2) {
		this.end2 = end2;
	}
	
	
}
