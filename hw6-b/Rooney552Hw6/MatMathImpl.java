package MatMathParallel;

import java.util.concurrent.*;

/*
 * Not able to get to Java stream solution.
 * Ran out of time to finish.  Busy week with work, moving, and midterms.
 * 
 */

interface MatMath{
    void multiply(int[][] A, int[][]B, int[][]C);  // multiply A and B into C
    void add(int[][]A, int[][]B, int[][]C);        // add A and B into C
    void print(int[][]A);                          // pretty print A
}

class RowColAddExecutable implements Runnable{

	 // code to calculate entry at [row][col] of the resulting matrix
	 // from an addition

	   int[][] first, second, third;  //third is result
	   int row, col, size;
	   

	  RowColAddExecutable(int[][]first, int [][]second, int [][]third, int row, int col, int size){

	     this.first = first;
	     this.second = second;
	     this.third = third;
	     this.row = row;
	     this.col = col;
	     this.size = size;
	   }

	  public void run(){  
		  third[row][col] = first[row][col] + second[row][col];  //runs through Executor Pool
	  }
	 }

class RowColProdExecutable implements Runnable{

	 // code to calculate entry at [row][col] of the resulting matrix
	 // from a multiplication

	   int[][] first, second, third;  //third is result
	   int row, col, size;
	   

	  RowColProdExecutable(int[][]first, int [][]second, int [][]third, int row, int col, int size){

	     this.first = first;
	     this.second = second;
	     this.third = third;
	     this.row = row;
	     this.col = col;
	     this.size = size;
	   }

	  public void run(){ 
	      for (int k=0; k < size; k++){ 
	          third[row][col] += first[row][k]*second[k][col];  //runs through Executor Pool
	      }
	  }
	 }

public class MatMathImpl implements MatMath {
	
	public void multiply(int[][] A, int[][]B, int[][]C) {
		// multiply A and B into C
		//mxn x nxp = mxp
		//to get each squares value, take coordinate (i.e. C[1][2])
		//A[1][0] x B [0][2]  + A[1][1] (y < n) x B [1] [2] (x < n) + ...
		//m[0 +1 <p]
		if (A.length == B[0].length){ //ensures matrices can be multiplied
			int prodPoolSize = A.length * B[0].length; //number of elements, will represent # of threads necessarry
			ExecutorService pool = Executors.newFixedThreadPool(prodPoolSize);//creates a thread to calculate every element position to be placed in C[][]
			for(int i=0; i < A.length; i++){ // iterates rows
				for(int j=0; j < B[0].length; j++){ // iterates columns
					RowColProdExecutable prodThread = new RowColProdExecutable(A, B, C, A.length, B[0].length, prodPoolSize);
					pool.execute(prodThread);  //executes multiplication formula with k depth
					}
				}
			pool.shutdown(); //closes out threads
			}
		else 
			System.out.println("Can't multiply Matrix A and Matrix B: do not have compatible # of Rows and Columns");
			
		}
	
	
    public void add(int[][]A, int[][]B, int[][]C){
    	 if (A.length == B.length && A[0].length == B[0].length) { //ensures they can actually be added together
    		 int addPoolSize = A.length * A[0].length; //number of elements, will represent # of threads necessarry 
    		 ExecutorService pool = Executors.newFixedThreadPool(addPoolSize);//creates a thread to calculate every element position to be placed in C[][]
    		for(int i = 0; i < A.length; i++){  //iterates rows
        		for(int j = 0; j < B[0].length; j++){ //iterates columns
        			//want a seperate thread for every calculation
        			//threads have to terminate and fill up result Matrix
        			//use MxN threads
        			RowColAddExecutable addThread = new RowColAddExecutable(A, B, C, A.length, B[0].length, addPoolSize);//instantiates RowColAdd and creates runnable thread
        			pool.execute(addThread);  //gives runnable command to thread executor        			        				
        			}
        		}
    		pool.shutdown(); //closes out threads
        	}
    	 else 
    		 System.out.println("Can't add Matrix A and Matrix B: do not have same # of Rows and Columns");
    }
    		
    	
    public void print(int[][]A) {  //simple print implementation to print out elements for each position A[i][j]
    	for (int i = 0; i < A.length; i++){
    		for (int j = 0; j < A[0].length; j++){
    			System.out.println(A[i][j]);
    		}
    	}
    }
    


}
