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
        while (true)
        {
            Task newTask = blockingFIFOQueue.take();
            try {
                newTask.execute();
            } catch (Throwable e) {

            }
        }
    }

}
