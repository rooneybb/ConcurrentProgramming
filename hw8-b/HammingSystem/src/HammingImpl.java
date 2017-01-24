/*
 * @author Brendan Rooney
 * Not done, there are still repeating numbers and it does not remove duplicates.  
 * Due to this, the 60 numbers it prints out are not the first 60 numbers of the Hamming Calculation.
 * Rather they are a collection of the first 60 numbers that would appear.
 * Also because I ran out of time, some of the other print statements are on for debugging purposes.
 * Also there are almost no comments added.  
 * The two screenShots included show the MultiplyThread LinkedBlockingQueues
 * and the partial results from the PrintThread.
 */

import java.util.concurrent.*;


public class HammingImpl {
	
	
	
	
	
	
	public static void main(String[] args) {
		BlockingQ bq = new BlockingQ();
		/*LinkedBlockingQueue<Integer> hammingOut_2 = new LinkedBlockingQueue<>(20);
		LinkedBlockingQueue<Integer> hammingOut_3 = new LinkedBlockingQueue<>(20);
		LinkedBlockingQueue<Integer> hammingOut_5 = new LinkedBlockingQueue<>(20);
		LinkedBlockingQueue<Integer> hammingIn_2 = new LinkedBlockingQueue<>(20);
		LinkedBlockingQueue<Integer> hammingIn_3 = new LinkedBlockingQueue<>(20);
		LinkedBlockingQueue<Integer> hammingIn_5 = new LinkedBlockingQueue<>(20);
		LinkedBlockingQueue<Integer> hammingMergeOutput = new LinkedBlockingQueue<>(61);
		LinkedBlockingQueue<Integer> hammingCopyOutput = new LinkedBlockingQueue<>(60); */
		
		/*try {
			bq.hammingMergeOutput.put(1);
			hammingIn_2.put(1);
			hammingIn_3.put(1);
			hammingIn_5.put(1);
		}
		catch (InterruptedException ex) {
			System.out.println("Main class, interrupted exception");
		} */
		
		ExecutorService pool = Executors.newFixedThreadPool(6);
		
		MultiplyThread mT2 = new MultiplyThread(2, bq.hammingIn_2, bq.hammingOut_2);
		MultiplyThread mT3 = new MultiplyThread(3, bq.hammingIn_3, bq.hammingOut_3);
		MultiplyThread mT5 = new MultiplyThread(5, bq.hammingIn_5, bq.hammingOut_5);
		MergeThread mergeT = new MergeThread(bq.hammingOut_2, bq.hammingOut_3, bq.hammingOut_5, bq.hammingMergeOutput);
		OutCopyThread ocT = new OutCopyThread(bq.hammingMergeOutput, bq.hammingCopyOutput, bq.hammingIn_2, bq.hammingIn_3, bq.hammingIn_5);
		PrintThread pT = new PrintThread(bq.hammingCopyOutput, pool);
		
		pool.execute(ocT);
		pool.execute(mT2);
		pool.execute(mT3);
		pool.execute(mT5);
		pool.execute(mergeT);
		
		pool.execute(pT);
		
		
	}

}
