//===========================================================================================================================
//	Program : Class that represents an edge of a Graph, sample provided by Professor rbk
//===========================================================================================================================
//	@author: Karthika Karunakaran
// 	Date created: 2016/10/02
//===========================================================================================================================
//package eulerTour;

public  class Edge {
	Vertex from; // head vertex
	Vertex to; // tail vertex
	int weight;// weight of edge
	boolean seen; // seen or not
	/**
	 * Constructor for Edge
	 * 
	 * @param u
	 *            : Vertex - Vertex from which edge starts
	 * @param v
	 *            : Vertex - Vertex on which edge lands
	 * @param w
	 *            : int - Weight of edge
	 */
	Edge(Vertex u, Vertex v, int w) {
		from = u;
		to = v;
		weight = w;
		seen = false;
	}

	/**
	 * Method to find the other end end of an edge, given a vertex reference
	 * This method is used for undirected graphs
	 * 
	 * @param u
	 *            : Vertex
	 * @return : Vertex - other end of edge
	 */
	public Vertex otherEnd(Vertex u) {
		assert from == u || to == u;
		// if the vertex u is the head of the arc, then return the tail else
		// return the head
		if (from == u) {
			return to;
		} else {
			return from;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if(this.from.name == ((Edge)obj).from.name && this.to.name == ((Edge)obj).to.name)
			return true;
		else 
		    return false;
	}
	
	/**
	 * Return the string "(x,y)", where edge goes from x to y
	 */
	public String toString() {
		return "(" + from + "," + to + ")";
	}

	public String stringWithSpaces() {
		return from + " " + to + " " + weight;
	}
}
