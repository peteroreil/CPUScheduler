package ie.dit.scheduler.producerConsumer;

import ie.dit.scheduler.priorityQueue.PriorityQueue;
import ie.dit.scheduler.priorityQueue.Process;

public class ReadyQueue extends PriorityQueue
{
	public static boolean isReady;	
	private boolean queueNotReady;
	private boolean producerFinished;
	private boolean consumerFinished;
	
	public ReadyQueue()
	{
		super(10);
		isReady = true;
		queueNotReady   = true;
	}	
		
	
	public synchronized void producerEnqueueReadyQueue(Process process)
	{
		if(queueIsFull())
		{
			System.out.println("Producer Waiting");
			queueNotReady = false;
			notify();
			waitForNotify();
		}
		
		consumerFinished = false;
		offer(process);
		
				
		if(producerFinished)
		{
			notify();
		}
		
	}
	
	private boolean queueIsFull() 
	{
		return size()==10;
	}
	
	
	public synchronized void consumerEnqueueReadyQueue(Process process)
	{
		offer(process);
//		frame.addProcessToTextArea(process, frame.getReadyQueueTextArea());
//		frame.pack();
	}
	
	
	public synchronized Process dequeueReadyQueue()
	{
		if(queueNotReady&&!producerFinished)
		{
			System.out.println("Scheduler Waiting");
			waitForNotify();
		}
			
		Process process = head();
		
		
		
		if(process==null)
		{
			consumerFinished = true;
		}
		
		if(process==null&&!producerFinished)
		{
			notify();
			waitForNotify();
		}
		
		if(consumerFinished&&producerFinished)
		{
			ReadyQueue.isReady = false;
		}
		
		return process;
	}

	private void waitForNotify()
	{
		try {
				wait();
			} 
		
		catch (InterruptedException e){}
	}
			
	public boolean isProducerFinished()
	{
		return producerFinished;
	}
	
	public void setProducerFinished(boolean producerFinished)
	{
		this.producerFinished = producerFinished;
	}
}

