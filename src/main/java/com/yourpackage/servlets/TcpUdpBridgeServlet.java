package com.yourpackage.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;
import com.yourpackage.client.MatrixClient;
import com.yourpackage.models.MatrixRequest;
import com.yourpackage.models.MatrixResponse;

@WebServlet("/tcp-udp-bridge")
public class TcpUdpBridgeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Gson gson = new Gson();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Parse request from web frontend
            JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);
            String protocol = json.get("protocol").getAsString();
            MatrixRequest matrixRequest = gson.fromJson(json.get("request"), MatrixRequest.class);
            
            // Initialize client and send request
            MatrixClient client = new MatrixClient("localhost", 1234, 1235);
            MatrixResponse matrixResponse;
            
            if ("tcp".equalsIgnoreCase(protocol)) {
                matrixResponse = client.sendViaTCP(matrixRequest);
            } else {
                matrixResponse = client.sendViaUDP(matrixRequest);
            }
            
            // Send response back to web frontend
            response.setContentType("application/json");
            gson.toJson(matrixResponse, response.getWriter());
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }
}