package com.yourpackage.operations;

public class MatrixOperatorFactory {
    public MatrixOperator getOperator(String operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        
        operation = operation.toLowerCase();
        
        switch (operation) {
            case "add": case "subtract": case "multiply": 
            case "hadamard": case "kronecker": case "divide":
                return createBinaryOperator(operation);
                
            case "transpose": case "determinant": 
            case "inverse": case "trace":
                return createUnaryOperator(operation);
                
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
    
    private MatrixOperator createBinaryOperator(String op) {
        switch (op) {
            case "add": return new MatrixAdder();
            case "subtract": return new MatrixSubtractor();
            case "multiply": return new MatrixMultiplier();
            case "hadamard": return new HadamardProduct();
            case "kronecker": return new KroneckerProduct();
            case "divide": return new MatrixDivider();
            default: throw new AssertionError();
        }
    }
    
    private MatrixOperator createUnaryOperator(String op) {
        switch (op) {
            case "transpose": return new MatrixTransposer();
            case "determinant": return new MatrixDeterminant();
            case "inverse": return new MatrixInverter();
            case "trace": return new MatrixTracer();
            default: throw new AssertionError();
        }
    }
}