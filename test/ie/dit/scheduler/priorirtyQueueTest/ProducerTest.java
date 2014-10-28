package ie.dit.scheduler.priorirtyQueueTest;

import org.junit.Before;
import org.junit.Test;
import ie.dit.scheduler.priorityQueue.Process;
import ie.dit.scheduler.producerConsumer.Producer;

public class ProducerTest 
{
	private Producer producer;
	
	@Before
	public void setUpTest()
	{
		this.producer = new Producer(null);
	}
	
	// just to test randomly generated processes
	@Test
	public void testGenerateRandomProcesses()
	{
		producer.generateRandomProcesses();
		Process[] processes = producer.getRandomList(); 
		
		for(Process p : processes)
		{
			String name        = p.getName();
			int runTime        = p.getProcessTime();
			System.out.println("Process Type :"+name+" RunTime: "+runTime);
		}
	}
}
