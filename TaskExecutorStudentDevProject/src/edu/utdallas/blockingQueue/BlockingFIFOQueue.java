package edu.utdallas.blockingQueue;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFOQueue {

    public Task[] taskPool;
    public int frontQueue, backQueue;
    Object queueFull, queueEmpty; // Two lock objects.
    public static int arraySize = 100; // Fixed array length specified by project description.

    public BlockingFIFOQueue()
    {
        taskPool = new Task[arraySize];
        frontQueue = -1;
        backQueue = -1;
        queueFull = new Object();
        queueEmpty = new Object();
    }

    // Adds tasks into the Blocking Queue.
    public void put(Task thisTask)
    {
        if (isQueueFull())
        {
            synchronized (queueFull)
            {
                try {
                    queueFull.wait(); // Queue is full, wait for take().
                } catch (InterruptedException e) {

                }
            }
        }
        synchronized (this)
        {
            if (isQueueEmpty())
            {
                frontQueue = 0;
                backQueue = 0;
            } else {
                backQueue = (backQueue + 1) % arraySize;
            }

            taskPool[backQueue] = thisTask;
            synchronized (queueEmpty)
            {
                if (isQueueFull())
                {
                    queueEmpty.notify(); // Signal waiting take() threads.
                }
            }
        }
    }

    // Returns the next task to be executed from the Blocking Queue.
    public Task take()
    {
        if (isQueueEmpty())
        {
            synchronized (queueEmpty)
            {
                try {
                    queueEmpty.wait(); // Queue is empty, wait for put().
                } catch (InterruptedException e) {

                }
            }
        }
        synchronized (this)
        {
            Task thisTask = taskPool[frontQueue];
            if (frontQueue == backQueue)
            {
                frontQueue = -1;
                backQueue = -1;
            } else {
                frontQueue = (frontQueue + 1) % arraySize;
            }

            synchronized (queueFull)
            {
                queueFull.notify(); // Signal waiting put() threads.
            }

            return thisTask;
        }
    }

    // Checks if the Blocking Queue is full.
    public boolean isQueueFull()
    {
        return ((backQueue + 1) % arraySize == frontQueue);
    }

    // Checks if the Blocking Queue is empty.
    public boolean isQueueEmpty()
    {
        return (backQueue == -1 && frontQueue == -1);
    }

}
