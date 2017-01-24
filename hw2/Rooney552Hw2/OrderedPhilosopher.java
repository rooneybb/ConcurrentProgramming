package ajeffrey.teaching.dining;

import ajeffrey.teaching.debug.Debug;

/**
 * A philosopher from the dining philosophers problem.
 * A philosopher thinks, picks up their left-hand fork,
 * picks up their right-hand fork, then eats.
 * Unfortunately, putting a collection of philosophers in a circle
 * can produce deadlock, if they all pick up their lh forks before any
 * of them have a chance to pick up their rh forks.
 * @author Alan Jeffrey
 * @version 1.0.1
 */
public interface DeadlockingPhilosopher {

    /**
     * A factory for building deadlocking philosophers.
     */
    public static final PhilosopherFactory factory 
	= new DeadlockingPhilosopherFactoryImpl ();

}

class DeadlockingPhilosopherFactoryImpl implements PhilosopherFactory {

    public Philosopher build 
	(final Comparable lhFork, final Comparable rhFork, final String name) 
    {
	return new DeadlockingPhilosopherImpl (lhFork, rhFork, name);
    }

}

class DeadlockingPhilosopherImpl implements Runnable, Philosopher {

    final protected Object lhFork;
    final protected Object rhFork;
    final protected String name;
    final protected Thread thread;
    protected Integer lhForkHash; //Int resource to get hash
    protected Integer rhForkHash; //Int resource to get hash
    protected Object hashTieLock; //lock for unlikely case hash resources are equal

    protected DeadlockingPhilosopherImpl
	(final Object lhFork, final Object rhFork, final String name) 
    {
	this.lhFork = lhFork;
	this.rhFork = rhFork;
	this.name = name;
	this.thread = new Thread (this);
	this.lhForkHash = System.identityHashCode(lhFork);  //establishes global order by getting hash of lhFork
	this.rhForkHash = System.identityHashCode(rhFork); //establishes global order by getting hash of rhFork
    }

    public void start () {
	thread.start ();
    }

    public void run () {
	Debug.out.breakPoint (name + " is starting");
	try {
	    while (true) {
	    if (lhForkHash.compareTo(rhForkHash) < 0){  //checks for global order
	    	Debug.out.println (name + " is thinking");  //runs program in original order
			delay ();
			Debug.out.println (name + " tries to pick up " + lhFork);
			synchronized (lhFork) {
			    Debug.out.println (name + " picked up " + lhFork);
			    delay ();
			    Debug.out.println (name + " tries to pick up " + rhFork);
			    synchronized (rhFork) {
				Debug.out.println (name + " picked up " + rhFork);
				Debug.out.println (name + " starts eating");
				delay ();
				Debug.out.println (name + " finishes eating");
			    }
			}
	    }
	    else if (rhForkHash.compareTo(lhForkHash) < 0) { //checks for global order
	    	Debug.out.println (name + " is thinking");  // runs reverse order of original program
			delay ();
			Debug.out.println (name + " tries to pick up " + rhFork);
			synchronized (rhFork) {
			    Debug.out.println (name + " picked up " + rhFork);
			    delay ();
			    Debug.out.println (name + " tries to pick up " + lhFork);
			    synchronized (lhFork) {
				Debug.out.println (name + " picked up " + lhFork);
				Debug.out.println (name + " starts eating");
				delay ();
				Debug.out.println (name + " finishes eating");
			    }
			}
	    }
	    else {  //used in rare case hashes are equal
	    	synchronized(hashTieLock){  //basic object lock
	    		Debug.out.println (name + " is thinking");
				delay ();
				Debug.out.println (name + " tries to pick up " + lhFork);
				synchronized (lhFork) {
				    Debug.out.println (name + " picked up " + lhFork);
				    delay ();
				    Debug.out.println (name + " tries to pick up " + rhFork);
				    synchronized (rhFork) {
					Debug.out.println (name + " picked up " + rhFork);
					Debug.out.println (name + " starts eating");
					delay ();
					Debug.out.println (name + " finishes eating");
				    }
				}
	    	}
	    }
		
	    }
	} catch (final InterruptedException ex) {
	    Debug.out.println (name + " is interrupted");
	}
    }

    protected void delay () throws InterruptedException {
	Thread.currentThread().sleep ((long)(1000*Math.random()));
    }

}
