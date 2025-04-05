package com.yourpackage.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(1234)) {
            System.out.println("UDP Server running on port 1234...");
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Now properly handles IOException
                
                String request = new String(packet.getData(), 0, packet.getLength());
                System.out.println("UDP Received: " + request);
                
                // Simulate matrix computation
                String response = "UDP Result: [[6,8],[10,12]]";
                byte[] responseData = response.getBytes();
                socket.send(new DatagramPacket(
                    responseData, responseData.length,
                    packet.getAddress(), packet.getPort()
                ));
            }
        } catch (SocketException e) {
            System.err.println("UDP Socket Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("UDP I/O Error: " + e.getMessage());
        }
    }
}