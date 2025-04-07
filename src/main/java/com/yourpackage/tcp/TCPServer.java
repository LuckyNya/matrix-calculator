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
            System.out.println("TCP Server (NEW VERSION) running on port 1234...");
            
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
            
            System.out.println("NEW SERVER: Connection received");
            String requestJson = in.readLine();
            System.out.println("NEW SERVER: Received: " + requestJson);
            
            MatrixRequest request = gson.fromJson(requestJson, MatrixRequest.class);
            System.out.println("NEW SERVER: Parsed request for operation: " + request.operation);
            
            MatrixOperator operator = operatorFactory.getOperator(request.operation);
            double[][] result = operator.operate(request.matrixA, request.matrixB);
            
            MatrixResponse response = new MatrixResponse(result);
            String responseJson = gson.toJson(response);
            System.out.println("NEW SERVER: Sending JSON: " + responseJson);
            
            out.println(responseJson);
            System.out.println("NEW SERVER: Response sent");
            
        } catch (Exception e) {
            System.err.println("NEW SERVER: Error handling client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}