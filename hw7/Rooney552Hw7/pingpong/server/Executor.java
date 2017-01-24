package ajeffrey.teaching.pingpong.server;

import ajeffrey.teaching.debug.Debug;

import java.util.concurrent.*;

/**
 * An Executor class for executing tasks.
 * @author Alan Jeffrey
 * @version 1.0.1
 */

public interface Executor {

    /**
     * Try to execute a given task.
     * The task will be run, if system resources permit.
     * If the system is too busy, then the task will be cancelled.
     * @param task the task to execute
     */
    public void execute (Task task);

    /**
     * An executor.
     */
    public Executor singleton = new ExecutorImpl ();  //singleton ensures only one pool is created with a strict limit of 50 threads

}

class ExecutorImpl implements Executor {

    // This is a simplistic executor which just builds a new
    // thread for each task.  This is not realistic!

    public void execute (Task task) {
	Debug.out.println ("Executor.execute: Starting");
	//ThreadPoolExecutor fixedExec = new ThreadPoolExecutor(40, 50, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50));  // was getting alot errors like on the next line:
	//Error: Caught java.net.SocketException: No buffer space available (maximum connections reached?): connect
	ExecutorService fixedExec = Executors.newFixedThreadPool(50);  //creates fixed thread pool of 50 threads 
	fixedExec.execute(task);  //fixed thread pool runs runnable task objects
	//final Thread thread = new Thread (task);  //gets rid of new thread calls in code
	//thread.start ();    //gets rid of new thread calls in code
	Debug.out.println ("Executor.execute: Returning");
    }

}
