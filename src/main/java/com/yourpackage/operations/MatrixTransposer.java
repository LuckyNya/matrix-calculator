package com.yourpackage.operations;

public class MatrixTransposer implements MatrixOperator {
    @Override public String getOperationName() { return "transpose"; }
    
    @Override
    public double[][] operate(double[][] a, double[][] b) {
        double[][] result = new double[a[0].length][a.length];
        transposeRecursive(a, result, 0, 0);
        return result;
    }
    
    private void transposeRecursive(double[][] a, double[][] result, int i, int j) {
        if (i >= a.length) return;
        if (j >= a[0].length) {
            transposeRecursive(a, result, i + 1, 0);
            return;
        }
        result[j][i] = a[i][j];
        transposeRecursive(a, result, i, j + 1);
    }
}