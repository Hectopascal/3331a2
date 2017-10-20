
public class Link {
	private int delay;
	private int capacity;
	private int currentLinks;
	private String end1;
	private String end2;
	
	public Link(String end1, String end2, int delay, int capacity, int currentLinks) {
		super();
		this.delay = delay;
		this.capacity = capacity;
		this.end1 = end1;
		this.end2 = end2;
		this.currentLinks = currentLinks;
	}
	public boolean isAvailable() {
		//System.out.println(end1 + " "+ end2+" Capacity is "+ capacity + "available "+(capacity-currentLinks));
		return (capacity - currentLinks) > 0;
	}
	/**
	 * Returns true if the node is connected to given end
	 */
	public boolean isConnectedTo(String end) {
		return (end.equals(end1) || end.equals(end2));
	}
	
	/**
	 * returns the node on the other end of the link, given this node end
	 * @param thisEnd
	 * @return otherEnd
	 */
	public String otherEnd(String thisEnd) {
		if(end1.equals(thisEnd)) { 
			if(end2.equals(thisEnd)) {
				return ""; //not connected
			} else {
				return end2;
			}
		} else {
			return end1;
		}
	}
	
	/**
	 * Returns true if it is an edge of 2 given nodes
	 * Returns false otherwise
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean isEdgeBetween(String start, String end) {
		if(start.equals(end1) && end.equals(end2)) return true;
		if(start.equals(end2) && end.equals(end1)) return true;
		
		return false;
	}
	
	/**
	 * Prints link in the format
	 * END1 END2 DELAY CAPACITY
	 */
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
	
	public void increaseLink() {
		currentLinks+=1;
	}
	
	public void decreaseLink() {
		currentLinks-=1;
	}

	public int getCurrentLinks() {
		return currentLinks;
	}

	public void setCurrentLinks(int currentLinks) {
		this.currentLinks = currentLinks;
	}
	
}
