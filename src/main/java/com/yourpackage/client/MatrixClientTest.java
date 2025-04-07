package com.yourpackage.client;

import com.yourpackage.models.MatrixRequest;
import com.yourpackage.models.MatrixResponse;

public class MatrixClientTest {
    public static void main(String[] args) {
        // Initialize client (TCP port 1234, UDP port 1235)
        MatrixClient client = new MatrixClient("localhost", 1234, 1235);
        
        // Create a sample matrix request
        MatrixRequest request = new MatrixRequest(
            new double[][]{{1, 2}, {3, 4}},  // matrixA
            new double[][]{{5, 6}, {7, 8}},  // matrixB
            "multiply"                       // operation
        );

        try {
            // Choose protocol (uncomment one)
            MatrixResponse response = client.sendViaTCP(request);
            // MatrixResponse response = client.sendViaUDP(request);
            
            // Process response
            if (response.getError() != null) {
                System.err.println("Error: " + response.getError());
            } else {
                System.out.println("Result:");
                for (double[] row : response.getResult()) {
                    for (double val : row) {
                        System.out.print(val + " ");
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}