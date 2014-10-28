package ie.dit.scheduler.producerConsumerTest;

import ie.dit.scheduler.producerConsumer.Producer;
import ie.dit.scheduler.producerConsumer.ReadyQueue;
import ie.dit.scheduler.producerConsumer.Scheduler;
import ie.dit.scheduler.priorityQueue.Background;
import ie.dit.scheduler.priorityQueue.Normal;
import ie.dit.scheduler.priorityQueue.Process;
import ie.dit.scheduler.priorityQueue.TimeCritical;
import org.junit.Before;
import org.junit.Test;

public class QueuingTest 
{
	private Producer producer;
	private Scheduler scheduler;
	private ReadyQueue readyQueue;
	private Process timeCriticalOne;
	private Process timeCriticalTwo;
	private Process timeCriticalThree;
	private Process timeCriticalFour;
	private Process timeCriticalFive;
	private Process timeCriticalSix;
	private Process normalOne;
	private Process normalTwo;
	private Process normalThree;
	private Process normalFour;
	private Process backgroundOne;
	private Process backgroundTwo;
	private Process[] queue;
	
	@Before
	public void setUpTest()
	{
		timeCriticalOne    = new TimeCritical(100);
		timeCriticalTwo    = new TimeCritical(100);
		timeCriticalThree  = new TimeCritical(900);
		timeCriticalFour   = new TimeCritical(100);
		timeCriticalFive   = new TimeCritical(500);
		timeCriticalSix    = new TimeCritical(100);
		normalOne          = new Normal(100);
		normalTwo          = new Normal(100);
		normalThree        = new Normal(500);
		normalFour         = new Normal(800);
		backgroundOne      = new Background(100);
		backgroundTwo      = new Background(900);
		queue              = new Process[12];
		
		timeCriticalOne.setTimeStamp();
		timeCriticalTwo.setTimeStamp();
		normalThree.setTimeStamp();
		normalTwo.setTimeStamp();
		normalFour.setTimeStamp();
		normalOne.setTimeStamp();
		backgroundTwo.setTimeStamp();
		backgroundOne.setTimeStamp();		
		
		queue[0]  = timeCriticalOne;
		queue[1]  = normalThree;
		queue[2]  = backgroundOne;
		queue[3]  = backgroundTwo;
		queue[4]  = normalTwo;
		queue[5]  = normalFour;
		queue[6]  = timeCriticalTwo;
		queue[7]  = timeCriticalThree;
		queue[8]  = normalOne;
		queue[9]  = timeCriticalFour;
		queue[10] = timeCriticalFive;
		queue[11] = timeCriticalSix;
		
		readyQueue = new ReadyQueue();
		producer   = new Producer(readyQueue);
		producer.setRandomProcesses(queue);
		scheduler  = new Scheduler(readyQueue);
					
	}	
		
	
	private void printList(Process[] list)
	{
		for(Process p : list)
		{
			System.out.println(p.getName()+" "+p.getProcessTime());
		}
	}

	
	@Test
	public void shouldEnqueueReadyQueue()
	{
		System.out.println(producer.getRandomList().length);
		producer.setQueueLength(producer.getRandomList().length);
		printList(producer.getRandomList());
		System.out.println("*******************************************************");
		Thread producerThread  = new Thread(producer);
		Thread schedulerThread = new Thread(scheduler);
		producerThread.start();
		schedulerThread.start();
		joinThreads(producerThread,schedulerThread);
	}


	private void joinThreads(Thread producerThread, Thread schedulerThread) 
	{
			try 
			{
				producerThread.join();
				schedulerThread.join();
			} 
			
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}	
	}

}
