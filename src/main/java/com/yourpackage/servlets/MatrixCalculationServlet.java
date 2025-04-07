package com.yourpackage.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yourpackage.client.MatrixClient;
import com.yourpackage.models.MatrixRequest;
import com.yourpackage.models.MatrixResponse;

@WebServlet("/calculate")
public class MatrixCalculationServlet extends HttpServlet {
    private static final Gson gson = new Gson();
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Parse the JSON request
            JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);
            String protocol = json.get("protocol").getAsString();
            MatrixRequest matrixRequest = gson.fromJson(json, MatrixRequest.class);
            
            // Initialize client
            MatrixClient client = new MatrixClient("localhost", 1234, 1235);
            MatrixResponse matrixResponse;
            
            // Call appropriate protocol
            if ("tcp".equalsIgnoreCase(protocol)) {
                matrixResponse = client.sendViaTCP(matrixRequest);
            } else {
                matrixResponse = client.sendViaUDP(matrixRequest);
            }
            
            // Send response back
            response.setContentType("application/json");
            gson.toJson(matrixResponse, response.getWriter());
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }
}