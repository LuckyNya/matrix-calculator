package com.yourpackage.models;  // THIS LINE MUST MATCH THE FOLDER STRUCTURE

public class MatrixRequest {
    public double[][] matrixA;
    public double[][] matrixB;
    public String operation;
    
    // Optional: Add constructor for better initialization
    public MatrixRequest(double[][] matrixA, double[][] matrixB, String operation) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.operation = operation;
    }
}