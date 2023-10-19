package edu.utdallas.blockingQueue;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFOQueue {

    public Task[] taskPool;
    public int frontQueue, backQueue;
    Object queueFull, queueEmpty;
    public static int arraySize = 100;

    public BlockingFIFOQueue()
    {
        taskPool = new Task[arraySize];
        frontQueue = -1;
        backQueue = -1;
        queueFull = new Object();
        queueEmpty = new Object();
    }

    public void put(Task thisTask)
    {
        if (isQueueFull())
        {
            synchronized (queueFull)
            {
                try {
                    queueFull.wait();
                } catch (InterruptedException e) {

                }
            }
        }
        synchronized (this)
        {
            if (isQueueEmpty())
            {
                frontQueue = backQueue = 0;
            } else {
                backQueue = (backQueue + 1) % arraySize;
            }

            taskPool[backQueue] = thisTask;
            synchronized (queueEmpty)
            {
                if (isQueueFull())
                {
                    queueEmpty.notify();
                }
            }
        }
    }

    public Task take()
    {
        if (isQueueEmpty())
        {
            synchronized (queueEmpty)
            {
                try {
                    queueEmpty.wait();
                } catch (InterruptedException e) {

                }
            }
        }
        synchronized (this)
        {
            Task thisTask = taskPool[frontQueue];
            if (frontQueue == backQueue)
            {
                frontQueue = backQueue = -1;
            } else {
                frontQueue = (frontQueue + 1) % arraySize;
            }

            synchronized (queueFull)
            {
                queueFull.notify();
            }

            return thisTask;
        }
    }

    public boolean isQueueFull()
    {
        return ((backQueue + 1) % arraySize == frontQueue);
    }

    public boolean isQueueEmpty()
    {
        return (backQueue == -1 && frontQueue == -1);
    }

}
