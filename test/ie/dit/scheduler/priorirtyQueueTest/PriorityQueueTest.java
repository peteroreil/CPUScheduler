package ie.dit.scheduler.priorirtyQueueTest;

import static org.junit.Assert.*;
import java.util.Arrays;

import ie.dit.scheduler.priorityQueue.*;
import ie.dit.scheduler.priorityQueue.Process;
import org.junit.Before;
import org.junit.Test;


public class PriorityQueueTest
{
	@SuppressWarnings("unused")
	private Process       process;
	private PriorityQueue queue;
	private TimeCritical  timeCriticalOne;
	private TimeCritical  timeCriticalTwo;
	private TimeCritical  timeCriticalThree;
	private TimeCritical  timeCriticalFour;
	private Normal        normalOne;
	private Normal        normalTwo;
	private Normal        normalThree;
	private Background    backgroundOne;
	private Background    backgroundTwo;	
	
	

	private String arrayToString(PriorityQueue queue)
	{
		Process[] processList   = queue.getProcessList();
		StringBuilder processes = new StringBuilder();
		
		for(Process process : processList)
		{
			processes.append(process.getName()+"("+process.getProcessTime()+") "); 
		}
		
		return processes.toString();
	}
	
	
	@Before
	public void setUpTest()
	{
		process            = new Process(0,1000);
		timeCriticalOne    = new TimeCritical(100);
		timeCriticalTwo    = new TimeCritical(1000);
		timeCriticalThree  = new TimeCritical(1);
		timeCriticalFour   = new TimeCritical(1);
		normalOne          = new Normal(1000);
		normalTwo          = new Normal(100);
		normalThree        = new Normal(50);
		backgroundOne      = new Background(1000);
		backgroundTwo      = new Background(900);
		queue              = new PriorityQueue(9);
		
		timeCriticalOne.setTimeStamp();
		timeCriticalTwo.setTimeStamp();
		normalThree.setTimeStamp();
		normalTwo.setTimeStamp();
		normalOne.setTimeStamp();
		backgroundTwo.setTimeStamp();
		backgroundOne.setTimeStamp();
		
		
		
		queue.add(timeCriticalOne);
		queue.add(normalThree);
		queue.add(backgroundOne);
		queue.add(backgroundTwo);
		queue.add(normalTwo);
		queue.add(timeCriticalTwo);
		queue.add(timeCriticalThree);
		queue.add(normalOne);
		queue.add(timeCriticalFour);
		
		
	}
	
	//Test 1
	@Test
	public void shouldAddAllValuesToInCorrectOrder()
	{
		String result = "TIME-CRITICAL(100) NORMAL(50) BACKGROUND(1000) BACKGROUND(900) NORMAL(100)" +
				" TIME-CRITICAL(1000) TIME-CRITICAL(1) NORMAL(1000) TIME-CRITICAL(1) ";
		
		assertEquals(result,arrayToString(queue));
	}
	
	//Test 2
	@Test
	(expected = IllegalStateException.class)
	public void shouldNotAllowToAddProcessesWhenListIsFull()
	{
		queue.add(normalOne);
	}
	
	//Test 3
	@Test
	public void shouldRemoveTheHeadOfTheQueue()
	{
		Process headOfQueue  = queue.head();
		String processName   = headOfQueue.getName();
		double processTime   = headOfQueue.getProcessTime();
		
		String result = processName+" "+processTime;
		assertEquals("TIME-CRITICAL 1.0",result);
		assertNotNull(queue.getProcessList()[0]);
	}
	
	//Test 4
	@Test
	public void shouldAllowToAddProcessesWhenListHeadCalledOnFullList()
	{
		queue.head();
		assertTrue(queue.add(normalOne));
	}
	
	//Test 5
	@Test
	public void shouldReturnTrueIfQueueOfferAfterHead()
	{
		queue.head();
		assertTrue(queue.offer(backgroundOne));
	}
	
	//Test 6
	@Test
	public void shouldReturnFalseIfQueueFullOnOffer()
	{
		assertFalse(queue.offer(backgroundTwo));
	}
	
	//Test 7
	@Test
	public void shouldSeeIfProcessIsGreaterThanOtherProcess()
	{
		assertTrue(timeCriticalOne.isGreaterThan(backgroundOne)); //upper bound
		assertFalse(backgroundOne.isGreaterThan(timeCriticalOne));//lower bound
		assertTrue(timeCriticalOne.isGreaterThan(timeCriticalOne));//if equal choose method caller as greater process.
	}
	
	//Test 8
	@Test
	public void shouldCheckTimeStampForEqualProcessClasses()
	{
		assertTrue(timeCriticalOne.isGreaterThan(timeCriticalTwo));
		assertFalse(timeCriticalTwo.isGreaterThan(timeCriticalOne));
	}
	
	//Test 9
	@Test
	public void shouldAllowHeadWithASingleProcess()
	{
		PriorityQueue queue = new PriorityQueue(5);
		
		Process timeCritical = new TimeCritical(100);			
		queue.add(timeCritical);				
		Process process = queue.head();
		String result = process.getName()+" "+process.getProcessTime();
		assertEquals("TIME-CRITICAL 100", result);		
	}
	
	
	//Test 10
	@Test
	public void shouldMaxHeapWithTwoProcesses()
	{
		PriorityQueue queue = new PriorityQueue(5);
		
		Process timeCriticalUnimportance = new TimeCritical(100);
		Process timeCriticalPriority     = new TimeCritical(0);
		
		timeCriticalUnimportance.setTimeStamp();		
		queue.add(timeCriticalUnimportance);
		queue.add(timeCriticalPriority);		
		Process process = queue.peek();
		String result = process.getName()+" "+process.getProcessTime();
		assertEquals("TIME-CRITICAL 0", result);		
	}
	
	//Test 11
	@Test
	public void shouldMaxHeapWithMultipleProcesses()
	{
		PriorityQueue queue = new PriorityQueue(5);
		
		Process timeCriticalUnimportance    = new TimeCritical(100);
		Process timeCriticalPriority        = new TimeCritical(0);
		Process BackGroundUnimportanceOne   = new Background(100);
		Process BackGroundUnimportanceTwo   = new Background(100);
		Process BackGroundUnimportanceThree = new Background(100);
				
		timeCriticalUnimportance.setTimeStamp();		
		
		queue.add(timeCriticalUnimportance);
		queue.add(timeCriticalPriority);
		queue.add(BackGroundUnimportanceOne);
		queue.add(BackGroundUnimportanceTwo);
		queue.add(BackGroundUnimportanceThree);
		
		Process process = queue.peek();
		String result = process.getName()+" "+process.getProcessTime();
		assertEquals("TIME-CRITICAL 0", result);		
	}
	
	//Test 12
	@Test
	public void shouldReturnNumberOfProcessesOnTheList()
	{
		assertEquals(9,queue.size());
	}
	
	//Test 13
	@Test
	public void shouldReturnCorrectCountAfterRemovingFromList()
	{
		queue.head();
		
		assertEquals(8, queue.size());		
	}
	
	//Test 14
	@Test
	public void shouldAllowRemovalOfTwoHeads()
	{
		queue.head();
		queue.head();
		
		assertEquals(7, queue.size());
	}
	
	//Test 15
	@Test
	public void shoudReturnNullAfterRemovingAllProcesses()
	{
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		queue.add(timeCriticalFour);
		assertNotNull(queue.head());
		assertNotNull(queue.head());
		queue.add(timeCriticalFour);
		assertNotNull(queue.head());
		Process process = queue.head();
		assertNull(process);
	}
	
	//Test 16
	@Test
	public void shouldCheckIfListIsEmpty()
	{
		PriorityQueue newQueue = new PriorityQueue(23);
		
		assertTrue(newQueue.isEmpty());
	}
	
	//Test 17
	@Test
	public void shouldAssertFalseIfFull()
	{
		assertFalse(queue.isEmpty());
		queue.head();
		assertFalse(queue.isEmpty());
	}
	
	//Test 18
	@Test
	public void shouldBuildMaxHeap()
	{
		String result         = "TIME-CRITICAL 1";		
		Process[] processList = queue.getProcessList();
		
		queue.buildMaxHeap(processList);
		Process highestPriorityProcess = queue.peek();
		String name = highestPriorityProcess.getName() +" "+ highestPriorityProcess.getProcessTime();
				
		assertEquals(result,name);
	}
	
	//Test 19
	@Test
	public void shouldHead()
	{
		queue.buildMaxHeap(queue.getProcessList());
		queue.head();
		queue.add(backgroundTwo);
		queue.buildMaxHeap(queue.getProcessList());
		queue.head();
		queue.add(backgroundTwo);
		queue.buildMaxHeap(queue.getProcessList());
		Process process = queue.head();
		String result = process.getName()+" "+process.getProcessTime();
		assertEquals("TIME-CRITICAL 100", result);
	}
	
	//Test 20
	///////////////////////////////////////////// PART A /////////////////////////////////////////////////////////////
	@Test
	public void shouldSortUsingHeapSort()
	{
		String result = "BACKGROUND(1000) BACKGROUND(900) NORMAL(1000) NORMAL(100)" +
				" NORMAL(50) TIME-CRITICAL(1000) TIME-CRITICAL(100) TIME-CRITICAL(1) TIME-CRITICAL(1) ";
		
		Process[] processList = queue.getProcessList();
		queue.heapSort(processList);			
		
		assertEquals(result,arrayToString(queue));		
	}
	
	//Test 21
	@Test
	public void shouldAfterEachHeadReMaxHeapTheList()
	{
		queue.head();
		queue.head();
		queue.head();
		queue.head();
		Process peekProcess = queue.peek();		
		String process = peekProcess.getName()+" "+peekProcess.getProcessTime();
		assertEquals("NORMAL 50",process);		
	}
	
	//Test 22
	@Test
	public void shouldWithPeekReMaxHeapTheList()
	{
		queue.head();
		queue.head();
		queue.head();
		queue.head();
		queue.add(timeCriticalOne);		
		Process peekProcess = queue.peek();		
		String process = peekProcess.getName()+" "+peekProcess.getProcessTime();
		assertEquals("TIME-CRITICAL 100",process);	
	}
	
	//Test 23
	@Test
	public void shouldClearList()
	{
		queue.clear();
		assertEquals(0,queue.size());
		assertNull(queue.head());	
//		queue.add(backgroundTwo);
//		queue.add(backgroundTwo);
//		queue.add(backgroundTwo);
	}
	
	//Test 24
	@Test
	public void shouldSortUsingComparableSort()
	{
		String result = "BACKGROUND(1000) BACKGROUND(900) NORMAL(1000) NORMAL(100)" +
				" NORMAL(50) TIME-CRITICAL(1000) TIME-CRITICAL(100) TIME-CRITICAL(1) TIME-CRITICAL(1) ";
		
		Process[] processList = queue.getProcessList();
		Arrays.sort(processList);		
		assertEquals(result,arrayToString(queue));		
	}
	
	//Test 25
	@Test
	public void shouldPrioritiseNewTimeCriticalAfterAllTimeCriticalsRemoved()
	{
		Process someProcess = new TimeCritical(0);
		queue.head();
		queue.head();
		queue.head();
		queue.add(someProcess);
		Process headProcess = queue.head();		
		String result = headProcess.getName()+" "+headProcess.getProcessTime();		
		assertEquals("TIME-CRITICAL 0",result);
	}
	
	//Test 26
	@Test
	public void shouldTestIfQueueIsFull()
	{
		queue.head();
		assertTrue(!queue.isEmpty());
	}
	
}
