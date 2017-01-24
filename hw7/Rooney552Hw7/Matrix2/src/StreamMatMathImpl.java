import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamMatMathImpl {

	/*
	 * Not able to get to Java stream solution.
	 * Ran out of time to finish.  Busy week with work, moving, and midterms.
	 * 
	 */
		
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
						for(int k= 0; k <A[0].length; k++){
					/*		IntStream.range(0,  A.length).parallel()
								.map(i -> IntStream.range(0,  B[0].length))
								.map(j -> IntStream.range(0,  A[0].length))
								.map(k -> result[i][j] += A[i][k]*B[k]))
										); */
							result[i][j] += A[i][k]*B[k][j];
						}
						
						}
					}
				
				
				}
			else 
				System.out.println("Can't multiply Matrix A and Matrix B: do not have compatible # of Rows and Columns");
			C = result;  // assigns result to C
			print(C);  //prints out final result
			System.out.println("StreamResult");
			return C;  //returns result int[][]
				
			}
		
		
	    public int[][] add(int[][]A, int[][]B, int[][]C){
	    	//Stream1[0-m-1]
	    	//Stream2[0-n-1]
	    	//stream1.parallel(i).forEach() ->
	    	//stream2.parallel(j).forEach() -> [i][j]
	    	//
	    	int result [][] = new int[A.length][B[0].length];
	    	Stream.of(A).parallel().flatMapToInt(IntStream::of).forEach(System.out::println);
	    	Stream.of(B).parallel().flatMapToInt(IntStream::of).forEach(System.out::println);
	 /*   	IntStream.range(0, A.length)
	    		.forEach(x -> IntStream.range(0,  B[0].length)
	    				.forEach(int y -> result[x][y])); */
	    	
	    	 if (A.length == B.length && A[0].length == B[0].length) { //ensures they can actually be added together    		 
	    		 for(int i = 0; i < A.length; i++){  //iterates rows
	        		for(int j = 0; j < B[0].length; j++){ //iterates columns
	        			//want a seperate thread for every calculation
	        			//threads have to terminate and fill up result Matrix
	        			//use MxN threads
	        			result[i][j] = A[i][j] + B[i][j];
	        			/*System.out.println("A new RowColAdd has been created for position " + i + " : " +j);
	        			System.out.println("A[][] = " +  A[i][j]);
	        			System.out.println("B[][] = " +  B[i][j]);     */

	        			}
	        		}
	    		
	    		
	        	}
	    	 else 
	    		 System.out.println("Can't add Matrix A and Matrix B: do not have same # of Rows and Columns");
	 		C = result; //assigns result to C
	 		print(C); //prints out final result
	 		System.out.println("StreamResult");
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
	
}
