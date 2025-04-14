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
        try (Socket socket = new Socket(serverHost, tcpPort);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
            
            out.println(gson.toJson(request));
            String response = in.readLine();
            
            if (response == null) {
                throw new IOException("Không nhận được phản hồi");
            }
            
            try {
                return gson.fromJson(response, MatrixResponse.class);
            } catch (JsonSyntaxException e) {
                return new MatrixResponse(response);
            }
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