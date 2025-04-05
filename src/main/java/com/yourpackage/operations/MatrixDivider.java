package com.yourpackage.operations;

public class MatrixDivider implements MatrixOperator {
    @Override public String getOperationName() { return "division"; }
    
    @Override
    public double[][] operate(double[][] a, double[][] b) {
        // Matrix division is equivalent to A * inv(B)
        MatrixInverter inverter = new MatrixInverter();
        double[][] invB = inverter.operate(b, null); // Second argument unused
        MatrixMultiplier multiplier = new MatrixMultiplier();
        return multiplier.operate(a, invB);
    }
}