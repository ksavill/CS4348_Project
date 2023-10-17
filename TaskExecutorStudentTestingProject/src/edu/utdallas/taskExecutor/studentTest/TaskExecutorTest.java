package edu.utdallas.taskExecutor.studentTest;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutorImpl.TaskExecutorImpl;

public class TaskExecutorTest
{
    public void runTest()
    {
        // Initialize the executor with 10 threads & a queue size of 100.
        final TaskExecutorImpl taskExecutor = new TaskExecutorImpl(10);

        // Inject 1000 tasks into the executor in a separate thread.
        Runnable inserter = new Runnable() {
            public void run()
            {
                for (int idx = 0; idx < 200; idx++) {
                    // Note that Threads are assigned names.
                    String name = "SimpleTask" + idx;
                    Task myTask = new SimpleTestTask(name);
                    taskExecutor.addTask(myTask);
                    System.out.println("******  Adding Task: " + myTask.getName());
                }
            }
        };

        Thread insertThread = new Thread(inserter);
        insertThread.start();
    }

    public static void main(String args[])
    {
        TaskExecutorTest app = new TaskExecutorTest();
        app.runTest();
    }

}
