package com.yourpackage.operations;

public class MatrixAdder implements MatrixOperator {
    @Override
    public String getOperationName() {
        return "addition";
    }

    @Override
    public double[][] operate(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException(
                "For " + getOperationName() + ", matrices must have identical dimensions. " +
                "Received: " + a.length + "x" + a[0].length + " and " + 
                b.length + "x" + b[0].length
            );
        }
        
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }
}