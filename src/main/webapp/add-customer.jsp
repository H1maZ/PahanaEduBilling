<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Check if user is logged in
    if (session.getAttribute("loggedIn") == null) {
        response.sendRedirect("login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Customer - Pahana Edu Bookshop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .navbar-brand {
            font-weight: bold;
        }
        .page-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
        .form-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-bottom: 2rem;
        }
        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e9ecef;
            padding: 0.75rem 1rem;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.5rem;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 8px;
            padding: 0.75rem 2rem;
            font-weight: 600;
            transition: transform 0.3s ease;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-secondary {
            border-radius: 8px;
            padding: 0.75rem 2rem;
            font-weight: 600;
        }
        .input-group-text {
            background-color: #f8f9fa;
            border: 2px solid #e9ecef;
            border-right: none;
            border-radius: 8px 0 0 8px;
        }
        .input-group .form-control {
            border-left: none;
            border-radius: 0 8px 8px 0;
        }
        .alert {
            border-radius: 10px;
            border: none;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">
            <i class="fas fa-book me-2"></i>Pahana Edu Bookshop
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="books">Books</a></li>
                <li class="nav-item"><a class="nav-link active" href="customers">Customers</a></li>
                <li class="nav-item"><a class="nav-link" href="cart">Cart</a></li>
                <li class="nav-item"><a class="nav-link" href="billing-history">Billing History</a></li>
                <li class="nav-item"><a class="nav-link" href="help">Help</a></li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <span class="navbar-text me-3">
                        <i class="fas fa-user me-1"></i>Welcome, <%= session.getAttribute("username") %>
                    </span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout">
                        <i class="fas fa-sign-out-alt me-1"></i>Logout
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Page Header -->
<div class="page-header">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h1><i class="fas fa-user-plus me-2"></i>Add New Customer</h1>
                <p class="mb-0">Register a new customer in the system</p>
            </div>
            <div class="col-md-4 text-end">
                <a href="customers" class="btn btn-outline-light btn-lg">
                    <i class="fas fa-arrow-left me-2"></i>Back to Customers
                </a>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!-- Messages -->
    <%
        String msg = request.getParameter("message");
        String err = request.getParameter("error");
        if (msg != null) {
    %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="fas fa-check-circle me-2"></i><%= msg %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <%
        }
        if (err != null) {
    %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fas fa-exclamation-circle me-2"></i><%= err %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <%
        }
    %>

    <!-- Customer Form -->
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="form-card">
                <form action="customer" method="post" id="customerForm">
                    <input type="hidden" name="action" value="add" />

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="accountNumber" class="form-label">
                                <i class="fas fa-id-card me-1"></i>Account Number
                            </label>
                            <input type="text" class="form-control" id="accountNumber" name="accountNumber" 
                                   required placeholder="Enter account number">
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="name" class="form-label">
                                <i class="fas fa-user me-1"></i>Full Name
                            </label>
                            <input type="text" class="form-control" id="name" name="name" 
                                   required placeholder="Enter full name">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="address" class="form-label">
                            <i class="fas fa-map-marker-alt me-1"></i>Address
                        </label>
                        <textarea class="form-control" id="address" name="address" rows="3" 
                                  required placeholder="Enter complete address"></textarea>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="phone" class="form-label">
                                <i class="fas fa-phone me-1"></i>Phone Number
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-phone"></i>
                                </span>
                                <input type="tel" class="form-control" id="phone" name="phone" 
                                       required placeholder="Enter phone number">
                            </div>
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="unitsConsumed" class="form-label">
                                <i class="fas fa-shopping-cart me-1"></i>Units Consumed
                            </label>
                            <input type="number" class="form-control" id="unitsConsumed" name="unitsConsumed" 
                                   min="0" value="0" required>
                        </div>
                    </div>

                    <div class="d-flex justify-content-between mt-4">
                        <a href="customers" class="btn btn-secondary">
                            <i class="fas fa-times me-2"></i>Cancel
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>Add Customer
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Form validation
    document.getElementById('customerForm').addEventListener('submit', function(e) {
        const accountNumber = document.getElementById('accountNumber').value.trim();
        const name = document.getElementById('name').value.trim();
        const phone = document.getElementById('phone').value.trim();
        
        if (accountNumber === '' || name === '' || phone === '') {
            e.preventDefault();
            alert('Please fill in all required fields.');
            return false;
        }
        
        // Basic phone number validation
        const phoneRegex = /^[\+]?[1-9][\d]{0,15}$/;
        if (!phoneRegex.test(phone.replace(/\s/g, ''))) {
            e.preventDefault();
            alert('Please enter a valid phone number.');
            return false;
        }
    });
</script>
</body>
</html>
