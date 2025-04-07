package com.yourpackage.operations;

public interface MatrixOperator {
    /**
     * Performs matrix operation
     * @param a First matrix (required)
     * @param b Second matrix (may be null for unary operations)
     * @return Operation result
     * @throws IllegalArgumentException for invalid inputs or operations
     */
    double[][] operate(double[][] a, double[][] b) throws IllegalArgumentException;
    
    String getOperationName();
}