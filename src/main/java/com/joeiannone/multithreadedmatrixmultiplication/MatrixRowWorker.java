/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joeiannone.multithreadedmatrixmultiplication;


/**
 *
 * @author josephiannone
 */
public class MatrixRowWorker implements Runnable {
        

    int start, inc, rowsA, colsA, colsB;

    int[][] matrixA, matrixB, matrixC;
    
    
    public MatrixRowWorker(int[][] matrixA, int[][] matrixB, int[][] matrixC, int start, int inc) {
        
        this.start = start;
        this.inc = inc;
        
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;
        
        this.rowsA = this.matrixA.length;
        this.colsA = this.matrixA[0].length;
        this.colsB = this.matrixB[0].length;
        
    }
    
    @Override
    public void run() {
        
        // From starting point to starting point + increment val (each row)
        for(int i = this.start; i < (this.start + this.inc) && (i < this.rowsA); i++) 
            
            // Each col in matrix B
	    for(int j = 0; j < this.colsB; j++) 
                
                // Each col in matrix A / row in matrix B
                for(int k = 0; k < this.colsA; k++) 
                    
                    this.matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
        
    }
    
    
}
