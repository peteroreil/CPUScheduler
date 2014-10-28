package ie.dit.scheduler.priorityQueue;

	/**
	* @author Dave Lynch (C) 2011
	*/
	public interface Queue<T> {
	/**
	* Make the Queue empty
	*/
	public void clear();
	/**
	* @return true if the queue is empty, false if it is not.
	*/
	public boolean isEmpty();
	/**
	* @return the count of the number of elements in the queue.
	*/
	public int size();
	/**
	* @param element An element to add to the queue
	* @return True after succeeding in addition, throws IllegalStateException if no space is available.
	*/
	public boolean add(T element);
	/**
	* Offer an element for addition to the queue
	* @param element to add to the queue
	* @return true after successful add, false otherwise
	*/
	public boolean offer(T element);
	/**
	* @return the head of the queue without removing it from the queue
	*/
	public T peek();
	/**
	* @return the head of the queue, removing it from the queue. Return null if the queue is empty.
	*/
	public T head();
	}
