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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>

        html, body {
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        }

        * {
            font-family: 'Inter', sans-serif;
        }
        
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
        }
        
        .navbar {
            background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%) !important;
            box-shadow: 0 2px 20px rgba(0,0,0,0.1);
            backdrop-filter: blur(10px);
        }
        
        .navbar-brand {
            font-weight: 700;
            font-size: 1.5rem;
            color: #ecf0f1 !important;
        }
        
        .nav-link {
            font-weight: 500;
            transition: all 0.3s ease;
            position: relative;
        }
        
        .nav-link:hover {
            color: #3498db !important;
            transform: translateY(-2px);
        }
        
        .welcome-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px;
            border-radius: 20px;
            margin-bottom: 40px;
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
            position: relative;
            overflow: hidden;
        }
        
        .welcome-section::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -50%;
            width: 200%;
            height: 200%;
            background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
            animation: float 6s ease-in-out infinite;
        }
        
        @keyframes float {
            0%, 100% { transform: translateY(0px) rotate(0deg); }
            50% { transform: translateY(-20px) rotate(180deg); }
        }
        
        .welcome-section h1 {
            font-weight: 700;
            font-size: 2.5rem;
            margin-bottom: 10px;
            position: relative;
            z-index: 1;
        }
        
        .welcome-section p {
            font-size: 1.1rem;
            opacity: 0.9;
            position: relative;
            z-index: 1;
        }
        
        .stats-card {
            background: white;
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
            border: none;
            position: relative;
            overflow: hidden;
        }
        
        .stats-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #667eea, #764ba2);
        }
        
        .stats-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.15);
        }
        
        .stats-card.books {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .stats-card.customers {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }
        
        .stats-icon {
            font-size: 3rem;
            margin-bottom: 20px;
            opacity: 0.8;
        }
        
        .stats-number {
            font-size: 3rem;
            font-weight: 700;
            margin-bottom: 10px;
        }
        
        .stats-label {
            font-size: 1.1rem;
            font-weight: 500;
            opacity: 0.9;
        }
        
        .quick-actions-section {
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            margin-top: 30px;
        }
        
        .section-title {
            font-weight: 700;
            font-size: 2rem;
            color: #2c3e50;
            margin-bottom: 30px;
            text-align: center;
            position: relative;
        }
        
        .section-title::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 60px;
            height: 4px;
            background: linear-gradient(90deg, #667eea, #764ba2);
            border-radius: 2px;
        }
        
        .action-card {
            background: white;
            border-radius: 15px;
            padding: 25px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            transition: all 0.3s ease;
            border: none;
            height: 100%;
            position: relative;
            overflow: hidden;
        }
        
        .action-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 3px;
            background: linear-gradient(90deg, #667eea, #764ba2);
            transform: scaleX(0);
            transition: transform 0.3s ease;
        }
        
        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.15);
        }
        
        .action-card:hover::before {
            transform: scaleX(1);
        }
        
        .action-icon {
            font-size: 2.5rem;
            margin-bottom: 20px;
            color: #667eea;
        }
        
        .action-title {
            font-weight: 600;
            font-size: 1.2rem;
            color: #2c3e50;
            margin-bottom: 10px;
        }
        
        .action-description {
            color: #7f8c8d;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }
        
        .btn-modern {
            border-radius: 25px;
            padding: 10px 25px;
            font-weight: 500;
            transition: all 0.3s ease;
            border: none;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-modern:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
        
        .btn-primary-modern {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-success-modern {
            background: linear-gradient(135deg, #56ab2f 0%, #a8e6cf 100%);
            color: white;
        }
        
        .btn-info-modern {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }
        
        .btn-warning-modern {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            color: white;
        }
        
        .navbar-text {
            color: #ecf0f1 !important;
            font-weight: 500;
        }
        
        .container {
            max-width: 1200px;
        }
        
        @media (max-width: 768px) {
            .welcome-section h1 {
                font-size: 2rem;
            }
            
            .stats-number {
                font-size: 2.5rem;
            }
            
            .section-title {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">
            <i class="fas fa-book-open me-2"></i>Pahana Edu Bookshop
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="books"><i class="fas fa-books me-1"></i>Books</a></li>
                <li class="nav-item"><a class="nav-link" href="customers"><i class="fas fa-users me-1"></i>Customers</a></li>
                <li class="nav-item"><a class="nav-link" href="cart"><i class="fas fa-shopping-cart me-1"></i>Cart</a></li>
                <li class="nav-item"><a class="nav-link" href="billing-history"><i class="fas fa-history me-1"></i>Billing History</a></li>
                <li class="nav-item"><a class="nav-link" href="help"><i class="fas fa-question-circle me-1"></i>Help</a></li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <span class="navbar-text me-3">
                        <i class="fas fa-user-circle me-1"></i>Welcome, <%= session.getAttribute("username") %>
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

<!-- Main Content -->
<div class="container mt-4">
    <!-- Welcome Section -->
    <div class="welcome-section text-center">
        <h1><i class="fas fa-chart-line me-3"></i>Dashboard Overview</h1>
        <p>Manage your bookstore operations efficiently with real-time insights</p>
    </div>

    <!-- Statistics Cards -->
    <div class="row mb-4">
        <div class="col-md-6 mb-4">
            <div class="stats-card books text-center">
                <div class="stats-icon">
                    <i class="fas fa-book"></i>
                </div>
                <div class="stats-number"><%= request.getAttribute("bookCount") != null ? request.getAttribute("bookCount") : "0" %></div>
                <div class="stats-label">Total Books Available</div>
            </div>
        </div>

        <div class="col-md-6 mb-4">
            <div class="stats-card customers text-center">
                <div class="stats-icon">
                    <i class="fas fa-users"></i>
                </div>
                <div class="stats-number"><%= request.getAttribute("customerCount") != null ? request.getAttribute("customerCount") : "0" %></div>
                <div class="stats-label">Total Customers</div>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions-section">
        <h3 class="section-title">Quick Actions</h3>
        
        <div class="row">
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-plus-circle"></i>
                    </div>
                    <div class="action-title">Add Book</div>
                    <div class="action-description">Add new books to your inventory</div>
                    <a href="add-book.jsp" class="btn btn-modern btn-primary-modern">Add Book</a>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-user-plus"></i>
                    </div>
                    <div class="action-title">Add Customer</div>
                    <div class="action-description">Register new customers</div>
                    <a href="add-customer.jsp" class="btn btn-modern btn-success-modern">Add Customer</a>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-list"></i>
                    </div>
                    <div class="action-title">View Books</div>
                    <div class="action-description">Browse and manage books</div>
                    <a href="books" class="btn btn-modern btn-info-modern">View Books</a>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-receipt"></i>
                    </div>
                    <div class="action-title">Start Billing</div>
                    <div class="action-description">Create bills for customers</div>
                    <a href="books" class="btn btn-modern btn-warning-modern">Start Billing</a>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-history"></i>
                    </div>
                    <div class="action-title">Billing History</div>
                    <div class="action-description">View and reprint invoices</div>
                    <a href="billing-history" class="btn btn-modern btn-info-modern">View History</a>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-shopping-cart"></i>
                    </div>
                    <div class="action-title">Shopping Cart</div>
                    <div class="action-description">Manage your cart items</div>
                    <a href="cart" class="btn btn-modern btn-primary-modern">View Cart</a>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-user-friends"></i>
                    </div>
                    <div class="action-title">Manage Customers</div>
                    <div class="action-description">View and edit customer details</div>
                    <a href="customers" class="btn btn-modern btn-success-modern">Manage</a>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="action-card text-center">
                    <div class="action-icon">
                        <i class="fas fa-question-circle"></i>
                    </div>
                    <div class="action-title">Help & Support</div>
                    <div class="action-description">Get help and documentation</div>
                    <a href="help" class="btn btn-modern btn-warning-modern">Get Help</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
