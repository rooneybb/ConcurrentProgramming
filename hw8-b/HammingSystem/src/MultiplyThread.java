import java.util.concurrent.*;

public class MultiplyThread implements Runnable{
	private final int x;
	private int y;
	private int result;
	private int lastNumber;
	private final LinkedBlockingQueue<Integer> bqIn;
	private final LinkedBlockingQueue<Integer> bqOut;
	
	MultiplyThread(int x, LinkedBlockingQueue<Integer> bqIn, LinkedBlockingQueue<Integer> bqOut) {
		this.x = x;
		this.bqIn = bqIn;
		this.bqOut = bqOut;
		this.lastNumber = 0;
	}
	
	public void run(){
		while(!Thread.currentThread().isInterrupted()) {
		try{
			y = bqIn.take();
			
			if (y != lastNumber) {
			result = x * y;
			System.out.println("Debug: Result of " + x + " multiplier = " + result);
			
			
			bqOut.put(result);
			
			System.out.println("Debug: " + x + " bqOut head = " + bqOut); 
			}
			else {
				System.out.println("Repeat multiplier " + x + " : " + lastNumber);
			}
			lastNumber = y; 
			
			if (bqOut.size() >= 20){
				stop();
			}
		}
		catch (InterruptedException e){
			System.out.println("InterruptedException MultiplyThread");
			Thread.currentThread().interrupt();
		}
		}
		
	}
	
	public void stop(){
		Thread.currentThread().interrupt();
	}

}
