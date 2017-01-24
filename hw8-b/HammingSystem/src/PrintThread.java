import java.util.concurrent.*;

public class PrintThread implements Runnable{
	LinkedBlockingQueue<Integer> bqIn;
	ExecutorService pool;

	
	PrintThread(LinkedBlockingQueue<Integer> bqIn, ExecutorService pool){
		this.bqIn = bqIn;
		this.pool = pool;
	}
	
	public void run(){

		while(!Thread.currentThread().isInterrupted()) {
			try{
			//	int x;
				//if (bqIn.size() >= 10){
				for (int i = 0; i <= 60; i++){
					
					//x = bqIn.take();
					System.out.println(bqIn.take());
				}
				
				
					
			//	}
		//		else {
		//		System.out.println("print is waiting");
					
		//		}
				
				pool.shutdown();
				
			}
			catch (InterruptedException ex){
				System.out.println("PrintThread InterruptedException thrown");
				Thread.currentThread().interrupt();
			}
				
			 
			}

	}
	
}
