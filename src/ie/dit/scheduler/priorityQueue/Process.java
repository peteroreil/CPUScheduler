package ie.dit.scheduler.priorityQueue;

import java.util.Date;

public class Process implements Comparable<Process> 
{
	private int                  processClassValue;
	private int                  processExecutionTime;
	private String               name;
	private long                 timeStamp;
	private static final String  TIME_CRITICAL = "TIME-CRITICAL";
	private static final String  NORMAL        = "NORMAL";
	private static final String  BACKGROUND    = "BACKGROUND";
	public  static final boolean TERMINATE     = false;
	public  static final boolean READY         = true;
	public  static final boolean WAITING       = true;
	public  static final boolean RUNNING       = true;
	private boolean state;
	private String stringState;
	
	public Process(int priorityValue, int timeValue)
	{
		this.state = READY;
		this.stringState = "READY";
		this.processClassValue      = priorityValue;
		this.processExecutionTime   = timeValue;
		timeStamp                   = Long.MIN_VALUE;
		assignName();
	}
	
	private void assignName()
	{
		switch(processClassValue)
		{
			case 2 : name = TIME_CRITICAL; break;
			case 1 : name = NORMAL       ; break;
			case 0 : name = BACKGROUND   ; break;			
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getProcessTime()
	{
		return this.processExecutionTime;
	}
	
	public int getPriorityValue()
	{
		return processClassValue;
	}
	
	public long getTimeStamp()
	{
		return this.timeStamp;
	}
	
	public boolean isGreaterThan(Process process)
	{
		boolean isGreater = this.processClassValue > process.getPriorityValue();
		
		if(this.processClassValue == process.getPriorityValue())
		{
			isGreater = this.timeStamp<=process.getTimeStamp();
		}
		
		return isGreater; 
	}
	
	public void setTimeStamp()
	{
		sleepForOneMilliSecond(); // to prevent any processObjec having the same timeStamp;
		Date dateObject = new Date();
		timeStamp = dateObject.getTime();
	}

	private void sleepForOneMilliSecond() 
	{
		try 
		{
			Thread.sleep(1);
		} 
		
		catch (InterruptedException e)
		
		{			
			e.printStackTrace();
		}
		
	}

	@Override
	public int compareTo(Process process) 
	{
		int result = 0;
		
		if(this.isGreaterThan(process))
		{
			result = 1;
		}
		
		else if(!this.isGreaterThan(process))
		{
			result = -1;
		}
		
		else{
			result = 0;
		}
		
		return result;
	}
	
	public void setProcessTime(int time)
	{
		this.processExecutionTime = time;
	}

	public void setState(boolean state)
	{
		this.state = state;	
		
		if(state==true)
		{
			stringState = "RUNNING";
		}
		
		else 
		{
			stringState = "TERMINATED";
		}
	}
	
	public boolean getState()
	{
		return this.state;
	}

	public String getStringState() 
	{
		return stringState;
	}
	

}
