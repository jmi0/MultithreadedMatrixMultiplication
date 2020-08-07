package com.joeiannone.multithreadedmatrixmultiplication;


/**
 *
 * @author josephiannone
 */
public class MatrixMultiplier {
    
    public int M, K, N;
    
    public int[][] matrixA, matrixB, matrixC, matrixC2;
    
    private Thread[] threads;
    
    long start_ts, end_ts;
    
    /**
     * Get count of available cores
     */
    public int CORES  = Runtime.getRuntime().availableProcessors();
    
    
    public MatrixMultiplier(int[][] matrixA, int[][] matrixB) {
        
        /**
         * Make sure cols in matrixA equals rows in matrixB
         */
        if (matrixA[0].length != matrixB.length) {
            System.out.println("\nInvalid matrices. Columns of matrix A must equal rows in matrix B.\n");
            System.exit(0);
        }
        
        /**
         * Set M, K, N values
         */
        this.M = matrixA.length; // matrix A rows
        this.K = matrixB.length; // matrix A cols, matrix B rows
        this.N = matrixB[0].length; // matrix B cols
        
        
        /**
         * Set/initialize matrices
         */
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        
        // for mutlithreaded matrix C
        this.matrixC = new int[this.M][this.N];
        
        // for traditional matrix C
        this.matrixC2 = new int[this.M][this.N];
	
        
        /**
         * Lets execute and time single threaded solution
         */
        // start timesstamp
	start_ts = System.currentTimeMillis();	
        
        // single threaded multiply
	this.matrixC2 = this.multiplyMatrices(this.matrixA, this.matrixB);
        
        // end timestamp
	end_ts = System.currentTimeMillis();
        System.out.println("Single threaded solution MS: " + (end_ts - start_ts) + "ms");
        
        
        
        /**
         * Now multi threaded multiply process
         */
        // Reset timestamp for multi threaded solution
	start_ts = System.currentTimeMillis(); // record time for threaded solution
        
        try {
            
            /**
             * if only 1 core, lets pretend there is 4 for POC
             */
            if (CORES == 1) CORES = 4;
            
            /**
             * Redefine CORES if more cores than resultant rows
             */ 
            if (CORES > this.M) CORES = this.M;
            
            
            /**
             * Define rows per thread
             */
            int ROWS_PER_THREAD = this.M/CORES;
            
            /**
             * Let us know how many cores we have, 
             * and how many rows each thread will handle
             */
            System.out.println("\nTotal CPU cores: " + CORES);
            System.out.println("Total rows (for resultant matrix): " + this.M);
            System.out.println("Rows per thread: " + ROWS_PER_THREAD);
            System.out.println();
            
            /**
             * Initialize threads
             */
            this.threads = new Thread[CORES];
            
            /**
             *  create each thread and worker
             */
            for (int i=0; i < this.threads.length; i++) {
                
                /**
                 * Define this specific threads row range
                 */
                int range = ROWS_PER_THREAD;
                
                // Last thread will take the excess
		if (i == this.threads.length - 1) range += this.M % CORES;
		
                /**
                 * Create thread and worker
                 */
                this.threads[i] = new Thread(new MatrixRowWorker(this.matrixA, this.matrixB, this.matrixC, i*ROWS_PER_THREAD, range));
                this.threads[i].start();
                
            }
            
            /**
             * Wait for threads to die
             */
            for (Thread thread : this.threads) thread.join(); 
    
            
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        
        end_ts = System.currentTimeMillis();
        System.out.println("Multi threaded solution MS: " + (end_ts - start_ts) + "ms");
        
        
        /**
         * To print matrices and result
         */
        //MatrixMultiplication.printMatrix(this.matrixA);
        //MatrixMultiplication.printMatrix(this.matrixB);
        //MatrixMultiplication.printMatrix(this.matrixC);
        
       
    }
    
    
    
    private int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        
        int[][] result = new int[this.M][this.N];
        
        for (int i = 0; i < this.M; i++)
            
            for (int j = 0; j < this.N; j++)
                
                for (int k = 0; k < this.K; k++) {
                    
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                    
                }

        return result;
    }
    
    
    
}
