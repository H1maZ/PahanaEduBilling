<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.dto.CartItemDTO" %>
<%@ page import="com.pahana.dto.CustomerDTO" %>
<%
    // Check if user is logged in
    if (session.getAttribute("loggedIn") == null) {
        response.sendRedirect("login");
        return;
    }
    
    // Initialize variables
    List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cart");
    double total = 0.0;
    
    // Calculate total if cartItems exist
    if (cartItems != null && !cartItems.isEmpty()) {
        for (CartItemDTO item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Checkout - Pahana Edu</title>
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
                    <h3><i class="fas fa-credit-card"></i> Checkout</h3>
                </div>
                <div class="card-body">
                    <%
                        if (cartItems == null || cartItems.isEmpty()) {
                    %>
                    <div class="alert alert-warning" role="alert">
                        <h4>Your cart is empty!</h4>
                        <p>Please add some books to your cart before proceeding to checkout.</p>
                        <a href="books" class="btn btn-primary">Browse Books</a>
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
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                for (CartItemDTO item : cartItems) {
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
                                <td><%= item.getQuantity() %></td>
                                <td>Rs. <%= String.format("%.2f", item.getPrice()) %></td>
                                <td><span>Rs. <%= String.format("%.2f", item.getPrice() * item.getQuantity()) %></span></td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="row mt-4">
                        <div class="col-md-6">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h5><i class="fas fa-calculator"></i> Order Summary</h5>
                                    <div class="d-flex justify-content-between mb-2">
                                        <span>Total Amount:</span>
                                        <span class="fw-bold text-primary fs-5">Rs. <%= String.format("%.2f", total) %></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5><i class="fas fa-user"></i> Customer Information</h5>
                                    <form action="checkout" method="post">
                                        <div class="mb-3">
                                            <label for="customerId" class="form-label">Select Customer:</label>
                                            <select name="customerId" id="customerId" class="form-select" required>
                                                <option value="">-- Select a customer --</option>
                                                <%
                                                    List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customers");
                                                    if (customers != null) {
                                                        for (CustomerDTO customer : customers) {
                                                %>
                                                <option value="<%= customer.getAccountNumber() %>">
                                                    <%= customer.getName() %> (<%= customer.getAccountNumber() %>)
                                                </option>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Payment Method:</label>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="paymentMethod" id="cash" value="cash" checked>
                                                <label class="form-check-label" for="cash">
                                                    <i class="fas fa-money-bill-wave"></i> Cash
                                                </label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="paymentMethod" id="card" value="card">
                                                <label class="form-check-label" for="card">
                                                    <i class="fas fa-credit-card"></i> Debit/Credit Card
                                                </label>
                                            </div>
                                        </div>
                                        <button type="submit" class="btn btn-success w-100">
                                            <i class="fas fa-check"></i> Generate Invoice
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
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    <h5><i class="fas fa-info-circle"></i> Order Details</h5>
                </div>
                <div class="card-body">
                    <%
                        if (cartItems != null && !cartItems.isEmpty()) {
                    %>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Items:</span>
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
                        <span class="fw-bold">Total Amount:</span>
                        <span class="fw-bold text-primary fs-5">Rs. <%= String.format("%.2f", total) %></span>
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
            
            <div class="card mt-3">
                <div class="card-header">
                    <h5><i class="fas fa-arrow-left"></i> Quick Actions</h5>
                </div>
                <div class="card-body">
                    <a href="cart" class="btn btn-outline-secondary w-100 mb-2">
                        <i class="fas fa-edit"></i> Edit Cart
                    </a>
                    <a href="books" class="btn btn-outline-primary w-100 mb-2">
                        <i class="fas fa-book"></i> Continue Shopping
                    </a>
                    <a href="add-customer.jsp" class="btn btn-outline-info w-100">
                        <i class="fas fa-user-plus"></i> Add New Customer
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
