//===========================================================================================================================
//	Program : Class Entry holds a single node of the list, sample provided by Professor rbk
//===========================================================================================================================
//	@author: Karthika Karunakaran
// 	Date created: 2016/10/02
//===========================================================================================================================
//package eulerTour;
public  class Entry<T> {
	public T element;
	public Entry<T> next;

	Entry(T x, Entry<T> nxt) {
		element = x;
		next = nxt;
	}
}

