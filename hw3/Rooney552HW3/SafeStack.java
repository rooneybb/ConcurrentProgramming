/*
 *@Brendan Rooney 
 * CSC552
 */

package ajeffrey.teaching.util.stack;

import java.util.*;

public class SafeStack extends UnsafeStackImpl{  //extends UnsafeStackImpl to make use of decorator patterns and existing Stack Methods
	//protected UnsafeStack contents = UnsafeStack.factory.build(); 
	protected UnsafeStackImpl safeContents;
	protected int version;
	protected int size;
	
	public SafeStack(UnsafeStack unsafeStack) {  //uses decorator pattern to create wrapper of unsafeStack class
		//contents = unsafeStack;
		safeContents = (UnsafeStackImpl) unsafeStack;  //casts UnsafeStack to UnsafeStackImpl to allow access to Contents[]
		version = 0;  //sets initial version to 0
		size = safeContents.size();  //sets size to that of UnsafeStackImpl
	}
	
	public synchronized void push(Object e) {
		version++;  //increases version for every addition to data structure
		safeContents.push(e);  //adds synchronization to UnsafeStakImpl.push()
	}
	
	public synchronized Object pop() throws NoSuchElementException {
		version--; //decreases version for every removal to data structure
		return safeContents.pop();	//adds synchronization to UnsafeStakImpl.pop()	
	}
	
	public synchronized Iterator iterator() {
		return new SafeStackIterator(safeContents.contents, safeContents.size);  //utilizes UnsafeStackImpl Objects
	}
	
	public int size() {
		return size; 
		}
	
	protected class SafeStackIterator implements Iterator {  //InnerClass of SafeStack, allows use of SafeStack.this as lock
		//protected final UnsafeStack safeStackContents;
		protected final Object[] ssContents;  //used to hold UnsafeStack
		protected final int ssSize; //used to note UnsafeStack size
		protected int currentIndex = 0;  //current position of iterator
		protected int currentVersion = version;  //sets currentVersion to last updated version of SafeStack class
		
		public SafeStackIterator (final Object[] ssContents, final int ssSize) {
			this.ssContents = ssContents;  //sets ssContents to UnsafeStackImpl.contents
			this.ssSize = ssSize; //sets ssSize to UnsafeStackImpl.size
		}
		
		
		public Object next() {
			synchronized(SafeStack.this) {  //locks on (super) SafeStack Object
				if (currentVersion != version) {  //ensures the most up to date version of Stack is being iterated 
					throw new ConcurrentModificationException();  //versioning is off, the program is out of sync with its order of operations
				}
				else if (currentIndex >= ssSize) {  //ensures Index is in a legal position in stack
					throw new NoSuchElementException();
				}
				else {
					return ssContents[currentIndex++];  //returns data from the next position in the Stack
				}
			}
		}
		
		public boolean hasNext() {
			synchronized(SafeStack.this) {   //locks on (super) SafeStack Object
				return (currentIndex < ssSize);  //boolean check to make sure there is still data to iterate through in the Stack
			}
		}
		
	}
	
}
 