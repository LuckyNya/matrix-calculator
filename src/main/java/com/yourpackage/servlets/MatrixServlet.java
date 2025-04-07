package com.yourpackage.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yourpackage.models.ErrorResponse;
import com.yourpackage.models.MatrixRequest;
import com.yourpackage.models.MatrixResponse;
import com.yourpackage.operations.MatrixOperator;
import com.yourpackage.operations.MatrixOperatorFactory;

@WebServlet("/matrix")
public class MatrixServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MatrixOperatorFactory operatorFactory = new MatrixOperatorFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Read and parse JSON request
            Gson gson = new Gson();
            MatrixRequest req = gson.fromJson(request.getReader(), MatrixRequest.class);
            
            // Get appropriate operator
            MatrixOperator operator = operatorFactory.getOperator(req.operation);
            
            // Perform calculation
            double[][] result = operator.operate(req.matrixA, req.matrixB);
            
            // Send success response
            response.setContentType("application/json");
            gson.toJson(new MatrixResponse(result), response.getWriter());
            
        } catch (IllegalArgumentException e) {
            // Handle matrix operation errors (invalid dimensions, etc.)
            new ErrorResponse("Calculation error: " + e.getMessage()).send(response);
        } catch (Exception e) {
            // Handle system errors
            new ErrorResponse("System error: " + e.getMessage()).send(response);
        }
    }
}