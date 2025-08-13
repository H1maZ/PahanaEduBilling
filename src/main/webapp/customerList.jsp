<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.model.Customer" %>
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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Management - Pahana Edu Bookshop</title>
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
        .customer-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            margin-bottom: 1rem;
        }
        .customer-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        .customer-info {
            padding: 1.5rem;
        }
        .customer-actions {
            padding: 1rem 1.5rem;
            background-color: #f8f9fa;
            border-top: 1px solid #dee2e6;
            border-radius: 0 0 10px 10px;
        }
        .btn-action {
            margin-right: 0.5rem;
            border-radius: 5px;
        }
        .btn-edit {
            background-color: #ffc107;
            border-color: #ffc107;
            color: #000;
        }
        .btn-edit:hover {
            background-color: #e0a800;
            border-color: #d39e00;
            color: #000;
        }
        .btn-delete {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-delete:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
        .alert {
            border-radius: 10px;
            border: none;
        }
        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #6c757d;
        }
        .empty-state i {
            font-size: 4rem;
            margin-bottom: 1rem;
            color: #dee2e6;
        }
        .stats-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 1.5rem;
            margin-bottom: 2rem;
        }
        .stats-number {
            font-size: 2rem;
            font-weight: bold;
            color: #667eea;
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
                <h1><i class="fas fa-users me-2"></i>Customer Management</h1>
                <p class="mb-0">Manage your customer database efficiently</p>
            </div>
            <div class="col-md-4 text-end">
                <a href="add-customer.jsp" class="btn btn-light btn-lg">
                    <i class="fas fa-plus me-2"></i>Add New Customer
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

    <!-- Statistics -->
    <%
        List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
        int customerCount = customerList != null ? customerList.size() : 0;
    %>
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="stats-card">
                <div class="d-flex align-items-center">
                    <div class="flex-shrink-0">
                        <i class="fas fa-users fa-2x text-primary"></i>
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <h6 class="mb-0">Total Customers</h6>
                        <div class="stats-number"><%= customerCount %></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stats-card">
                <div class="d-flex align-items-center">
                    <div class="flex-shrink-0">
                        <i class="fas fa-chart-line fa-2x text-success"></i>
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <h6 class="mb-0">Active Customers</h6>
                        <div class="stats-number"><%= customerCount %></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stats-card">
                <div class="d-flex align-items-center">
                    <div class="flex-shrink-0">
                        <i class="fas fa-plus-circle fa-2x text-warning"></i>
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <h6 class="mb-0">New This Month</h6>
                        <div class="stats-number">0</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Customer List -->
    <%
        if (customerList != null && !customerList.isEmpty()) {
    %>
    <div class="row">
        <%
            for (Customer c : customerList) {
        %>
        <div class="col-md-6 col-lg-4">
            <div class="customer-card">
                <div class="customer-info">
                    <div class="d-flex align-items-center mb-3">
                        <div class="flex-shrink-0">
                            <div class="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                                <i class="fas fa-user"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h5 class="mb-1"><%= c.getName() %></h5>
                            <small class="text-muted">ID: <%= c.getAccountNumber() %></small>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-12 mb-2">
                            <small class="text-muted">
                                <i class="fas fa-map-marker-alt me-1"></i>
                                <%= c.getAddress() != null ? c.getAddress() : "No address provided" %>
                            </small>
                        </div>
                        <div class="col-6">
                            <small class="text-muted">
                                <i class="fas fa-phone me-1"></i>
                                <%= c.getPhone() != null ? c.getPhone() : "No phone" %>
                            </small>
                        </div>
                        <div class="col-6">
                            <small class="text-muted">
                                <i class="fas fa-shopping-cart me-1"></i>
                                <%= c.getUnitsConsumed() %> purchases
                            </small>
                        </div>
                    </div>
                </div>
                
                <div class="customer-actions">
                    <div class="d-flex justify-content-between">
                        <a href="edit-customer.jsp?accountNumber=<%= c.getAccountNumber() %>" 
                           class="btn btn-edit btn-sm">
                            <i class="fas fa-edit me-1"></i>Edit
                        </a>
                        
                        <form action="customer" method="post" style="display:inline;"
                              onsubmit="return confirm('Are you sure you want to delete this customer? This action cannot be undone.');">
                            <input type="hidden" name="action" value="delete" />
                            <input type="hidden" name="accountNumber" value="<%= c.getAccountNumber() %>" />
                            <button type="submit" class="btn btn-delete btn-sm">
                                <i class="fas fa-trash me-1"></i>Delete
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
        } else {
    %>
    <div class="empty-state">
        <i class="fas fa-users"></i>
        <h3>No Customers Found</h3>
        <p>Get started by adding your first customer to the system.</p>
        <a href="add-customer.jsp" class="btn btn-primary">
            <i class="fas fa-plus me-2"></i>Add First Customer
        </a>
    </div>
    <%
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
