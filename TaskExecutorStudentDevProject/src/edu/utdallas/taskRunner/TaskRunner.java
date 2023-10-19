package edu.utdallas.taskRunner;

import edu.utdallas.blockingQueue.BlockingFIFOQueue;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable{

    BlockingFIFOQueue blockingFIFOQueue;
    public TaskRunner(BlockingFIFOQueue thisQueue)
    {
        blockingFIFOQueue = thisQueue;
    }

    @Override
    public void run()
    {
        while (true) // While there are still tasks to be taken from the Blocking Queue...
        {
            Task newTask = blockingFIFOQueue.take();
            try {
                newTask.execute();
            } catch (Throwable e) {

            }
        }
    }

}
