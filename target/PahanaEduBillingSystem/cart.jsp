<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.dto.CartItemDTO" %>
<%
    // Check if user is logged in
    if (session.getAttribute("loggedIn") == null) {
        response.sendRedirect("login");
        return;
    }
    
    // Initialize grandTotal variable
    double grandTotal = 0.0;
    List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");
    
    // Calculate grandTotal if cartItems exist
    if (cartItems != null && !cartItems.isEmpty()) {
        for (CartItemDTO item : cartItems) {
            grandTotal += item.getPrice() * item.getQuantity();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Shopping Cart - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
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
    <div class="row">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h3><i class="fas fa-shopping-cart"></i> Shopping Cart</h3>
                </div>
                <div class="card-body">
                    <%
                        if (cartItems == null || cartItems.isEmpty()) {
                    %>
                    <div class="text-center py-5">
                        <i class="fas fa-shopping-cart fa-3x text-muted mb-3"></i>
                        <h4 class="text-muted">Your cart is empty</h4>
                        <p class="text-muted">Add some books to get started!</p>
                        <a href="books" class="btn btn-primary">
                            <i class="fas fa-book"></i> Browse Books
                        </a>
                    </div>
                    <%
                    } else {
                    %>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-dark">
                            <tr>
                                <th>Book</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                for (CartItemDTO item : cartItems) {
                                    double total = item.getPrice() * item.getQuantity();
                            %>
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="ms-3">
                                            <h6 class="mb-0"><%= item.getTitle() %></h6>
                                            <small class="text-muted">Book ID: <%= item.getBookId() %></small>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="input-group" style="width: 120px;">
                                        <form action="cart" method="post" style="display: inline;">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="bookId" value="<%= item.getBookId() %>">
                                            <input type="number" name="quantity" value="<%= item.getQuantity() %>" 
                                                   min="1" max="99" class="form-control form-control-sm" 
                                                   onchange="this.form.submit()">
                                        </form>
                                    </div>
                                </td>
                                <td>Rs. <%= String.format("%.2f", item.getPrice()) %></td>
                                <td class="fw-bold">Rs. <%= String.format("%.2f", total) %></td>
                                <td>
                                    <form action="cart" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="bookId" value="<%= item.getBookId() %>">
                                        <button type="submit" class="btn btn-danger btn-sm" 
                                                onclick="return confirm('Remove this item from cart?')">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="row mt-4">
                        <div class="col-md-6">
                            <a href="books" class="btn btn-outline-primary">
                                <i class="fas fa-arrow-left"></i> Continue Shopping
                            </a>
                        </div>
                        <div class="col-md-6">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-8 text-end">
                                            <strong>Grand Total:</strong>
                                        </div>
                                        <div class="col-4">
                                            <strong class="text-primary fs-5">Rs. <%= String.format("%.2f", grandTotal) %></strong>
                                        </div>
                                    </div>
                                    <div class="mt-3">
                                        <a href="checkout" class="btn btn-success w-100">
                                            <i class="fas fa-credit-card"></i> Proceed to Checkout
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    <h5><i class="fas fa-info-circle"></i> Cart Summary</h5>
                </div>
                <div class="card-body">
                    <%
                        if (cartItems != null && !cartItems.isEmpty()) {
                    %>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Items in Cart:</span>
                        <span class="fw-bold"><%= cartItems.size() %></span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Total Quantity:</span>
                        <span class="fw-bold">
                            <%= cartItems.stream().mapToInt(CartItemDTO::getQuantity).sum() %>
                        </span>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between">
                        <span class="fw-bold">Grand Total:</span>
                        <span class="fw-bold text-primary fs-5">Rs. <%= String.format("%.2f", grandTotal) %></span>
                    </div>
                    <%
                        } else {
                    %>
                    <p class="text-muted">No items in cart</p>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
