package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingQueue.BlockingFIFOQueue;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.taskRunner.TaskRunner;

public class TaskExecutorImpl implements TaskExecutor {

	BlockingFIFOQueue blockingFIFOQueue;

	public TaskExecutorImpl(int threadPoolSize)
	{
		blockingFIFOQueue = new BlockingFIFOQueue();
		TaskRunner taskRunner = new TaskRunner(blockingFIFOQueue);
		for (int i = 0; i < threadPoolSize; i++)
		{
			Thread newThread = new Thread(taskRunner, "TaskThread" + i);
			newThread.start();
		}
	}
	
	@Override
	public void addTask(Task task)
	{
		blockingFIFOQueue.put(task);
	}

}
