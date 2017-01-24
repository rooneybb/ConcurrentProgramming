package ajeffrey.teaching.test;

import ajeffrey.teaching.util.stack.UnsafeStack;
import java.util.Iterator;
import ajeffrey.teaching.util.stack.SafeStack;

public class TestStack {

    public static void main (String[] args) {

	final UnsafeStack stack2 = UnsafeStack.factory.build ();
    final SafeStack stack = new SafeStack(stack2);
	stack.push ("fred"); stack.push ("wilma"); stack.push ("barney"); stack.push ("betty");
	for (Iterator i = stack.iterator (); i.hasNext ();) {
	    System.out.println (i.next ());
	}
	
    }

}
