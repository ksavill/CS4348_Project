package edu.utdallas.taskExecutorImpl;

import java.util.LinkedList;
import java.util.Queue;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor
{
	Thread threads[];
	Queue<Task> tasks = new LinkedList<>();

	public TaskExecutorImpl(int threadPoolSize)
	{
		threads = new Thread[threadPoolSize];

		for (int i = 0; i < threadPoolSize; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Task task = tasks.poll();
						if (task != null) {
							task.execute();
						}
						Thread.yield();
					}
				}
			}, "TaskThread" + i);
		}

		for (int i = 0; i < threadPoolSize; i++) {
			threads[i].start();
		}
	}
	
	@Override
	public void addTask(Task task)
	{
		tasks.add(task);
	}

}
