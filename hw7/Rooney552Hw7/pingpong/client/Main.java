package ajeffrey.teaching.pingpong.client;

import java.net.Socket;
import java.util.concurrent.*;

import com.macfaq.io.SafeBufferedReader;
import com.macfaq.io.SafePrintWriter;

import java.io.IOException;

import ajeffrey.teaching.io.SocketIO;

import ajeffrey.teaching.debug.Debug;

/**
 * A Ping-Pong client.
 * @author Alan Jeffrey
 * @version 1.0.1
 */

public class Main {

    public static void main (String[] args) {
    ExecutorService cachedExec = Executors.newCachedThreadPool();  //creates CachedThreadPool to prioritize re-using old connections
	final int numClients;
	Debug.out.addPrintStream (System.err);
	if (args.length == 0) {
	    numClients = 10;
	} else {
			numClients = Integer.parseInt (args[0]);	    
	}
	for (int i=0; i<numClients; i++) {
		if (i <= 100){
			Debug.out.println ("Creating client " + i);
		    final Client client = new Client ();
		    cachedExec.execute(client);
		}
		else {
			Debug.out.println("Too many clients connected");
		}
	    
	}
    }

}

class Client implements Runnable {

  //  final Thread thread = new Thread (this);

 //   public void start () { thread.start (); }

    public void run () {
	try {
	    while (true) {
		Debug.out.println ("Making connection");
		final Socket socket = new Socket ("127.0.0.1", 2000);
		final SafePrintWriter out = 
		    SocketIO.singleton.buildSafePrintWriter (socket, "\n");
		final SafeBufferedReader in = 
		    SocketIO.singleton.buildSafeBufferedReader (socket);
		final String welcome = in.readLine ();
		Debug.out.println ("Got " + welcome);
		while (Math.random () > .1) {
		    if (Math.random () > .5) {
			Debug.out.println ("Sending PING");
			out.println ("PING");
		    } else {	
			Debug.out.println ("Sending PONG");
			out.println ("PONG");
		    }
		    final String line = in.readLine ();
		    Debug.out.println ("Got " + line);
		//    thread.sleep ((long)(Math.random () * 1000));    removes thread code
		}
		Debug.out.println ("Quitting");
		out.println ("QUIT");
		socket.close ();
//		thread.sleep ((long)(Math.random () * 2000));    removes thread code
	    }
	} catch (final IOException ex) {
	    Debug.out.println ("Caught " + ex);
	//} catch (final InterruptedException ex) {   removes thread code
	    Debug.out.println ("Caught " + ex);
	}
    }

}
