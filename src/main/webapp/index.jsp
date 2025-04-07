<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Matrix Calculator</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            margin: 0;
            background-color: #f5f5f5;
        }
        .calculator-container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            width: 80%;
            max-width: 900px;
        }
        h1 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }
        .control-panel {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            justify-content: center;
            margin-bottom: 20px;
        }
        .control-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        label {
            font-weight: bold;
            color: #2c3e50;
        }
        input[type="number"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 60px;
        }
        select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            padding: 10px 15px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #2980b9;
        }
        .matrix-container {
            display: flex;
            gap: 30px;
            margin-bottom: 20px;
            justify-content: center;
        }
        .matrix {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .matrix h3 {
            margin-top: 0;
            color: #2c3e50;
            text-align: center;
        }
        .matrix-input {
            width: 60px;
            margin: 3px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
        #result {
            margin-top: 30px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 5px;
            border: 1px solid #ddd;
        }
        .error {
            color: #e74c3c;
            margin: 10px 0;
            padding: 10px;
            border: 1px solid #f5b7b1;
            background-color: #fdedec;
            border-radius: 4px;
        }
        #result table {
            border-collapse: collapse;
            margin: 0 auto;
        }
        #result td {
            padding: 8px 12px;
            text-align: center;
            border: 1px solid #ddd;
            background-color: white;
        }
        .button-group {
            display: flex;
            gap: 10px;
            justify-content: center;
            margin: 20px 0;
        }
		.hidden {
		    display: none !important;
		}
		.loading {
    		color: #3498db;
    		text-align: center;
    		padding: 20px;
    		font-weight: bold;
}
    </style>
</head>
<body>
    <div class="calculator-container">
        <h1>Matrix Calculator</h1>
        
        <div class="control-panel">
            <div class="control-group">
                <label>Operation:</label>
				<select id="operation" onchange="updateMatrixInputs()">
				    <option value="add">Addition (A+B)</option>
				    <option value="subtract">Subtraction (A-B)</option>
				    <option value="multiply">Multiplication (A&times;B)</option>
				    <option value="hadamard">Hadamard Product (A&#8728;B)</option>
				    <option value="kronecker">Kronecker Product (A&#8855;B)</option>
				    <option value="divide">Division (A/B)</option>
				    <option value="transpose">Transpose (A&#x1D40;)</option>
				    <option value="determinant">Determinant (|A|)</option>
				    <option value="inverse">Inverse (A&#8315;&#185;)</option>
				    <option value="trace">Trace (tr(A))</option>
				</select>
            </div>
            
            <div class="control-group" id="matrixAControls">
                <label>Matrix A Rows:</label>
                <input type="number" id="rowsA" min="1" max="10" value="2">
            </div>
            
            <div class="control-group" id="matrixAColsControls">
                <label>Matrix A Columns:</label>
                <input type="number" id="colsA" min="1" max="10" value="2">
            </div>
            
            <div class="control-group" id="matrixBControls">
                <label>Matrix B Rows:</label>
                <input type="number" id="rowsB" min="1" max="10" value="2">
            </div>
            
            <div class="control-group" id="matrixBColsControls">
                <label>Matrix B Columns:</label>
                <input type="number" id="colsB" min="1" max="10" value="2">
            </div>
            
            <div class="control-group">
    			<label>Protocol:</label>
    			<div>
        			<input type="radio" id="tcp" name="protocol" value="tcp" checked>
        			<label for="tcp">TCP</label>
        			<input type="radio" id="udp" name="protocol" value="udp">
        			<label for="udp">UDP</label>
    			</div>
			</div>
            
            <div class="control-group">
                <label>&nbsp;</label>
                <button onclick="createMatrixInputs()">Create Matrices</button>
            </div>
        </div>
        
        <div class="matrix-container">
            <div class="matrix" id="matrixA">
                <h3>Matrix A</h3>
            </div>
            <div class="matrix" id="matrixB">
                <h3>Matrix B</h3>
            </div>
        </div>
        
        <div class="button-group">
            <button type="button" onclick="fillRandom()">Fill Random Numbers</button>
            <button onclick="loadSampleMatrix()">Load Sample Matrix</button>
            <button onclick="calculate()">Calculate</button>
        </div>
        
        <div id="result"></div>
    </div>

    <script>
        // Track operations that need only one matrix
        const singleMatrixOperations = [
            'transpose', 'determinant', 'inverse', 'trace'
        ];
        
        // Update matrix inputs based on operation selection
		function updateMatrixInputs() {
		    const operation = document.getElementById('operation').value;
		    const needsSingleMatrix = singleMatrixOperations.includes(operation);
		    
		    // Toggle visibility of Matrix B controls
		    document.getElementById('matrixBControls').classList.toggle('hidden', needsSingleMatrix);
		    document.getElementById('matrixBColsControls').classList.toggle('hidden', needsSingleMatrix);
		    
		    // Toggle visibility of the entire Matrix B container
		    document.getElementById('matrixB').classList.toggle('hidden', needsSingleMatrix);
		    
		    // Adjust Matrix B title for unary operations
		    const matrixBTitle = document.querySelector('#matrixB h3');
		    if (needsSingleMatrix) {
		        matrixBTitle.textContent = 'Result Preview';
		    } else {
		        matrixBTitle.textContent = 'Matrix B';
		    }
		    
		    // Special cases for square matrices
		    if (operation === 'determinant' || operation === 'inverse' || operation === 'trace') {
		        document.getElementById('colsA').value = document.getElementById('rowsA').value;
		    }
		}
        
        // Initialize with correct visibility
        window.onload = function() {
            updateMatrixInputs();
            createMatrixInputs();
        };
        
		function createMatrixInputs() {
		    const operation = document.getElementById('operation').value;
		    const needsSingleMatrix = singleMatrixOperations.includes(operation);
		    
		    const rowsA = parseInt(document.getElementById('rowsA').value);
		    const colsA = parseInt(document.getElementById('colsA').value);
		    
		    createMatrix('matrixA', rowsA, colsA);
		    
		    if (!needsSingleMatrix) {
		        const rowsB = parseInt(document.getElementById('rowsB').value);
		        const colsB = parseInt(document.getElementById('colsB').value);
		        createMatrix('matrixB', rowsB, colsB);
		        document.getElementById('matrixB').classList.remove('hidden');
		    } else {
		        document.getElementById('matrixB').innerHTML = '<h3>Result Preview</h3>';
		        document.getElementById('matrixB').classList.add('hidden');
		    }
		}
        
        function createMatrix(containerId, rows, cols) {
            const container = document.getElementById(containerId);
            container.innerHTML = `<h3>${containerId === 'matrixA' ? 'Matrix A' : 'Matrix B'}</h3>`;
            
            for (let i = 0; i < rows; i++) {
                for (let j = 0; j < cols; j++) {
                    const input = document.createElement('input');
                    input.type = 'number';
                    input.className = 'matrix-input';
                    input.id = `${containerId}_${i}_${j}`;
                    input.value = (i === j) ? '1' : '0';
                    container.appendChild(input);
                    
                    if (j === cols - 1) {
                        container.appendChild(document.createElement('br'));
                    }
                }
            }
        }
        
		function fillRandom() {
		    const operation = document.getElementById('operation').value;
		    const needsSingleMatrix = singleMatrixOperations.includes(operation);
		    
		    const rowsA = parseInt(document.getElementById('rowsA').value);
		    const colsA = parseInt(document.getElementById('colsA').value);
		    fillRandomMatrix('matrixA', rowsA, colsA);
		    
		    if (!needsSingleMatrix) {
		        const rowsB = parseInt(document.getElementById('rowsB').value);
		        const colsB = parseInt(document.getElementById('colsB').value);
		        fillRandomMatrix('matrixB', rowsB, colsB);
		    }
		}
        
        function fillRandomMatrix(prefix, rows, cols) {
            for (let i = 0; i < rows; i++) {
                for (let j = 0; j < cols; j++) {
                    const randomValue = (Math.random() * 20 - 10).toFixed(1);
                    document.getElementById(`${prefix}_${i}_${j}`).value = randomValue;
                }
            }
        }
        
		function loadSampleMatrix() {
		    const operation = document.getElementById('operation').value;
		    
		    if (singleMatrixOperations.includes(operation)) {
		        // Load sample square matrix for unary operations
		        document.getElementById('rowsA').value = 3;
		        document.getElementById('colsA').value = 3;
		        createMatrixInputs();
		        
		        const presetA = [
		            [2, -1, 0],
		            [-1, 2, -1],
		            [0, -1, 2]
		        ];
		        fillMatrix('matrixA', presetA);
		        document.getElementById('matrixB').classList.add('hidden');
		    } else {
		        // Load sample matrices for binary operations
		        document.getElementById('rowsA').value = 2;
		        document.getElementById('colsA').value = 3;
		        document.getElementById('rowsB').value = 3;
		        document.getElementById('colsB').value = 2;
		        createMatrixInputs();
		        
		        const presetA = [
		            [1, 2, 3],
		            [4, 5, 6]
		        ];
		        const presetB = [
		            [7, 8],
		            [9, 10],
		            [11, 12]
		        ];
		        fillMatrix('matrixA', presetA);
		        fillMatrix('matrixB', presetB);
		        document.getElementById('matrixB').classList.remove('hidden');
		    }
		}
        
        function fillMatrix(prefix, values) {
            for (let i = 0; i < values.length; i++) {
                for (let j = 0; j < values[0].length; j++) {
                    document.getElementById(`${prefix}_${i}_${j}`).value = values[i][j];
                }
            }
        }
        
        function calculate() {
            const operation = document.getElementById('operation').value;
            const needsSingleMatrix = singleMatrixOperations.includes(operation);
            const protocol = document.querySelector('input[name="protocol"]:checked').value;
            
            const rowsA = parseInt(document.getElementById('rowsA').value);
            const colsA = parseInt(document.getElementById('colsA').value);
            const matrixA = getMatrixValues('matrixA', rowsA, colsA);
            
            let matrixB = [];
            if (!needsSingleMatrix) {
                const rowsB = parseInt(document.getElementById('rowsB').value);
                const colsB = parseInt(document.getElementById('colsB').value);
                matrixB = getMatrixValues('matrixB', rowsB, colsB);
            }
            
            // Show loading indicator
            document.getElementById("result").innerHTML = '<div class="loading">Calculating...</div>';
            
            // Send to our JSP backend which will handle TCP/UDP
            fetch("<%= request.getContextPath() %>/calculate", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ 
                    protocol: protocol,
                    matrixA: matrixA, 
                    matrixB: matrixB, 
                    operation: operation,
                    dimensions: { 
                        rowsA: rowsA, 
                        colsA: colsA,
                        rowsB: needsSingleMatrix ? 0 : parseInt(document.getElementById('rowsB').value),
                        colsB: needsSingleMatrix ? 0 : parseInt(document.getElementById('colsB').value)
                    }
                })
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(err => { throw new Error(err); });
                }
                return response.json();
            })
            .then(data => {
                if (data.error) {
                    document.getElementById("result").innerHTML = 
                        `<div class="error">Error: ${data.error}</div>`;
                } else {
                    displayResult(data.result, 
                        data.result.length, 
                        data.result[0]?.length || 1
                    );
                }
            })
            .catch(error => {
                document.getElementById("result").innerHTML = 
                    `<div class="error">${error.message || "Server error"}</div>`;
            });
        }
        
        function getMatrixValues(prefix, rows, cols) {
            const matrix = [];
            for (let i = 0; i < rows; i++) {
                const row = [];
                for (let j = 0; j < cols; j++) {
                    const val = parseFloat(document.getElementById(`${prefix}_${i}_${j}`).value) || 0;
                    row.push(val);
                }
                matrix.push(row);
            }
            return matrix;
        }
        
        function displayResult(matrix, rows, cols) {
            let html = "<h3>Result</h3><table>";
            for (let i = 0; i < rows; i++) {
                html += "<tr>";
                for (let j = 0; j < cols; j++) {
                    const value = matrix[i][j];
                    // Show as integer if no decimal, otherwise show 2 decimal places
                    html += `<td>${Number.isInteger(value) ? value : value.toFixed(2)}</td>`;
                }
                html += "</tr>";
            }
            html += "</table>";
            document.getElementById("result").innerHTML = html;
        }
    </script>
</body>
</html>