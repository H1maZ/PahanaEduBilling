<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.dto.BookDTO" %>
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
    <title>Book Management - Pahana Edu Bookshop</title>
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
        .book-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            margin-bottom: 1.5rem;
            overflow: hidden;
        }
        .book-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }
        .book-image-container {
            height: 200px;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            overflow: hidden;
        }
        .book-image {
            max-width: 100%;
            max-height: 100%;
            object-fit: cover;
            border-radius: 10px;
        }
        .no-image {
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6c757d;
            font-size: 3rem;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        }
        .book-info {
            padding: 1.5rem;
        }
        .book-title {
            font-size: 1.2rem;
            font-weight: bold;
            color: #333;
            margin-bottom: 0.5rem;
            line-height: 1.3;
        }
        .book-author {
            color: #6c757d;
            font-style: italic;
            margin-bottom: 1rem;
        }
        .book-price {
            font-size: 1.3rem;
            font-weight: bold;
            color: #28a745;
            margin-bottom: 0.5rem;
        }
        .book-stock {
            color: #6c757d;
            font-size: 0.9rem;
            margin-bottom: 1rem;
        }
        .book-actions {
            padding: 1rem 1.5rem;
            background-color: #f8f9fa;
            border-top: 1px solid #dee2e6;
        }
        .btn-action {
            margin-right: 0.5rem;
            border-radius: 8px;
            font-size: 0.9rem;
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
        .btn-add-cart {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-add-cart:hover {
            background-color: #218838;
            border-color: #1e7e34;
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
        .quantity-input {
            width: 80px;
            border-radius: 5px;
            border: 1px solid #ced4da;
            padding: 0.375rem 0.75rem;
            text-align: center;
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
                <li class="nav-item"><a class="nav-link active" href="books">Books</a></li>
                <li class="nav-item"><a class="nav-link" href="customers">Customers</a></li>
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
                <h1><i class="fas fa-books me-2"></i>Book Management</h1>
                <p class="mb-0">Browse and manage your book inventory</p>
            </div>
            <div class="col-md-4 text-end">
                <a href="add-book.jsp" class="btn btn-light btn-lg me-2">
                    <i class="fas fa-plus me-2"></i>Add New Book
                </a>
                <a href="cart" class="btn btn-outline-light btn-lg">
                    <i class="fas fa-shopping-cart me-2"></i>View Cart
                </a>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!-- Messages -->
    <%
        String message = (String) session.getAttribute("message");
        String error = (String) session.getAttribute("error");
        if (message != null) {
    %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="fas fa-check-circle me-2"></i><%= message %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <%
            session.removeAttribute("message");
        }
        if (error != null) {
    %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fas fa-exclamation-circle me-2"></i><%= error %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <%
            session.removeAttribute("error");
        }
    %>

    <!-- Statistics -->
    <%
        List<BookDTO> books = (List<BookDTO>) request.getAttribute("books");
        int bookCount = books != null ? books.size() : 0;
        int totalStock = 0;
        if (books != null) {
            for (BookDTO book : books) {
                totalStock += book.getStock();
            }
        }
    %>
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="stats-card">
                <div class="d-flex align-items-center">
                    <div class="flex-shrink-0">
                        <i class="fas fa-book fa-2x text-primary"></i>
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <h6 class="mb-0">Total Books</h6>
                        <div class="stats-number"><%= bookCount %></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stats-card">
                <div class="d-flex align-items-center">
                    <div class="flex-shrink-0">
                        <i class="fas fa-boxes fa-2x text-success"></i>
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <h6 class="mb-0">Total Stock</h6>
                        <div class="stats-number"><%= totalStock %></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stats-card">
                <div class="d-flex align-items-center">
                    <div class="flex-shrink-0">
                        <i class="fas fa-shopping-cart fa-2x text-warning"></i>
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <h6 class="mb-0">In Cart</h6>
                        <div class="stats-number">0</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Book List -->
    <%
        if (books == null || books.isEmpty()) {
    %>
    <div class="empty-state">
        <i class="fas fa-book"></i>
        <h3>No Books Available</h3>
        <p>Get started by adding your first book to the inventory.</p>
        <a href="add-book.jsp" class="btn btn-primary">
            <i class="fas fa-plus me-2"></i>Add First Book
        </a>
    </div>
    <%
        } else {
    %>
    <div class="row">
        <%
            for (BookDTO book : books) {
        %>
        <div class="col-md-6 col-lg-4">
            <div class="book-card">
                <div class="book-image-container">
                    <% if (book.getImageData() != null && book.getImageData().length > 0) { %>
                        <img src="book-image?id=<%= book.getBookId() %>" alt="<%= book.getTitle() %>" class="book-image" />
                    <% } else { %>
                        <div class="no-image">
                            <i class="fas fa-book"></i>
                        </div>
                    <% } %>
                </div>
                
                <div class="book-info">
                    <div class="book-title"><%= book.getTitle() %></div>
                    <div class="book-author">by <%= book.getAuthor() %></div>
                    <div class="book-price">Rs. <%= String.format("%.2f", book.getPrice()) %></div>
                    <div class="book-stock">
                        <i class="fas fa-boxes me-1"></i>
                        <%= book.getStock() %> in stock
                    </div>
                </div>
                
                <div class="book-actions">
                    <div class="row g-2">
                        <div class="col-12 mb-2">
                            <form action="cart" method="post" class="d-flex align-items-center">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                                <input type="number" name="quantity" value="1" min="1" max="<%= book.getStock() %>" 
                                       required class="quantity-input me-2">
                                <button type="submit" class="btn btn-add-cart btn-sm flex-grow-1">
                                    <i class="fas fa-cart-plus me-1"></i>Add to Cart
                                </button>
                            </form>
                        </div>
                        <div class="col-6">
                            <a href="edit-book.jsp?bookId=<%= book.getBookId() %>" 
                               class="btn btn-edit btn-sm w-100">
                                <i class="fas fa-edit me-1"></i>Edit
                            </a>
                        </div>
                        <div class="col-6">
                            <form action="books" method="post" style="display:inline;"
                                  onsubmit="return confirm('Are you sure you want to delete this book? This action cannot be undone.');">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />
                                <button type="submit" class="btn btn-delete btn-sm w-100">
                                    <i class="fas fa-trash me-1"></i>Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
