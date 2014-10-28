package ie.dit.scheduler.priorityQueue;

public class PriorityQueue implements Queue<Process>
{
	protected Process[] processList;
	private int       listSize;
	private int       queuePointer;
	private int       heapSize;
	
	public PriorityQueue(int listSize)
	{
		this.listSize     = listSize;
		this.processList  = new Process[listSize];	
		this.queuePointer = -1;
	}
	
	public Process[] getProcessList()
	{
		return processList;
	}
	

	@Override
	public void clear() 
	{
		processList  = new Process[listSize];
	    queuePointer = -1;
	}

	@Override
	public boolean isEmpty() 
	{
		return size()==0;
	}

	@Override
	public int size()
	{	
		int count = 0;
		
		for(int i = 0; i<processList.length; i++)
		{
			if(processList[i]!=null)
			{
				count++;
			}
		}
		
		return count;
	}

	
	@Override
	public boolean offer(Process process) 
	{
		boolean offered = true;
		
		try
		{
			offered = add(process);
		}
		
		catch(IllegalStateException ise)
		{
			return false;
		}
		
		return offered;
	}

	@Override
	public Process peek() {
		
		buildMaxHeap(processList);
		return processList[0];
	}

	@Override
	public Process head()
	{
		Process headOfList = null;
						
		try{
			
			if((size()>=2 && processList[0]==null)||(processList[0]==null&&size()<processList.length))
			{
				moveAllProccessesUpList();
			}
			
			if(size()>1)
			{
			    buildMaxHeap(processList);
			}
			
			if(size()==1)
			 {
				if(processList[0]==null)
				{
					processList[0] = processList[1];
				}
				
				headOfList = processList[0];
				processList[0] = null;
			}
			
			
			if(size()>1)
			{
				 headOfList     = processList[0];
				 processList[0] = null;
			}		
			 
			 
			 if(headOfList!=null&&size()>1)
			 {
				 moveAllProccessesUpList();
			 }
			 
			
						 
		}catch(NullPointerException npe)
		{
			return null;
			
		}catch(ArrayIndexOutOfBoundsException aioobe)
		{
			return null;
		}		
		
		
		return headOfList;
	}
	
	private void moveAllProccessesUpList()
	{
		if(processList[0]== null)
		{
			Process[] processListTwo = new Process[processList.length-1];
			
			for(int i = 1; i<processList.length;i++)
			{
				processListTwo[i-1] = processList[i];
			}
			
			if(processList.length==1)
			{
				processListTwo[0] = processList[0];
			}
						
			processList  = processListTwo;
			
			if(processList[0]!=null) 
			{
				queuePointer = queuePointer-1;				
			}
			
		}
	}

	@Override
	public boolean add(Process process)
	{
		boolean addToList = false;
		
		if(listIsNotFull())
		{
			if(currentListSizeIsLessThanMaximumSize())
			{
				processList = createANewListForExistingAndAdditionalProcesses();
			}
			
			try{			
					processList[queuePointer+1] = process;
					System.out.println(Thread.currentThread().getName()+" enqueue: "+process.getName() + " process time: "+process.getProcessTime() +" process state: "+process.getStringState());
					
			}catch(ArrayIndexOutOfBoundsException aioobe)
			{
				throw new IllegalStateException();
			}
			
			queuePointer++;
			addToList = true;
		}
		
		return addToList;
	}
	
	
	private Process[] createANewListForExistingAndAdditionalProcesses() 
	{
		Process[] processListTwo = new Process[queuePointer+2];
		
		for(int i = 0; i<processList.length; i++)
		{
			processListTwo[i] = processList[i];
		}
		
		return processListTwo;
	}

	private boolean currentListSizeIsLessThanMaximumSize() 
	{
		return processList.length<listSize;
	}

	private boolean listIsNotFull() 
	{
		return queuePointer<listSize;
	}

	public void buildMaxHeap(Process[] processList)
	{
		heapSize = size();
		
		for(int i = (size()/2); i>=0; i--)
		{
			maxHeapify(processList, i);
		}	
	}
	
	private void maxHeapify(Process[] processlist, int index)
	{
		int left    = left(index);
		int right   = right(index);
		int largest = index;
		
		if(left<=heapSize-1 && processList[left].isGreaterThan(processList[index]))
		{
			largest = left;
		}
		
		if(right<=heapSize-1 && processList[right].isGreaterThan(processList[largest]))
		{
			largest = right;
		}
		
		if(largest != index)
		{
			exchangePositionsOfProcesses(index,largest);
			maxHeapify(processList, largest);
		}		
		
	}

	private void exchangePositionsOfProcesses(int index, int largest)
	{
		Process placeHolder  = processList[index];
		processList[index]   = processList[largest];
		processList[largest] = placeHolder;
	}

	private int right(int index) 
	{
		return (index*2)+1;
	}

	private int left(int index) 
	{
		return 2*index;
	}
	
	public void heapSort(Process[] process)
	{
		buildMaxHeap(process);
		
		while(heapSize>0)
		{
			exchangePositionsOfProcesses(0,heapSize-1);
			heapSize--;
			maxHeapify(process,0);
		}
	}


}
