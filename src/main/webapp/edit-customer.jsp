<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahana.dao.CustomerDAO" %>
<%@ page import="com.pahana.dao.DBConnection" %>
<%@ page import="com.pahana.model.Customer" %>
<%@ page import="java.sql.Connection" %>

<%
    String accountNumberParam = request.getParameter("accountNumber");
    Customer customer = null;

    if (accountNumberParam != null && !accountNumberParam.isEmpty()) {
        try (Connection conn = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(conn);
            customer = customerDAO.getCustomerByAccountNumber(accountNumberParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    if (customer == null) {
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Not Found - Pahana Billing System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .error-container {
            background: white;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 400px;
            width: 90%;
        }
        
        .error-icon {
            font-size: 4rem;
            color: #e74c3c;
            margin-bottom: 1rem;
        }
        
        .error-title {
            color: #2c3e50;
            margin-bottom: 1rem;
            font-size: 1.5rem;
        }
        
        .error-message {
            color: #7f8c8d;
            margin-bottom: 2rem;
        }
        
        .back-btn {
            display: inline-block;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.3s ease;
            font-weight: 500;
        }
        
        .back-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        <h1 class="error-title">Customer Not Found</h1>
        <p class="error-message">The customer you're looking for doesn't exist or has been removed.</p>
        <a href="customers" class="back-btn">Back to Customer List</a>
    </div>
</body>
</html>
<%
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Customer - Pahana Billing System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem;
            text-align: center;
        }
        
        .header h1 {
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }
        
        .header p {
            opacity: 0.9;
            font-size: 1.1rem;
        }
        
        .form-container {
            padding: 2rem;
        }
        
        .form-group {
            margin-bottom: 1.5rem;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #2c3e50;
            font-weight: 600;
            font-size: 0.95rem;
        }
        
        .form-group input {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e1e8ed;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background: #f8f9fa;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
            background: white;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        .form-group input:disabled {
            background: #f1f3f4;
            color: #6c757d;
            cursor: not-allowed;
        }
        
        .account-number-display {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 1rem;
            border-radius: 8px;
            border-left: 4px solid #667eea;
            margin-bottom: 1.5rem;
        }
        
        .account-number-display strong {
            color: #667eea;
        }
        
        .button-group {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 120px;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background: #5a6268;
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(108, 117, 125, 0.3);
        }
        
        .btn-danger {
            background: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background: #c82333;
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(220, 53, 69, 0.3);
        }
        
        .icon {
            margin-right: 8px;
            font-size: 1.1rem;
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }
        
        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
            }
            
            .button-group {
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
            }
            
            .container {
                margin: 10px;
            }
            
            .header {
                padding: 1.5rem;
            }
            
            .form-container {
                padding: 1.5rem;
            }
        }
        
        .loading {
            opacity: 0.7;
            pointer-events: none;
        }
        
        .success-message {
            background: #d4edda;
            color: #155724;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1rem;
            border-left: 4px solid #28a745;
        }
        
        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1rem;
            border-left: 4px solid #dc3545;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📝 Edit Customer</h1>
            <p>Update customer information and billing details</p>
        </div>
        
        <div class="form-container">
            <div class="account-number-display">
                <strong>Account Number:</strong> <%= customer.getAccountNumber() %>
            </div>
            
            <form action="customer" method="post" id="editForm">
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="accountNumber" value="<%= customer.getAccountNumber() %>" />
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="name">👤 Full Name</label>
                        <input type="text" id="name" name="name" value="<%= customer.getName() %>" required 
                               placeholder="Enter customer's full name" />
                    </div>
                    
                    <div class="form-group">
                        <label for="phone">📞 Phone Number</label>
                        <input type="tel" id="phone" name="phone" value="<%= customer.getPhone() %>" 
                               placeholder="Enter phone number" />
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="address">🏠 Address</label>
                    <input type="text" id="address" name="address" value="<%= customer.getAddress() %>" 
                           placeholder="Enter complete address" />
                </div>
                
                <div class="form-group">
                    <label for="unitsConsumed">⚡ Units Consumed</label>
                    <input type="number" id="unitsConsumed" name="unitsConsumed" 
                           value="<%= customer.getUnitsConsumed() %>" min="0" step="0.01"
                           placeholder="Enter units consumed" />
                </div>
                
                <div class="button-group">
                    <button type="submit" class="btn btn-primary">
                        <span class="icon">💾</span>
                        Update Customer
                    </button>
                    
                    <a href="customers" class="btn btn-secondary">
                        <span class="icon">←</span>
                        Back to List
                    </a>
                    
                    <a href="customer?action=delete&accountNumber=<%= customer.getAccountNumber() %>" 
                       class="btn btn-danger" 
                       onclick="return confirm('Are you sure you want to delete this customer? This action cannot be undone.')">
                        <span class="icon">🗑️</span>
                        Delete Customer
                    </a>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        // Form submission handling
        document.getElementById('editForm').addEventListener('submit', function(e) {
            const form = this;
            const submitBtn = form.querySelector('button[type="submit"]');
            
            // Add loading state
            form.classList.add('loading');
            submitBtn.innerHTML = '<span class="icon">⏳</span> Updating...';
            submitBtn.disabled = true;
        });
        
        // Input validation
        document.getElementById('phone').addEventListener('input', function(e) {
            // Remove non-numeric characters except +, -, (, ), and space
            this.value = this.value.replace(/[^\d\s\+\-\(\)]/g, '');
        });
        
        document.getElementById('unitsConsumed').addEventListener('input', function(e) {
            // Ensure positive numbers only
            if (this.value < 0) {
                this.value = 0;
            }
        });
        
        // Auto-save functionality (optional)
        let autoSaveTimeout;
        const inputs = document.querySelectorAll('input[type="text"], input[type="tel"], input[type="number"]');
        
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                clearTimeout(autoSaveTimeout);
                autoSaveTimeout = setTimeout(() => {
                    // You can implement auto-save here if needed
                    console.log('Auto-save triggered for:', this.name);
                }, 2000);
            });
        });
    </script>
</body>
</html>
