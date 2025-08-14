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
    <title>Book List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .book-image {
            max-width: 80px;
            max-height: 80px;
            border: 1px solid #ddd;
            border-radius: 4px;
            object-fit: cover;
        }
        .no-image {
            width: 80px;
            height: 80px;
            background-color: #f8f9fa;
            border: 1px solid #ddd;
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6c757d;
            font-size: 12px;
            text-align: center;
        }
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">Pahana Edu Bookshop</a>
        <div class="navbar-nav ms-auto">
            <span class="navbar-text me-3">Welcome, <%= session.getAttribute("username") %></span>
            <a class="nav-link" href="logout">Logout</a>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2>Books Available</h2>
<%
    String message = (String) session.getAttribute("message");
    String error = (String) session.getAttribute("error");
    if (message != null) {
%>
<p style="color: green; font-weight: bold;"><%= message %></p>
<%
        session.removeAttribute("message");
    }
    if (error != null) {
%>
<p style="color: red; font-weight: bold;"><%= error %></p>
<%
        session.removeAttribute("error");
    }
%>

<div class="mb-3">
    <a href="add-book.jsp" class="btn btn-primary">Add New Book</a>
    <a href="cart" class="btn btn-info">View Cart</a>
    <a href="dashboard" class="btn btn-secondary">Back to Dashboard</a>
</div>

<%
    List<BookDTO> books = (List<BookDTO>) request.getAttribute("books");
    if (books == null || books.isEmpty()) {
%>
<p>No books available.</p>
<%
} else {
%>
<table class="table table-striped table-bordered">
    <thead>
    <tr>
        <th>Image</th>
        <th>Book ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Add to Cart</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (BookDTO book : books) {
    %>
    <tr>
        <td>
            <% if (book.getImageData() != null && book.getImageData().length > 0) { %>
                <img src="book-image?id=<%= book.getBookId() %>" alt="<%= book.getTitle() %>" class="book-image" />
            <% } else { %>
                <div class="no-image">No Image</div>
            <% } %>
        </td>
        <td><%= book.getBookId() %></td>
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td>Rs. <%= String.format("%.2f", book.getPrice()) %></td>
        <td><%= book.getStock() %></td>
        <td>
            <form action="cart" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                <input type="number" name="quantity" value="1" min="1" max="<%= book.getStock() %>" required>
                <button type="submit" class="btn btn-success btn-sm">Add to Cart</button>
            </form>
        </td>
        <td>
            <!-- Edit button -->
            <form action="edit-book.jsp" method="get" style="display:inline;">
                <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />
                <button type="submit" class="btn btn-warning btn-sm">Edit</button>
            </form>

            <!-- Delete button -->
            <form action="books" method="post" style="display:inline;"
                  onsubmit="return confirm('Are you sure you want to delete this book?');">
                <input type="hidden" name="action" value="delete" />
                <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />
                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
