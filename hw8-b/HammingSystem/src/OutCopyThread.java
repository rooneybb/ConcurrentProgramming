import java.util.concurrent.*;

public class OutCopyThread implements Runnable{
	private final LinkedBlockingQueue<Integer> bqIn;
	private final LinkedBlockingQueue<Integer> bqOutCopy;
	private final LinkedBlockingQueue<Integer> bqOut2;
	private final LinkedBlockingQueue<Integer> bqOut3;
	private final LinkedBlockingQueue<Integer> bqOut5;
	private LinkedBlockingQueue<Integer> localCopy = new LinkedBlockingQueue<Integer>();
	
	OutCopyThread(LinkedBlockingQueue<Integer> bqIn, LinkedBlockingQueue<Integer> bqOutCopy, LinkedBlockingQueue<Integer> bqOut2, LinkedBlockingQueue<Integer> bqOut3, LinkedBlockingQueue<Integer> bqOut5){
		this.bqIn = bqIn;
		this.bqOutCopy = bqOutCopy;
		this.bqOut2 = bqOut2;
		this.bqOut3 = bqOut3;
		this.bqOut5 = bqOut5;
		
	}
	
	public void run(){
		while (!Thread.currentThread().isInterrupted()){
			try{
				int x; 
		//		int y;
				

				//	if (x > y && x != y  || y = null) {;
						x = bqIn.take();
						bqOut2.put(x);
						bqOut3.put(x);
						bqOut5.put(x);
						localCopy.put(x);
						
						if (localCopy.size() >= 60) {
							for (int i = 0; i < localCopy.size(); i++) {
								bqOutCopy.put(localCopy.take());
								
								//if (i == localCopy.size()){
								//	stop();
							//	}
							}
							
						}
				//		y = x;
					//	x = bqIn.take();
			//		}

		//		for (int i = 0; i < bqIn.size(); i++) {
					
			//	}
				
			}
			catch (InterruptedException ex){
				System.out.println("OutCopyThread Interrupted Exception");
				Thread.currentThread().interrupt();
			}
			
		}
		
	}
	
	public void stop(){
		Thread.currentThread().interrupt();
	}
}
