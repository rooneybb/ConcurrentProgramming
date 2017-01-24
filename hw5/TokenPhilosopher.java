package ajeffrey.teaching.dining;

import ajeffrey.teaching.debug.Debug;
import java.util.concurrent.*;


/**
 * A philosopher from the dining philosophers problem.
 * A philosopher thinks, picks up their left-hand fork,
 * picks up their right-hand fork, then eats.
 * Philosophers acquire a token to sit at the table before they can pick up their forks.
 * There are one less tokens than there are Philosophers. This prevents any potential deadlocking issues. 
 * Tokens are issued through a semaphore and are actually permits.  
 * @author Alan Jeffrey and Brendan Rooney
 * @version 1.0.1
 */
public interface TokenPhilosopher {

    /**
     * A factory for building deadlocking philosophers.
     */
    public static final PhilosopherFactory factory 
	= new DeadlockingPhilosopherFactoryImpl ();

}

class TokenPhilosopherFactoryImpl implements PhilosopherFactory {

    public Philosopher build 
	(final Comparable lhFork, final Comparable rhFork, final String name) 
    {
	return new TokenPhilosopherImpl (lhFork, rhFork, name);
    }

}

class TokenPhilosopherImpl implements Runnable, Philosopher {

    final protected Object lhFork;
    final protected Object rhFork;
    final protected String name;
    final protected Thread thread;
    public static Semaphore tableToken = new Semaphore(-1, true); // creates a semaphore with -1 available tokens (permits)
    

    protected TokenPhilosopherImpl
	(final Object lhFork, final Object rhFork, final String name) 
    {
	this.lhFork = lhFork;
	this.rhFork = rhFork;
	this.name = name;
	this.thread = new Thread (this);
    }

    public void start () {
    tableToken.release(); //increases tokens available to threads by 1 each time a new thread is started, tableToken permit of -1 will allow for n-1 available tokens
	thread.start ();   //cont. where n = number of threads
    }
    
   protected void getToken() throws InterruptedException { //protected method allows for protection on static semaphore
    	tableToken.acquire(); //synchronization provided by java.util.concurrent.semaphore    	 
    }
    
    protected void giveToken() {  //protected method allows for protection on static semaphore
    	tableToken.release(); //synchronization provided by java.util.concurrent.semaphore
    } 


    public void run () {
	Debug.out.breakPoint (name + " is starting");
	
	    while (true) {
	    try{  //changed position of try catch block to include token release in finally block, previosuly finally block was unreachable
	    	Debug.out.println ("tokens before acquire: " + tableToken.availablePermits());  //checks to make sure semaphore is properly setup
	    	getToken();  //acquires token, -1 token from pool
		    Debug.out.println ("tokens after acquire: " + tableToken.availablePermits());  //checks to see tokens available decrease after acquire
			Debug.out.println (name + " is thinking");
			delay ();
			Debug.out.println (name + " tries to pick up " + lhFork);
			synchronized (lhFork) {  //keeps lock on forks to prevent race conditions
			    Debug.out.println (name + " picked up " + lhFork);
			    delay ();
			    Debug.out.println (name + " tries to pick up " + rhFork);
			    synchronized (rhFork) { //keeps lock on forks to prevent race conditions
				Debug.out.println (name + " picked up " + rhFork);
				Debug.out.println (name + " starts eating");
				delay ();
				Debug.out.println (name + " finishes eating");
	    }
	    
		    }
		}
	    	catch (final InterruptedException ex) {
	    		Debug.out.println (name + " is interrupted");
		}
	    	finally {
	    		giveToken();  //releases token, +1 added back to available pool
	    		Debug.out.println ("tokens after release: " + tableToken.availablePermits());  //checks to see tokens available increased
	    	}
	    }

    }

    protected void delay () throws InterruptedException {
	Thread.currentThread().sleep ((long)(1000*Math.random()));
    }

}
