package ajeffrey.teaching.jack;

import ajeffrey.teaching.debug.Debug;
import ajeffrey.teaching.io.DevNull;
import ajeffrey.teaching.util.guard.Guard;

import java.io.PrintWriter;

/**
 * A multi-threaded implementation of the business logic for the Jack
 * application.  This uses a separate thread for each window, and
 * uses a guard to handle suspend/resume.  This is how I would recommend
 * implementing this application!
 * @version 1.0.1
 * @author Alan Jeffrey and Brendan Rooney
 * note: all changes made by Brendan Rooney, noted in line comment with @brooney
 */
public interface GuardedLogic {

    public static LogicFactory factory = new GuardedLogicFactoryImpl ();

}

class GuardedLogicFactoryImpl implements LogicFactory {
    
    public Logic build () { return new GuardedLogicImpl (); }

}

class GuardedLogicImpl implements Logic, Runnable {

    protected final Thread thread = new Thread (this);

    protected PrintWriter out = DevNull.printWriter;

    protected Guard flag = Guard.factory.build (true);

    protected final String message = 
	"\nAll work and no play makes Jack a dull boy.";

    protected int offset = 0;
    
    protected static boolean lockSwitch = true;  //@brooney added static boolean to help create lock through polling, outside of main loop

    protected void printChar () {
    if(lockSwitch == true){  //@brooney use of boolean lock to flip on/off printchar()
    	//thread.notifyAll(); // could not get thread that hit suspend to resume automatically if resume is hit from another thread
    	//notifyAll(); // have to manually press the suspended threads resume button, all other threads are responsive though
    	offset = (offset + 1) % (message.length ());
    	char c = message.charAt (offset);
    	Debug.out.println ("GuardedLogic.printChar (): Printing " + c);
    	out.print (c);
    	out.flush ();
    }
    else {
    	Debug.out.println ("GuardedLogic.printChar (): All Threads Suspended"); //@brooney lets you see what thread is in this else statement 
    /*	try {
    		Debug.out.println ("GuardedLogic.printChar (): All Threads Suspended ");
    		flag.waitForTrue();
    	}
    	catch (InterruptedException ex) {
    	    Debug.out.println ("GuardedLogic.printChar (): Caught exception " + ex);
    	} 
    	*/
    } 
    }

    public void setPrintWriter (final PrintWriter out) {
	Debug.out.println ("GuardedLogic.setPrintWriter (): Starting");
	this.out = out;
	Debug.out.println ("GuardedLogic.setPrintWriter (): Returning");
    }

    public void suspend () {
	Debug.out.println ("GuardedLogic.suspend (): Starting");
	synchronized(flag) {  //@brooney synchronized on Guard object to switch value in Guard, and assign boolean
		flag.setValue (false); 
		lockSwitch = flag.getValue(); 
	}
	try { 
		wait(); 
	}
	catch (InterruptedException ex) { 
	    Debug.out.println ("GuardedLogic.suspend (): Caught exception " + ex);
	} 
	
	Debug.out.println ("GuardedLogic.suspend (): Returning");
	
    }

    public void resume () {
	Debug.out.println ("GuardedLogic.resume (): Starting");
	synchronized(flag){ //@brooney synchronized on Guard object to switch value in Guard, and assign boolean
		flag.setValue (true);
		lockSwitch = flag.getValue();
		notifyAll(); //attempting to notify all threads value is = true, doesn't seem to be working
	}
	
	
	Debug.out.println ("GuardedLogic.resume (): Returning");
    }

    public void run () { 
	Debug.out.println ("GuardedLogic.run (): Starting");
	try {
	    while (true) {
	    	if(lockSwitch == true){  ////@brooney polling if else statement to separate active commands
	    		Thread.sleep (200);
	    		printChar ();
	    	}
	    	else {
	    		
	    		flag.waitForTrue ();
	    	}
		
		
	    }
	} catch (InterruptedException ex) {
	    Debug.out.println 
		("GuardedLogic.run (): Caught exception " + ex);
	}
        Debug.out.println ("GuardedLogic.run (): Returning");
    }

    public void start () {
        Debug.out.println ("GuardedLogic.start (): Starting");
	thread.start ();
        Debug.out.println ("GuardedLogic.start (): Returning");
    }

}
