//===========================================================================================================================
//	Program : Class to represent a vertex of a graph, provided by Professor rbk
//===========================================================================================================================
//	@author: Karthika Karunakaran
// 	Date created: 2016/10/02
//===========================================================================================================================
//package eulerTour;

import java.util.List;
import java.util.ArrayList;

public  class Vertex {
	int name; // name of the vertex
	boolean seen; // flag to check if the vertex has already been visited
	int distance; // distance of vertex from a source
	List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList
    int currentIndex;
	//degree
	int degree;
	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		name = n;
		seen = false;
		distance = Integer.MAX_VALUE;
		adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); /* only for directed graphs */
		degree = 0;
		currentIndex = 0;
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}
}
