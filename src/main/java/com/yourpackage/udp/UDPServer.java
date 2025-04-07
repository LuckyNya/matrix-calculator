package com.yourpackage.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import com.google.gson.Gson;
import com.yourpackage.models.MatrixRequest;
import com.yourpackage.models.MatrixResponse;
import com.yourpackage.operations.MatrixOperator;
import com.yourpackage.operations.MatrixOperatorFactory;

public class UDPServer {
    private static final Gson gson = new Gson();
    private static final MatrixOperatorFactory operatorFactory = new MatrixOperatorFactory();

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(1235)) {  // Using port 1235 for UDP
            System.out.println("UDP Server running on port 1235...");
            byte[] buffer = new byte[65507];  // Max UDP packet size

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                String requestJson = new String(packet.getData(), 0, packet.getLength());
                MatrixRequest request = gson.fromJson(requestJson, MatrixRequest.class);

                try {
                    MatrixOperator operator = operatorFactory.getOperator(request.operation);
                    double[][] result = operator.operate(request.matrixA, request.matrixB);
                    
                    // Send as PROPER JSON
                    MatrixResponse response = new MatrixResponse(result);
                    System.out.println("SERVER DEBUG - Sending: " + gson.toJson(response));
                    byte[] responseData = gson.toJson(response).getBytes();
                    socket.send(new DatagramPacket(
                        responseData, responseData.length,
                        packet.getAddress(), packet.getPort()
                    ));
                    
                } catch (IllegalArgumentException e) {
                    // Send error as PROPER JSON
                    MatrixResponse errorResponse = new MatrixResponse(e.getMessage());
                    byte[] errorData = gson.toJson(errorResponse).getBytes();
                    socket.send(new DatagramPacket(
                        errorData, errorData.length,
                        packet.getAddress(), packet.getPort()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("UDP Server error: " + e.getMessage());
        }
    }
}