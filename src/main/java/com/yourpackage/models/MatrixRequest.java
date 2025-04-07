package com.yourpackage.models;

public class MatrixRequest {
    public double[][] matrixA;
    public double[][] matrixB;
    public String operation;
    public String protocol;
    
    // For client-server communication
    public MatrixRequest() {}  // Add default constructor for JSON deserialization
    
    public MatrixRequest(double[][] matrixA, double[][] matrixB, String operation) {
        if (operation == null) throw new IllegalArgumentException("Operation cannot be null");
        if (matrixA == null) throw new IllegalArgumentException("MatrixA cannot be null");
        
        // MatrixB can be null for unary operations
        if (!isUnaryOperation(operation) && matrixB == null) {
            throw new IllegalArgumentException("MatrixB required for this operation");
        }
        
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.operation = operation;
    }
    
    private boolean isUnaryOperation(String op) {
        return op.equalsIgnoreCase("transpose") || 
               op.equalsIgnoreCase("determinant") || 
               op.equalsIgnoreCase("inverse") || 
               op.equalsIgnoreCase("trace");
    }
    
    // Getters for JSON serialization
    public double[][] getMatrixA() { return matrixA; }
    public double[][] getMatrixB() { return matrixB; }
    public String getOperation() { return operation; }
}