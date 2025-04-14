package com.yourpackage.operations;

public class MatrixInverter implements MatrixOperator {
    @Override public String getOperationName() { return "inversion"; }
    
    @Override
    public double[][] operate(double[][] a, double[][] b) {
        MatrixDeterminant detCalculator = new MatrixDeterminant();
        double det = detCalculator.operate(a, null)[0][0];
        if (det == 0) throw new IllegalArgumentException("Ma trận được nhập suy biến( Định thức = 0)");
        
        double[][] inverse = new double[a.length][a.length];
        invertRecursive(a, inverse, det, 0, 0);
        return inverse;
    }
    
    private void invertRecursive(double[][] a, double[][] inverse, double det, int i, int j) {
        if (i >= a.length) return;
        if (j >= a.length) {
            invertRecursive(a, inverse, det, i + 1, 0);
            return;
        }
        
        MatrixDeterminant detCalculator = new MatrixDeterminant();
        double[][] minor = getMinor(a, j, i); // Note: i,j swapped for adjugate
        double minorDet = detCalculator.operate(minor, null)[0][0];
        inverse[i][j] = Math.pow(-1, i + j) * minorDet / det;
        
        invertRecursive(a, inverse, det, i, j + 1);
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