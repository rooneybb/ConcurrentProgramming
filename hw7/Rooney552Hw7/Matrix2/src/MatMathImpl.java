import java.util.concurrent.*;

/*
 * Utilized CachedThreadPool to add and multiply matrices.
 * 
 */

interface MatMath{ //changed return type on methods, so this interface is not used
    void multiply(int[][] A, int[][]B, int[][]C);  // multiply A and B into C
    void add(int[][]A, int[][]B, int[][]C);        // add A and B into C
    void print(int[][]A);                          // pretty print A
}

class RowColAddExecutable implements Runnable{ //worker thread

	 // code to calculate entry at [row][col] of the resulting matrix
	 // from an addition

	   int[][] first, second, third;  //third is result
	   int row, col, size;
	   

	  RowColAddExecutable(int[][]first, int [][]second, int [][]third, int row, int col){ 

	     this.first = first;
	     this.second = second;
	     this.third = third;
	     this.row = row;
	     this.col = col;
	   }

	  public void run(){  
		  third[row][col] = first[row][col] + second[row][col];  //runs through Executor Pool
	  }
	 }

class RowColProdExecutable implements Runnable{ //worker thread

	 // code to calculate entry at [row][col] of the resulting matrix
	 // from a multiplication

	   int[][] first, second, third;  //third is result
	   int row, col, size;
	   

	  RowColProdExecutable(int[][]first, int [][]second, int [][]third, int row, int col){

	     this.first = first;
	     this.second = second;
	     this.third = third;
	     this.row = row;
	     this.col = col;
	     this.size = first[0].length;
	   }

	  public void run(){ 
	      for (int k=0; k < size; k++){ 
	          third[row][col] += first[row][k]*second[k][col];  //runs through Executor Pool
	      }
	  }
	 }

public class MatMathImpl {
	
	ExecutorService pool;  //owns a reference to an ExecutorService so actual ExecutorService can be instantiated in main

	
	MatMathImpl(ExecutorService pool){
		this.pool = pool;  //initializes ExecutiveService reference 
	}
	
	
	public int[][] multiply(int[][] A, int[][]B, int[][]C) {
		// multiply A and B into C
		//mxn x nxp = mxp
		//to get each squares value, take coordinate (i.e. C[1][2])
		//A[1][0] x B [0][2]  + A[1][1] (y < n) x B [1] [2] (x < n) + ...
		//m[0 +1 <p]
		int result [][] = new int[A.length][B[0].length]; // holds result of product
		
		if (A.length == B[0].length){ //ensures matrices can be multiplied
			for(int i=0; i < A.length; i++){ // iterates rows
				for(int j=0; j < B[0].length; j++){ // iterates columns
					RowColProdExecutable prodThread = new RowColProdExecutable(A, B, result, i, j); //instantiates worker to be used in every position			       			
					pool.execute(prodThread);  //executes multiplication formula with k depth, carried out by worker thread
					}
				}
			
			
			}
		else 
			System.out.println("Can't multiply Matrix A and Matrix B: do not have compatible # of Rows and Columns");
		pool.shutdown(); //closes out threads
		try{
    		pool.awaitTermination(1000, TimeUnit.MILLISECONDS); //allows threads to finish
    	}
    	catch (InterruptedException e) {
    		System.out.println("threads awaiting termination in multiply interupted");
    	}
		C = result;  // assigns result to C
		print(C);  //prints out final result
		return C;  //returns result int[][]
			
		}
	
	
    public int[][] add(int[][]A, int[][]B, int[][]C){
    	int result [][] = new int[A.length][B[0].length];
    	 if (A.length == B.length && A[0].length == B[0].length) { //ensures they can actually be added together    		 
    		 for(int i = 0; i < A.length; i++){  //iterates rows
        		for(int j = 0; j < B[0].length; j++){ //iterates columns
        			//want a seperate thread for every calculation
        			//threads have to terminate and fill up result Matrix
        			//use MxN threads
        			RowColAddExecutable addThread = new RowColAddExecutable(A, B, result, i, j);//instantiates RowColAdd and creates runnable thread
        			/*System.out.println("A new RowColAdd has been created for position " + i + " : " +j);
        			System.out.println("A[][] = " +  A[i][j]);
        			System.out.println("B[][] = " +  B[i][j]);     */
        			pool.execute(addThread); //gives worker thread task command to thread executor 
        			}
        		}
    		
    		
        	}
    	 else 
    		 System.out.println("Can't add Matrix A and Matrix B: do not have same # of Rows and Columns");
    	pool.shutdown(); //closes out threads
    	try{
    		pool.awaitTermination(1000, TimeUnit.MILLISECONDS);  //allows threads to finish
    	}
    	catch (InterruptedException e) {
    		System.out.println("threads awaiting termination in add interupted");
    	}
 		C = result; //assigns result to C
 		print(C); //prints out final result
    	return C; //returns result int[][]
    }
    		
    	
    public void print(int[][]A) {  //prints out elements for each position of specified matrix
    	for (int i = 0; i < A.length; i++){
    		for (int j = 0; j < A[0].length; j++){
    			System.out.print(A[i][j] + " ");
    		}
    		System.out.println();
    	}
    	
    }

	public static void main(String[] args) {
		
		//comment out following block when running custom driver:
		int [][] A = new int [][]{{1,3,9}, {2,4,7}, {3,6,8}};
		int [][] B = new int [][]{{4,5,7}, {6,2,7}, {1,6,9}};
		int [][] r = new int [A.length][B[0].length];
		int [][] C = new int [][]{{9,5,8},{7,3,9}, {8, 7, 8}};
		int [][] D = new int [][]{{4,3, 8},{3,9,7}, {9, 4, 8}};
		int [][] s = new int [r.length][C[0].length];
		int [][] t = new int [s.length][D[0].length];
		//comment out preceding block when running custom driver:
		
		
		//uncomment following block to run custom driver
		/*int [][] A,B,C,D;
		int [][] r = new int [A.length][B[0].length];
		int [][] s = new int [r.length][C[0].length];
		int [][] t = new int [s.length][D[0].length]; */
		//uncomment preceding block to run custom driver
		
		ExecutorService pool = Executors.newCachedThreadPool(); //instantiates ExecutorService in Main
		ExecutorService pool2 = Executors.newCachedThreadPool();
		ExecutorService pool3 = Executors.newCachedThreadPool();
		
		MatMathImpl u = new MatMathImpl(pool);
		MatMathImpl u2 = new MatMathImpl(pool2);
		MatMathImpl u3 = new MatMathImpl(pool3);
	   
	//	StreamMatMathImpl stream = new StreamMatMathImpl();
	//	stream.add(A, B, r);  //will only print out 2d array as 1d array
	//	stream.multiply(A, B, r);
	   // code to initialize A,B,C,D
	  // u.add(A,B,r);
	   r = u.add(A,B,r);
	  // u.multiply(A,B,r);
	   s = u2.multiply(r,C,s);
	   u3.multiply(s,D,t);

	}

}
