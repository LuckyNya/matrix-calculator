package com.yourpackage.models;

public class MatrixResponse {
    private double[][] result;
    private String error;
    
    public MatrixResponse(double[][] result) {
        this.result = result;
    }
    
    public MatrixResponse(String possibleError) {
        if (possibleError != null && possibleError.startsWith("{")) {
            // This appears to be JSON, let Gson handle it
            throw new IllegalArgumentException("Use Gson for JSON parsing");
        }
        this.error = possibleError;
    }
    
    // Getters
    public double[][] getResult() { return result; }
    public String getError() { return error; }
}