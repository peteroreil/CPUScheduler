package ie.dit.scheduler.producerConsumer;

public class Runner 
{
	private ReadyQueue readyQueue;
	private Producer   producer;
	private Scheduler  scheduler;
	private Thread     producerThread;
	private Thread     schedulerThread;
	public Runner()
	{
		readyQueue      = new ReadyQueue();
		producer        = new Producer(readyQueue);	
		scheduler       = new Scheduler(readyQueue);
		
		producer.createFrameToDisplayProcesses();
		
		producerThread  = new Thread(producer);
		schedulerThread = new Thread(scheduler);
		
		producerThread.start();
		schedulerThread.start();
	}
	
	public static void main(String[]args)
	{
		new Runner();
	}
}
