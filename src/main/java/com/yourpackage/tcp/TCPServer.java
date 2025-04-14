package com.yourpackage.tcp;

import java.io.*;
import java.net.*;
import com.google.gson.*;
import com.yourpackage.models.*;
import com.yourpackage.operations.*;

public class TCPServer {
    private static final Gson gson = new Gson();
    private static final MatrixOperatorFactory operatorFactory = new MatrixOperatorFactory();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("TCP Server running on port 1234...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("TCP Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            // Set timeout to prevent hanging
            socket.setSoTimeout(10000); // 10 seconds
            
            System.out.println("NEW SERVER: Connection received");
            String requestJson = in.readLine();
            
            if (requestJson == null || requestJson.trim().isEmpty()) {
                sendError(out, "Empty request received");
                return;
            }
            
            System.out.println("NEW SERVER: Received: " + requestJson);
            
            try {
                MatrixRequest request = gson.fromJson(requestJson, MatrixRequest.class);
                System.out.println("NEW SERVER: Parsed request for operation: " + request.operation);
                
                try {
                    MatrixOperator operator = operatorFactory.getOperator(request.operation);
                    double[][] result = operator.operate(request.matrixA, request.matrixB);
                    
                    MatrixResponse response = new MatrixResponse(result);
                    sendResponse(out, response);
                    
                } catch (IllegalArgumentException e) {
                    // Operation-specific errors (like non-square matrix)
                    sendError(out, e.getMessage());
                }
                
            } catch (JsonSyntaxException e) {
                sendError(out, "Invalid JSON format");
            }
            
        } catch (SocketTimeoutException e) {
            System.err.println("Client timeout");
        } catch (Exception e) {
            System.err.println("NEW SERVER: Error handling client: " + e.getMessage());
        }
    }
    
    private static void sendResponse(PrintWriter out, MatrixResponse response) {
        String responseJson = gson.toJson(response);
        System.out.println("NEW SERVER: Sending JSON: " + responseJson);
        out.println(responseJson);
    }
    
    private static void sendError(PrintWriter out, String errorMessage) {
        System.err.println("NEW SERVER: Error: " + errorMessage);
        MatrixResponse errorResponse = new MatrixResponse(errorMessage);
        sendResponse(out, errorResponse);
    }
}