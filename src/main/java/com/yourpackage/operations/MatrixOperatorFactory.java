package com.yourpackage.operations;

public class MatrixOperatorFactory {
    public MatrixOperator getOperator(String operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        
        switch (operation.toLowerCase()) {
            case "add":         return new MatrixAdder();
            case "subtract":    return new MatrixSubtractor();
            case "multiply":   return new MatrixMultiplier();
            case "hadamard":   return new HadamardProduct();
            case "kronecker":  return new KroneckerProduct();
            case "divide":     return new MatrixDivider();
            case "transpose":  return new MatrixTransposer();
            case "determinant":return new MatrixDeterminant();
            case "inverse":   return new MatrixInverter();
            case "trace":      return new MatrixTracer();
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}