//package eulerTour;
//===========================================================================================================================
//	Program : CircularLinkedList structure, to add, merge and print, structure provided by Professor rbk
//===========================================================================================================================
//	@author: Karthika Karunakaran
// 	Date created: 2016/10/02
//===========================================================================================================================
import java.util.*;

public class CircularLinkedList<T> implements Iterable<T> {

	// Dummy header is used.  tail stores reference of tail element of list
	Entry<T> header, tail;
	int size;

	public CircularLinkedList() {
		header = new Entry<T>(null, null);
		tail = header;
		size = 0;
	}

	public Iterator<T> iterator() { return new SLLIterator(header); }

	private class SLLIterator implements Iterator<T> {
		Entry<T> cursor, prev;
		SLLIterator(Entry<T> head) {
			cursor = head;
			prev = null;
		}

		public boolean hasNext() {
			if (cursor != tail)
				return cursor.next != null;
			else
				return false;
		}

		public T next() {
			prev = cursor;
			cursor = cursor.next;
			return cursor.element;
		}
		
		public void remove() {
			prev.next = cursor.next;
			prev = null;
		}
	}

	// Add new elements to the end of the list
	public void add(T x) {
		tail.next = new Entry<T>(x, null);
		tail = tail.next;
		tail.next = header;
		size++;
	}

	void printList() {
		int time = 0;
		for(Object item: this) {
			System.out.println(item); 
			time++;
		}
		//System.out.println();
		System.out.println("Total no. of edges in the tour :: "+time);
	}
	
	/** Procedure to merge 2 lists
	 * Runs in time O(1) as the references are indexed already
	 * @param indexEntry : address to merge
	 * @param inpCirLst : CircularLinkedList to merge
	 */
	public void mergeList(Entry<T> indexEntry, CircularLinkedList<T> inpCirLst) {
		//Merging the new input circular list to existing list
		Entry<T> temp = indexEntry.next;
		indexEntry.next = inpCirLst.header.next;
		inpCirLst.tail.next = temp;
		inpCirLst.tail = this.tail;
	}
}
