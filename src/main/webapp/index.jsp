<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Check if user is logged in
    if (session.getAttribute("loggedIn") == null) {
        response.sendRedirect("login");
        return;
    }
%>
<html>
<head>
    <title>Pahana Edu Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .dashboard-card {
            transition: transform 0.3s ease;
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
        .welcome-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">Pahana Edu Bookshop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="books">Books</a></li>
                <li class="nav-item"><a class="nav-link" href="customers">Customers</a></li>
                <li class="nav-item"><a class="nav-link" href="cart">Cart</a></li>
                <li class="nav-item"><a class="nav-link" href="billing-history">Billing History</a></li>
                <li class="nav-item"><a class="nav-link" href="help">Help</a></li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <span class="navbar-text me-3">Welcome, <%= session.getAttribute("username") %></span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container mt-4">
    <!-- Welcome Section -->
    <div class="welcome-section text-center">
        <h1>Welcome to Pahana Edu Bookshop</h1>
        <p>Manage your bookstore operations efficiently</p>
    </div>

    <!-- Statistics Cards -->
    <div class="row mb-4">
        <div class="col-md-6">
            <div class="card dashboard-card bg-primary text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Total Books Available</h5>
                    <h2><%= request.getAttribute("bookCount") != null ? request.getAttribute("bookCount") : "0" %></h2>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card dashboard-card bg-success text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Total Customers</h5>
                    <h2><%= request.getAttribute("customerCount") != null ? request.getAttribute("customerCount") : "0" %></h2>
                </div>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="row">
        <div class="col-md-12">
            <h3 class="text-center mb-4">Quick Actions</h3>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-3 mb-3">
            <div class="card dashboard-card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">Add Book</h5>
                    <p class="card-text">Add new books to inventory</p>
                    <a href="add-book.jsp" class="btn btn-primary">Add Book</a>
                </div>
            </div>
        </div>
        
        <div class="col-md-3 mb-3">
            <div class="card dashboard-card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">Add Customer</h5>
                    <p class="card-text">Register new customers</p>
                    <a href="add-customer.jsp" class="btn btn-success">Add Customer</a>
                </div>
            </div>
        </div>
        
        <div class="col-md-3 mb-3">
            <div class="card dashboard-card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">View Books</h5>
                    <p class="card-text">Browse and manage books</p>
                    <a href="books" class="btn btn-info">View Books</a>
                </div>
            </div>
        </div>
        
        <div class="col-md-3 mb-3">
            <div class="card dashboard-card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">Start Billing</h5>
                    <p class="card-text">Create bills for customers</p>
                    <a href="books" class="btn btn-warning">Start Billing</a>
                </div>
            </div>
        </div>
        
        <div class="col-md-3 mb-3">
            <div class="card dashboard-card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">Billing History</h5>
                    <p class="card-text">View and reprint invoices</p>
                    <a href="billing-history" class="btn btn-info">View History</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
