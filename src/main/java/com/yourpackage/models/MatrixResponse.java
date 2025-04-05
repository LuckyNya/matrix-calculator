package com.yourpackage.models;  // THIS LINE MUST MATCH THE FOLDER STRUCTURE

import com.google.gson.Gson;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unused")
public class MatrixResponse {
    private final double[][] result;
    
    public MatrixResponse(double[][] result) {
        this.result = result;
    }
    
    public void send(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        new Gson().toJson(this, response.getWriter());
    }
}