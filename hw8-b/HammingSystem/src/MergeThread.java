import java.util.concurrent.*;

public class MergeThread implements Runnable{
	
	LinkedBlockingQueue<Integer> bqIn2;
	LinkedBlockingQueue<Integer> bqIn3;
	LinkedBlockingQueue<Integer> bqIn5;
	LinkedBlockingQueue<Integer> bqOut;
	
	
	MergeThread(LinkedBlockingQueue<Integer> bqIn2, LinkedBlockingQueue<Integer> bqIn3, LinkedBlockingQueue<Integer> bqIn5, LinkedBlockingQueue<Integer> bqOut){
		this.bqIn2 = bqIn2;
		this.bqIn3 = bqIn3;
		this.bqIn5 = bqIn5;
		this.bqOut = bqOut;
	}
	
	public void run(){
		while (!Thread.currentThread().isInterrupted()){
			try{  //may need a way to make sure they are on same number
				int q2 = 1;
				int q3 = 1;
				int q5 = 1;
				
				System.out.println("merge running");
				
				if (bqIn2.peek() != null){
					q2 = bqIn2.take(); 
				}
				
				if (bqIn3.peek() != null){
					q3 = bqIn3.take(); 
				}
				
				if (bqIn5.peek() != null){
					q5 = bqIn5.take(); 
				} 
					

					
					if (q2 < q3 && q2 < q5){
						bqOut.put(q2);
						if (q5 < q3 ){
							bqOut.put(q5);
						}
							else {
								bqOut.put(q3);
							}
					}
					if (q3 < q2 && q3 < q5){
						bqOut.put(q3);
						if (q2 < q5 ){
							bqOut.put(q2);
						}
							else {
								bqOut.put(q5);
							}
					}
					if(q5 < q2 && q5 < q3) {
						bqOut.put(q5);
						if (q2 < q3 ){
							bqOut.put(q3);
						}
							else {
								bqOut.put(q2);
							}
						}
					
					if (bqOut.size() >= 60) {
						stop();
					}
					
					
					}
							
			catch (InterruptedException ex){
				System.out.println("MergeThread Interrupted Exception");
				Thread.currentThread().interrupt();
		}
		}
	}
	
	public void stop(){
		Thread.currentThread().interrupt();
	}

}
