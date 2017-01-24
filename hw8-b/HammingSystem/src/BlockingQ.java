import java.util.concurrent.*;

public class BlockingQ {
	public LinkedBlockingQueue<Integer> hammingOut_2;
	public LinkedBlockingQueue<Integer> hammingOut_3;
	public LinkedBlockingQueue<Integer> hammingOut_5;
	public LinkedBlockingQueue<Integer> hammingIn_2;
	public LinkedBlockingQueue<Integer> hammingIn_3;
	public LinkedBlockingQueue<Integer> hammingIn_5;
	public LinkedBlockingQueue<Integer> hammingMergeOutput;
	public LinkedBlockingQueue<Integer> hammingCopyOutput;
	
	BlockingQ(){
		this.hammingOut_2 = new LinkedBlockingQueue<Integer>(20);
		this.hammingOut_3 = new LinkedBlockingQueue<Integer>(20);
		this.hammingOut_5 = new LinkedBlockingQueue<Integer>(20);
		this.hammingIn_2 = new LinkedBlockingQueue<Integer>(20);
		this.hammingIn_3 = new LinkedBlockingQueue<Integer>(20);
		this.hammingIn_5 = new LinkedBlockingQueue<Integer>(20);
		this.hammingMergeOutput = new LinkedBlockingQueue<Integer>(60);
		this.hammingCopyOutput = new LinkedBlockingQueue<Integer>(60);
		try{
			hammingMergeOutput.put(1);
		}
		catch(InterruptedException ex){
			System.out.print("BlockingQ interrupted exception");
		}  
		
	}
	

}
