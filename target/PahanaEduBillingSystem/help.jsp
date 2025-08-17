<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Pahana Edu Bookshop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .help-section {
            margin-bottom: 30px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .help-section h3 {
            color: #333;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
        }
        .step-list {
            list-style-type: decimal;
            padding-left: 20px;
        }
        .step-list li {
            margin-bottom: 10px;
        }
        .highlight {
            background-color: #fff3cd;
            padding: 10px;
            border-radius: 5px;
            border-left: 4px solid #ffc107;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center mb-4">Pahana Edu Bookshop - Help Guide</h1>
                
                <div class="help-section">
                    <h3>1. User Authentication</h3>
                    <p>To access the system, you need to log in with valid credentials:</p>
                    <ul class="step-list">
                        <li>Navigate to the login page</li>
                        <li>Enter username: <strong>staff</strong></li>
                        <li>Enter password: <strong>staff123</strong></li>
                        <li>Click "Login" to access the dashboard</li>
                    </ul>
                </div>

                <div class="help-section">
                    <h3>2. Managing Books</h3>
                    <p>You can add, edit, and delete books from the inventory:</p>
                    <ul class="step-list">
                        <li><strong>Add Book:</strong> Click "Add Book" from the navigation menu, fill in the details, and submit</li>
                        <li><strong>View Books:</strong> Click "View Books" to see all available books</li>
                        <li><strong>Edit Book:</strong> Click the "Edit" button next to any book to modify its details</li>
                        <li><strong>Delete Book:</strong> Click the "Delete" button next to any book to remove it from inventory</li>
                    </ul>
                </div>

                <div class="help-section">
                    <h3>3. Managing Customers</h3>
                    <p>Customer management functions:</p>
                    <ul class="step-list">
                        <li><strong>Add Customer:</strong> Click "Add Customer" to register new customers</li>
                        <li><strong>View Customers:</strong> Click "View Customers" to see all registered customers</li>
                        <li><strong>Edit Customer:</strong> Click "Edit" next to any customer to update their information</li>
                        <li><strong>Delete Customer:</strong> Click "Delete" to remove a customer from the system</li>
                    </ul>
                </div>

                <div class="help-section">
                    <h3>4. Billing Process</h3>
                    <p>Complete billing workflow:</p>
                    <ul class="step-list">
                        <li><strong>Add to Cart:</strong> From the book list, select quantity and click "Add to Cart"</li>
                        <li><strong>View Cart:</strong> Click "View Cart" to see selected items and modify quantities</li>
                        <li><strong>Checkout:</strong> Click "Proceed to Checkout" to select a customer</li>
                        <li><strong>Generate Bill:</strong> Select customer and click "Generate Bill" to create invoice</li>
                        <li><strong>Print Invoice:</strong> Use the "Print Invoice" button to print the bill</li>
                    </ul>
                </div>

                <div class="help-section">
                    <h3>5. Dashboard Features</h3>
                    <p>The dashboard provides an overview of your business:</p>
                    <ul class="step-list">
                        <li>Total number of books in inventory</li>
                        <li>Total number of registered customers</li>
                        <li>Quick access to billing functions</li>
                        <li>Navigation to all system features</li>
                    </ul>
                </div>

                <div class="highlight">
                    <h4>Important Notes:</h4>
                    <ul>
                        <li>Stock levels are automatically updated when bills are generated</li>
                        <li>You cannot add more items to cart than available in stock</li>
                        <li>All transactions are logged with timestamps</li>
                        <li>Use the logout button to securely exit the system</li>
                    </ul>
                </div>

                <div class="text-center mt-4">
                    <a href="dashboard" class="btn btn-primary">Back to Dashboard</a>
                    <a href="logout" class="btn btn-secondary">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
