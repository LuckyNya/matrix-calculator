<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@400;500&display=swap" rel="stylesheet">
    <title>Matrix Calculator</title>
    <style>
        body {
	        transform: scale(1.5);
		    transform-origin: 0 0;
		    width: 66.67%;
		    font-family: 'Be Vietnam Pro', Arial, sans-serif;
		    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%) fixed; /* Add 'fixed' */
		    min-height: 100vh;
		    margin: 0;
		    padding: 20px;
		    box-sizing: border-box;
		    position: relative;
		    overflow-x: hidden;
		}
		
		html {
		    height: 100%;
		    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%); /* Match body background */
		}
	    
	    .calculator-container {
	        background-color: white;
		    border-radius: 15px;
		    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
		    padding: 30px;
		    margin: 20px auto;
		    max-width: 800px;
		    border: 1px solid #e0e0e0;
	    }
	    
	    h1 {
	        color: #2c3e50;
	        margin-bottom: 25px;
	        font-size: 1.8em;
	    }
        .control-panel {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            justify-content: center;
            margin-bottom: 20px;
            display: grid;
    		grid-template-columns: 1fr 1fr;
        }
        .control-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        .matrix-control-group {
		    display: flex;
		    gap: 10px;
		}
		.control-group.compact {
		    margin-bottom: 0;
		}
		
		.control-group.compact label {
		    min-width: 50px;
		    text-align: right;
		}
		
		.control-group.compact input {
		    width: 50px;
		}
		
		.control-group.full-width {
		    grid-column: span 2;
		}
		.protocol-options {
		    display: flex;
		    gap: 15px;
		    align-items: center;
		    margin: 5px 0;
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
	        background-color: #3498db;
	        color: white;
	        border: none;
	        padding: 10px 15px;
	        border-radius: 6px;
	        cursor: pointer;
	        font-family: inherit;
	        font-weight: bold;
	        transition: all 0.3s;
	    }
	    
	    button:hover {
	        background-color: #2980b9;
	        transform: translateY(-2px);
	        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
	    }
        .matrix-container {
            display: flex;
    		flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 20px;
            justify-content: center;
        }
        .matrix {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 5px;
            background-color: #f9f9f9;
            max-width: 100%;
		    overflow-x: auto;
		    display: inline-block;
		    white-space: nowrap;
		    padding-bottom: 15px;
        }
        .matrix h3 {
            margin-top: 0;
            color: #2c3e50;
            text-align: center;
        }
        .matrix-input {
            width: 40px;  /* Reduce from 60px */
		    margin: 2px;  /* Reduce from 3px */
		    padding: 3px; /* Reduce from 5px */
		    font-size: 12px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
       #result {
		    margin-top: 30px;
		    padding: 20px;
		    background-color: #f9f9f9;
		    border-radius: 5px;
		    border: 1px solid #ddd;
		    max-height: 50vh; /* Limits height */
		    overflow-y: auto; /* Adds scroll if needed */
		}
		.result-input {
		    width: 60px;          /* Same as matrix-input */
		    height: 28px;         /* Same height */
		    margin: 3px;          /* Same margin */
		    padding: 5px;         /* Same padding */
		    border: 1px solid #ddd;
		    border-radius: 3px;
		    text-align: center;
		    font-size: 14px;      /* Same font size */
		    background-color: white;
		    box-sizing: border-box;
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
            border-collapse: separate;
		    border-spacing: 0;
		    margin: 0 auto;
        }
        #result td {
            padding: 0;
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
		@media (max-width: 768px) {
		    .result-input {
		        width: 50px;
		        font-size: 12px;
		        padding: 3px;
		    }
		}
	    @media (max-width: 480px) {
		    .result-input {
		        width: 40px;
		        font-size: 11px;
		    }
		}
    </style>
</head>
<body>
    <div class="calculator-container">
        <h1>Matrix Calculator</h1>
        
        <div class="control-panel">
            <div class="control-group full-width">
		        <label id="operation-label">Phép toán:</label>
		        <select id="operation" onchange="updateMatrixInputs()">
				    <option value="add">Cộng (A+B)</option>
				    <option value="subtract">Trừ (A-B)</option>
				    <option value="multiply">Nhân (A&times;B)</option>
				    <option value="hadamard">Nhân Hadamard (A&#8728;B)</option>
				    <option value="kronecker">Nhân Kronecker (A&#8855;B)</option>
				    <option value="divide">Chia (A/B)</option>
				    <option value="transpose">Chuyển vị (A&#x1D40;)</option>
				    <option value="determinant">Định thức (|A|)</option>
				    <option value="inverse">Khả nghịch (A&#8315;&#185;)</option>
				    <option value="trace">Vết ma trận (tr(A))</option>
				</select>
            </div>
            
            <div class="matrix-control-group">
		        <div class="control-group compact">
		            <label id="rowsA-label">Hàng A:</label>
		            <input type="number" id="rowsA" min="1" max="10" value="2">
		        </div>
		        <div class="control-group compact">
		            <label id="colsA-label">Cột A:</label>
		            <input type="number" id="colsA" min="1" max="10" value="2">
		        </div>
		    </div>
		    
		    <!-- Matrix B Controls -->
		    <div class="matrix-control-group" id="matrixBControls">
		        <div class="control-group compact">
		            <label id="rowsB-label">Hàng B:</label>
		            <input type="number" id="rowsB" min="1" max="10" value="2">
		        </div>
		        <div class="control-group compact" id="matrixBColsControls">
		            <label id="colsB-label">Cột B:</label>
		            <input type="number" id="colsB" min="1" max="10" value="2">
		        </div>
		    </div>
            
            <div class="control-group full-width">
		        <label id="protocol-label">Giao thức:</label>
		        <div class="protocol-options">
		            <input type="radio" id="tcp" name="protocol" value="tcp" checked>
		            <label for="tcp" id="tcp-label">TCP</label>
		            <input type="radio" id="udp" name="protocol" value="udp">
		            <label for="udp" id="udp-label">UDP</label>
		        </div>
		        <button id="create-matrices-btn" onclick="createMatrixInputs()">Tạo Ma Trận</button>
   			</div>
        </div>
        
        <div class="matrix-container">
		    <div class="matrix" id="matrixA">
		        <h3>Ma trận A</h3>
		    </div>
		    <div class="matrix" id="matrixB">
		        <h3>Ma trận B</h3>
		    </div>
		</div>
        
        <div class="button-group">
            <button id="fill-random-btn" type="button" onclick="fillRandom()">Điền Số Ngẫu Nhiên</button>
            <button id="load-sample-btn" onclick="loadSampleMatrix()">Tải Ma Trận Mẫu</button>
            <button id="calculate-btn" onclick="calculate()">Tính Toán</button>
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
		        matrixBTitle.textContent = 'Xem trước kết quả';
		    } else {
		        matrixBTitle.textContent = 'Ma trận B';
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
		        document.getElementById('matrixB').innerHTML = '<h3>Xem trước kết quả</h3>';
		        document.getElementById('matrixB').classList.add('hidden');
		    }
		}
        
        function createMatrix(containerId, rows, cols) {
            const container = document.getElementById(containerId);
            const title = containerId === 'matrixA' ? 'Ma trận A' : 'Ma trận B';
            container.innerHTML = `<h3>${title}</h3>`;
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
            document.getElementById("result").innerHTML = '<div class="loading">Đang tính toán...</div>';
            
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
			    // Handle both JSON success and plain text errors
			    if (typeof data === 'string' || data.error) {
			        const errorMessage = typeof data === 'string' ? data : data.error;
			        document.getElementById("result").innerHTML = 
			            `<div class="error">Lỗi: ${errorMessage}</div>`;
			    } else {
			        displayResult(data.result, 
			            data.result.length, 
			            data.result[0]?.length || 1
			        );
			    }
			})
			.catch(error => {
			    try {
			        // Try to parse error message from JSON
			        const errorObj = JSON.parse(error.message);
			        const cleanError = errorObj.error || error.message;
			        document.getElementById("result").innerHTML = 
			            `<div class="error">Lỗi: ${cleanError}</div>`;
			    } catch (e) {
			        // If not JSON, show original message
			        document.getElementById("result").innerHTML = 
			            `<div class="error">Lỗi kết nối: ${error.message || "Không thể kết nối tới máy chủ"}</div>`;
			    }
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
            let html = `<h3>Kết quả</h3><table style="font-size: inherit;">`;
            for (let i = 0; i < rows; i++) {
                html += "<tr>";
                for (let j = 0; j < cols; j++) {
                    const value = matrix[i][j];
                    // Match the input styling exactly
                    html += `<td><input type="text" class="result-input" value="${
                        Number.isInteger(value) ? value : value.toFixed(2)
                    }" readonly></td>`;
                }
                html += "</tr>";
            }
            html += "</table>";
            document.getElementById("result").innerHTML = html;
        }
    </script>
</body>
</html>