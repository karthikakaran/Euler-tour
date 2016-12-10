//===========================================================================================================================
//	Program : Class to represent a graph, sample provided by Professor rbk
//===========================================================================================================================
//	@author: Karthika Karunakaran
// 	Date created: 2016/10/02
//===========================================================================================================================
//package eulerTour;

import java.util.*;

public  class Graph implements Iterable<Vertex> {
	List<Vertex> v; // vertices of graph
	int size; // number of vertices in the graph
	boolean directed; // true if graph is directed, false otherwise
	static int saturation = 0;
	//static HashMap<Integer, TreeSet<Integer>> vertexVerify = new HashMap<Integer, TreeSet<Integer>>();
	HashMap<String, Boolean> verifyHelper = new HashMap<String, Boolean>();
	//HashMap to index the previous node of the vertex
	HashMap<Vertex, Entry> ht = new HashMap<Vertex, Entry>();
	
	/**
	 * Constructor for Graph
	 * 
	 * @param size
	 *            : int - number of vertices
	 */
	Graph(int size) {
		this.size = size;
		this.v = new ArrayList<>(size + 1);
		this.v.add(0, null); // Vertex at index 0 is not used
		this.directed = false; // default is undirected graph
		// create an array of Vertex objects
		for (int i = 1; i <= size; i++)
			this.v.add(i, new Vertex(i));
	}

	/**
	 * Find vertex no. n
	 * 
	 * @param n
	 *            : int
	 */
	Vertex getVertex(int n) {
		return this.v.get(n);
	}

	/**
	 * Method to add an edge to the graph
	 * 
	 * @param a
	 *            : int - one end of edge
	 * @param b
	 *            : int - other end of edge
	 * @param weight
	 *            : int - the weight of the edge
	 */
	void addEdge(Vertex from, Vertex to, int weight) {
		Edge e = new Edge(from, to, weight);
		if (this.directed) {
			from.adj.add(e);
			to.revAdj.add(e);
		} else {
			from.adj.add(e);
			to.adj.add(e);
		}
		from.degree = from.degree + 1;
		to.degree = to.degree + 1;
		verifyHelper.put(Math.min(from.name, to.name) + "-" + Math.max(from.name, to.name), false);
	}

	/**
	 * Method to create iterator for vertices of graph
	 */
	public Iterator<Vertex> iterator() {
		Iterator<Vertex> it = this.v.iterator();
		it.next(); // Index 0 is not used. Skip it.
		return it;
	}

	/** Procedure to fetch unseen edge
	 * Moving pointers for each vertex, the edges are checked if visited or not then moved
	 * @param v : vertex for which unseen egde is to be found
	 * @return edge : returns the unseen edge 
	 */
	public Edge unseenEdge(Vertex v) {
		//Moving pointers for each vertex, the edges are checked if visited or not then moved
		while (v.currentIndex < v.adj.size()) {
			if (v.adj.get(v.currentIndex).seen == false)
				return v.adj.get(v.currentIndex++);
			else 
				v.currentIndex++;
		}
		return null;
	}

	/** Procedure to break graph into tours
	 * Form tours by starting at 1 vertex and end in the same 
	 * when the vertex has no un visited edge, next start from a vertex from the formed tour
	 * @param g : graph to break into tours
	 * @return resultList : List<CircularLinkedList<Vertex>> the list of tours
	 */
	public List<CircularLinkedList<Vertex>> breakGraphIntoTours(Graph g) {
		int saturationLocal = saturation;
		Queue<Vertex> queue = new ArrayDeque<>();
		List<CircularLinkedList<Vertex>> resultList = new ArrayList<>();
		Vertex nextVertex = null;
		Vertex v = g.getVertex(1);
		Edge e = null;
		CircularLinkedList<Vertex> tours = new CircularLinkedList<>();
		do {
			tours = new CircularLinkedList<>();
			while (v.degree > 0) {
				e = unseenEdge(v);
				if (e != null) {
					tours.add(v);
					nextVertex = e.otherEnd(v);
					e.seen = true;
					saturationLocal--;
					nextVertex.degree--;
					v.degree--;
					if(nextVertex.degree > 0) {
						queue.add(nextVertex);
					}
					v = nextVertex;
				}
			}
			resultList.add(tours);
			//To start next tour
			while (!queue.isEmpty()) {
				v = queue.poll();
				if (v.degree > 0)
					break;
			}
		} while (saturationLocal > 0);
		return resultList;
	}
	
	/** Procedure to indexing
	 * Store the vertex and its address
	 * @param vert : vertex to store
	 * @param tour : to find the reference of the vertex
	 */
	void createIndexOfChain (Vertex vert, CircularLinkedList<Vertex> tour) {
		//Entry head = tour.header;
		Entry prev = tour.header;
		Iterator<Vertex> it = tour.iterator();
		while (it.hasNext()) {
			if (it.next() == vert && !ht.containsKey(vert)){				
				ht.put(vert, prev);
				break;
			}
			prev = prev.next;
		}
	}
	
	/** Procedure to stitch the formed tours
	 * @param listOfTours : List<CircularLinkedList<Vertex>> list of tours
	 * @return finalTour  : CircularLinkedList<Vertex> of final euler tour
	 */
	CircularLinkedList<Vertex> stitchTours(List<CircularLinkedList<Vertex>> listOfTours) {
		// final result variable
		CircularLinkedList<Vertex> finalTour = new CircularLinkedList<>();
		
		// from each group
		int index = 0, count = 1;
		while(ht.size() < listOfTours.size() - 1) {
			finalTour = listOfTours.get(index);
			for (Vertex v : finalTour) {
				//index = 1; 
				while (count < listOfTours.size()) {
					if(listOfTours.get(count).header.next.element == v) {
						createIndexOfChain(v, finalTour);
						count++;
					} else
						break;
				}
				if (ht.size() == listOfTours.size() - 1)
					break;
			}
			index++;
		}
		
		index = 1;
		finalTour = listOfTours.get(0);
		while (index < listOfTours.size()) {
			// Fetch the point/vertex index to merge the second circular list
			Vertex firstVertexSecondList = (Vertex) listOfTours.get(index).header.next.element;
			Entry indexEntry = ht.get(firstVertexSecondList);
			// Handle when not indexed
			if (indexEntry == null) {
				System.err.println("Cannot merge");
				System.exit(0);
			}
			
			finalTour.mergeList(indexEntry, listOfTours.get(index));
			index++;
		}
		return finalTour;
	}

	/** Procedure to verify the formed tours
	 * @param g : graph to be verfied
	 * @param tour : euler tour formed
	 * @return true/false  : boolean to mention the tour is valid or invalid
	 */
	public boolean verifyTour(Graph g, CircularLinkedList<Vertex> tour) {
		int t = 0;		
		/*Vertex from = null, to = null;
		for (Vertex checkVertex : tour) {
			if (t++ == 0) from = checkVertex;
			else {
				to = checkVertex;
				TreeSet<Integer> list = vertexVerify.get(from.name);
				if (!list.contains(to.name)) {
					System.out.println("Not a valid tour");
					return false;
				} 
				from = to;
			}
		}
		if (t != saturation) {
			System.out.println("Not a Valid tour");
			return false;
		}*/
		Vertex from = null, to = null, firstVert = null; 
		String key = "";
		for (Vertex checkVertex : tour) {
			if (t++ == 0) {
				firstVert = checkVertex;
				from = checkVertex;
			}
			else {
				to = checkVertex;
				key = Math.min(from.name, to.name) + "-" + Math.max(from.name, to.name);
				if(verifyHelper.containsKey(key)) {
					verifyHelper.put(key, true);
				} else {
					System.out.println("Not a valid tour, the edge is not present in the input");
					return false;
				} 
				from = to;
			}
		}
		//The last edge
		key = Math.min(firstVert.name, from.name)  + "-" + Math.max(firstVert.name, from.name);
		if(verifyHelper.containsKey(key) && verifyHelper.get(key) == false) {
			verifyHelper.put(key, true);
		}
		
		if (t != saturation) {
			System.out.println("Not a Valid tour");
			return false;
		} else if (verifyHelper.containsValue(false)) {
			System.out.println("Not a Valid tour");
			return false;
		} else {
			System.out.println("Valid tour");
			return true;
		}
	}

	// read a directed graph using the Scanner interface
	public static Graph readDirectedGraph(Scanner in) {
		return readGraph(in, true);
	}

	// read an undirected graph using the Scanner interface
	public static Graph readGraph(Scanner in) {
		return readGraph(in, false);
	}

	public static Graph readGraph(Scanner in, boolean directed) {
		// read the graph related parameters
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph

		// create a graph instance
		Graph g = new Graph(n);
		g.directed = directed;
		saturation = m;
		for (int i = 0; i < m; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			g.addEdge(g.getVertex(u), g.getVertex(v), w);
			/*TreeSet<Integer> l;
			if (vertexVerify.containsKey(u)) {
				l = vertexVerify.get(u);
				l.add(v);
				vertexVerify.put(u, l);
			} else {
				l = new TreeSet<>();
				l.add(v);
				vertexVerify.put(u, l);
			}
			if (vertexVerify.containsKey(v)) {
				l = vertexVerify.get(v);
				l.add(u);
				vertexVerify.put(v, l);
			} else {
				l = new TreeSet<>();
				l.add(u);
				vertexVerify.put(v, l);
			}*/
		}
		in.close();
		return g;
	}

}
