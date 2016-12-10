//===========================================================================================================================
//	Program : Read graph, calls to break into graphs, stitch the graphs and verify, sample provided by Professor rbk
//===========================================================================================================================
//	@author: Karthika Karunakaran
// 	Date created: 2016/10/02
//===========================================================================================================================
//package eulerTour;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		Timer time = new Timer();
		Graph g = Graph.readGraph(in);
		System.out.println("Read graph "+time.end());
		
		System.out.println("Input Graph:");
		for (Vertex u : g) {
			if (u.degree %2 != 0) {
				System.out.println("Not an Eulerian tour");
				System.exit(0);
			}
		}
		//Break into tours
		Timer time2 = new Timer();
		List<CircularLinkedList<Vertex>> groupOfTours = g.breakGraphIntoTours(g);
		System.out.println("Time taken for Break graph "+time2.end());
		//Print tour groups
		/*int index = 1;
		for (CircularLinkedList<Vertex> gp : groupOfTours) {
			System.out.println("Group " + index);
			gp.printList();
			index++;
		}*/
		Timer time3 = new Timer();
		//Stitch tours together
		CircularLinkedList<Vertex> eulerTour =  g.stitchTours(groupOfTours);
		System.out.println("Time taken for stiching :: " + time3.end());
		System.out.println("Euler tour :: ");
		eulerTour.printList();
		//Verify tour
		Timer time4 = new Timer();
		System.out.println("Verify :: " + g.verifyTour(g, eulerTour));
		System.out.println("Time taken for Verifying :: " + time4.end());
	}
}
