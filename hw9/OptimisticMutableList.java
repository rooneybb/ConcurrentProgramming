package ajeffrey.teaching.util.list;

import ajeffrey.teaching.debug.Debug;
import java.util.concurrent.atomic.*;

/*
 * @author Brendan Rooney
 * Utilizes an AtomicReference to an ImmutableList and the included ompareAndSet method
 * to achieve an optimistic non-blocking implementation of a MutableList
 */



public class OptimisticMutableList {
	protected ImmutableList contents = ImmutableList.empty;
	public AtomicReference<ImmutableList> atomicContents = new AtomicReference<ImmutableList>(contents);
    

    public void add (final Object element) {
	    Debug.out.println 
		("OptimisticMutableList.add: Starting...");
	    final ImmutableList oldContents = atomicContents.get(); //copies AtomicRef Immutable List into oldContents
	    Debug.out.println ("OptimisticMutableList.add: Calling " + oldContents + ".cons (" + element + ")");
	    final ImmutableList newContents = oldContents.cons (element); //adds element to head of oldContents, sets reference in newContents
	    if (atomicContents.compareAndSet(oldContents, newContents)) { //boolean compareAndSet, checks to make sure oldContents match what they were when thread entered add() method
	    Debug.out.println ("OptimisticMutableList.add: setting contents = " + element); //only if oldContents is the same, is newContents copied and placed as the value of AtomicContents
	    contents = oldContents;
	    }
	    Debug.out.println ("OptimisticMutableList.add: ...done.");
	    Debug.out.println("size after add: " + atomicContents.get().toString());  //added to view what atomicContents looks like as Thread runs
    }

    public void remove (final Object element) {
	    Debug.out.println 
		("OptimisticMutableList.remove: Starting...");
	    final ImmutableList oldContents = atomicContents.get();  //copies AtomicRef Immutable List into oldContents
	    Debug.out.println ("OptimisticMutableList.remove: Calling " + oldContents + ".remove (" + element + ")");
	    final ImmutableList newContents = oldContents.remove (element); //removes element from oldContents, sets reference in newContents
	    if (atomicContents.compareAndSet(oldContents, newContents)){ //boolean compareAndSet, checks to make sure oldContents match what they were when thread entered remove() method
	    Debug.out.println ("OptimisticMutableList.remove: setting contents = " + oldContents);  //only if oldContents is the same, is newContents copied and placed as the value of AtomicContents
	    }
	    Debug.out.println 
		("OptimisticMutableList.remove: ...done.");
	    Debug.out.println("size after remove: " + atomicContents.get().toString()); //added to view what atomicContents looks like as Thread runs
    }

    public Iterator iterator () { 
	return atomicContents.get().iterator (); //utilizes ImmutableList interface to use with AtomicRef Immutable List object
    }

    public int size () {
	return atomicContents.get().size (); //utilizes ImmutableList interface to use with AtomicRef Immutable List object
    }

    public String toString () {
	return "{ atomicContents = " + atomicContents + " }";  //formats debug output of AtomicRef Immutable List object
    }
    
    

}
