package com.yourpackage.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yourpackage.models.MatrixRequest;
import com.yourpackage.models.MatrixResponse;
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class MatrixClient {
    private static final Gson gson = new Gson();
    private final String serverHost;
    private final int tcpPort;
    private final int udpPort;

    public MatrixClient(String serverHost, int tcpPort, int udpPort) {
        this.serverHost = serverHost;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    // TCP Implementation
    public MatrixResponse sendViaTCP(MatrixRequest request) throws IOException {
        System.out.println("CLIENT: Connecting to " + serverHost + ":" + tcpPort);
        
        try (Socket socket = new Socket(serverHost, tcpPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                 new InputStreamReader(socket.getInputStream()))) {
            
            String requestJson = gson.toJson(request);
            System.out.println("CLIENT: Sending request: " + requestJson);
            out.println(requestJson);
            
            String responseJson = in.readLine();
            System.out.println("CLIENT: Raw response: " + responseJson);
            
            if (responseJson == null) {
                throw new IOException("Empty response from server");
            }
            
            // Check if response is JSON
            if (!responseJson.trim().startsWith("{")) {
                throw new IOException("Server returned non-JSON response: " + responseJson);
            }
            
            return gson.fromJson(responseJson, MatrixResponse.class);
        }
    }

    // UDP Implementation
    public MatrixResponse sendViaUDP(MatrixRequest request) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(serverHost);
            byte[] requestData = gson.toJson(request).getBytes();
            
            socket.send(new DatagramPacket(
                requestData, requestData.length, address, udpPort
            ));
            
            byte[] buffer = new byte[65507];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);
            
            String responseJson = new String(
                responsePacket.getData(), 0, responsePacket.getLength()
            );
            
            // DEBUG: Print raw UDP response
            System.out.println("DEBUG - Raw UDP JSON: " + responseJson);  // <-- ADD THIS
            
            try {
                MatrixResponse response = gson.fromJson(responseJson, MatrixResponse.class);
                
                // DEBUG: Print parsed UDP response
                System.out.println("DEBUG - Parsed UDP result: " + Arrays.deepToString(response.getResult())); // <-- ADD THIS
                
                return response;
            } catch (JsonSyntaxException e) {
                System.err.println("DEBUG - Failed to parse UDP JSON: " + responseJson);
                throw new IOException("Invalid UDP JSON response", e);
            }
        }
    }
}