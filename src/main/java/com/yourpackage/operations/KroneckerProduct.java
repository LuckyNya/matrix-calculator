package com.yourpackage.operations;

public class KroneckerProduct implements MatrixOperator {
    @Override 
    public String getOperationName() { 
        return "Kronecker product"; 
    }
    
    @Override
    public double[][] operate(double[][] a, double[][] b) {
        double[][] result = new double[a.length * b.length][a[0].length * b[0].length];
        kroneckerRecursive(a, b, result, 0, 0, 0, 0);
        return result;
    }
    
    private void kroneckerRecursive(double[][] a, double[][] b, double[][] result, 
                                  int aRow, int aCol, int resRow, int resCol) {
        // Base case: we've processed all elements of matrix A
        if (aRow >= a.length) {
            return;
        }
        
        // Calculate the Kronecker product block for current A element
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                result[resRow + i][resCol + j] = a[aRow][aCol] * b[i][j];
            }
        }
        
        // Move to next column in A (and corresponding position in result)
        if (aCol + 1 < a[0].length) {
            kroneckerRecursive(a, b, result, aRow, aCol + 1, 
                              resRow, resCol + b[0].length);
        } 
        // Move to next row in A (and corresponding position in result)
        else {
            kroneckerRecursive(a, b, result, aRow + 1, 0, 
                              resRow + b.length, 0);
        }
    }
}