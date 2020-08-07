package com.joeiannone.multithreadedmatrixmultiplication;


/**
 *
 * @author josephiannone
 */

public class MatrixMultiplication {
    
    /**
     * Min and max values fro getting random integers to fill matrices
     */
    static int MIN = -100;
    static int MAX = 100;
    
    public static void main(String[] args) {
        
        /**
         * Default values for rows and columns
         */
        int m = 2000; // matrix A rows
        int k = 1000; // matrix A cols, matrix B rows
        int n = 500;  // matrix B cols
        
        int[][] matrixA = getRandomMatrix(m, k);
        int[][] matrixB = getRandomMatrix(k, n);
        
        
        MatrixMultiplier multiplier = new MatrixMultiplier(matrixA, matrixB);

       
    }
    
    /**
     * Prints a given matrix
     * 
     * @param matrix 
     */
    public static void printMatrix(int[][] matrix) {
        
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            // new row
            System.out.println("");
        }
        
    }
    
    /**
     * Builds a random matrix based on params and MIN/MAX
     * 
     * @param rows
     * @param cols
     * @return 
     */
    public static int[][] getRandomMatrix(int rows, int cols) {
        
        int[][] matrix = new int[rows][cols];
        
        // build matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (int) Math.floor(Math.random() * (MatrixMultiplication.MAX - MatrixMultiplication.MIN + 1) + MatrixMultiplication.MIN);
            }
        }
        
        return matrix;
    }
    
    /**
     * This is just a function I built to help me validate results from both
     * multiplication methods to ensure results were accurate.
     * 
     * @param matrix1
     * @param matrix2
     * @return 
     */
    public static boolean equals(int[][] matrix1, int[][] matrix2) {
        boolean yes = true;
        for (int i = 0; i < matrix1.length; i++) 
            for (int j = 0; j < matrix1[0].length; j++) 
                if (matrix1[i][j] != matrix2[i][j]) return false;
        
        return yes;
    }
    
    
}