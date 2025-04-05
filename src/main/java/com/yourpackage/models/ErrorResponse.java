package com.yourpackage.models;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;

public class ErrorResponse {
    private final String error;
    
    public ErrorResponse(String error) {
        this.error = error;
    }
    
    public void send(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        new Gson().toJson(this, response.getWriter());
    }
    
    // Getters
    public String getError() {
        return error;
    }
}