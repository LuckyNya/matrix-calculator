package com.yourpackage.operations;

public class MatrixDeterminant implements MatrixOperator {
    @Override public String getOperationName() { return "determinant"; }
    
    @Override
    public double[][] operate(double[][] a, double[][] b) {
        if (a.length != a[0].length) {
            throw new IllegalArgumentException("Ma trận phải vuông");
        }
        double det = calculateDeterminant(a);
        return new double[][]{{det}}; // Return as 1x1 matrix
    }
    
    private double calculateDeterminant(double[][] matrix) {
        if (matrix.length == 1) return matrix[0][0];
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        
        double determinant = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            double[][] minor = getMinor(matrix, 0, i);
            determinant += matrix[0][i] * Math.pow(-1, i) * calculateDeterminant(minor);
        }
        return determinant;
    }
    
    private double[][] getMinor(double[][] matrix, int row, int col) {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];
        for (int i = 0, m = 0; i < matrix.length; i++) {
            if (i == row) continue;
            for (int j = 0, n = 0; j < matrix.length; j++) {
                if (j == col) continue;
                minor[m][n] = matrix[i][j];
                n++;
            }
            m++;
        }
        return minor;
    }
}