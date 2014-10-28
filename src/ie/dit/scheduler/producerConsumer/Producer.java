package ie.dit.scheduler.producerConsumer;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import ie.dit.scheduler.priorityQueue.Background;
import ie.dit.scheduler.priorityQueue.Normal;
import ie.dit.scheduler.priorityQueue.Process;
import ie.dit.scheduler.priorityQueue.TimeCritical;

//The Producer Class Generates a Random Number of Processes (To a maximum amount of 30) of Type TimeCritical, Background, or
//Normal.

public class Producer implements Runnable
{
	private Process[]      randomProcesses;
	private Process        process;
	private ReadyQueue     readyQueue;
	private int            queueLength;
	
	
	public Producer(ReadyQueue readyQueue)
	{
		this.readyQueue = readyQueue;
		generateRandomProcesses();
	}
	
	public int generateRandomProcesses()
	{
		int randomNumber = generateRandomNumberOfProcesses();
		randomProcesses  = new Process[randomNumber];
		
		for(int i = 0; i < randomNumber; i++)
		{
			int processType    = generateRandomProcessType();
			int runningTime    = generateRandomNumberRunningTime();
			this.process       = generateProcess(processType, runningTime);
			randomProcesses[i] = process;
		}
		
		queueLength = randomProcesses.length;
		return randomNumber;
	}
	
	// generates process determined by its intrinsic value
	private Process generateProcess(int type, int milliSeconds)
	{
		Process processHolder = new Background(0);
		
		switch(type)
		{
			case 2 : processHolder = new TimeCritical(milliSeconds); break;
			case 1 : processHolder = new Normal(milliSeconds);       break;
			case 0 : processHolder = new Background(milliSeconds);	break;	
		}
		
		return processHolder;		
	}

	// total number of random processes to be generated (1-30)
	private int generateRandomNumberOfProcesses()
	{
		return (int)((Math.random()*30)+10);
	}
	
	// generates process run times of between (1-60)
	private int generateRandomNumberRunningTime()
	{
		return ((int)((Math.random()*60)+1))*1000;
	}
	
	// generates random values which is used to determine process type (0-2)
	private int generateRandomProcessType()
	{
		return (int)((Math.random()*3));
	}
	
	public Process[] getRandomList()
	{
		return this.randomProcesses;
	}

	@Override
	public void run()
	{
		System.out.println("In Producer Run Method");
		enqueueReadyQueue();		
	}

	public ReadyQueue getReadyQueue()
	{
		return this.readyQueue;
	}
	
	public void enqueueReadyQueue()
	{
		int counter = 0;
				
		while(counter<queueLength)
		{			
			Process process = randomProcesses[counter];
			counter++;
			
			if(counter==queueLength)
			{
				readyQueue.setProducerFinished(true);
			}
			
			readyQueue.producerEnqueueReadyQueue(process);
			
		}
	}
	
	/// Testing Methods Only
	
	public void setRandomProcesses(Process[] process)
	{
		this.randomProcesses = process;
	}
	
	public void setQueueLength(int length)
	{
		this.queueLength = length;
	}
	
	public void createFrameToDisplayProcesses()
	{
		Process[] processList  = this.getRandomList();
		JFrame window          = new JFrame("Random Processes Created");
		JPanel containerPanel  = new JPanel(new BorderLayout());
		JPanel panel           = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);
		
		window.setSize(500,500);
	    panel.setLayout(new MigLayout());
		
		for(Process process : processList)
		{
			String name  = process.getName();
			int runTime  = process.getProcessTime();
			String state = process.getStringState();
			panel.add(new JLabel("Process Type: "+name+"\t  RunTime: "+runTime+ "\t  State:  "+state),"wrap paragraph");
		 
		}
		
		containerPanel.add(scrollPane, BorderLayout.CENTER);
		
		window.add(containerPanel);
		//window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
}
