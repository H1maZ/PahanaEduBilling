<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.dto.InvoiceDTO" %>
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
    <title>Billing History - Pahana Edu</title>
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
        <div class="col-md-12">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="fas fa-history"></i> Billing History</h2>
                <a href="dashboard" class="btn btn-outline-primary">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </div>

    <!-- Statistics Cards -->
    <div class="row mb-4">
        <div class="col-md-3">
            <div class="card bg-primary text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h4 class="mb-0">
                                <%
                                    List<InvoiceDTO> invoices = (List<InvoiceDTO>) request.getAttribute("invoices");
                                    if (invoices != null) {
                                        out.print(invoices.size());
                                    } else {
                                        out.print("0");
                                    }
                                %>
                            </h4>
                            <p class="text-muted">Total Invoices</p>
                        </div>
                        <div class="align-self-center">
                            <i class="fas fa-file-invoice fa-2x"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card bg-success text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h4 class="mb-0">
                                <%
                                    if (invoices != null) {
                                        double totalRevenue = invoices.stream().mapToDouble(InvoiceDTO::getTotalAmount).sum();
                                        out.print("Rs. " + String.format("%.2f", totalRevenue));
                                    } else {
                                        out.print("Rs. 0.00");
                                    }
                                %>
                            </h4>
                            <p class="text-muted">Total Revenue</p>
                        </div>
                        <div class="align-self-center">
                            <i class="fas fa-money-bill-wave fa-2x"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card bg-info text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h4 class="mb-0">
                                <%
                                    if (invoices != null) {
                                        long totalItems = invoices.stream().mapToLong(invoice -> 
                                            invoice.getItems() != null ? invoice.getItems().size() : 0).sum();
                                        out.print(totalItems);
                                    } else {
                                        out.print("0");
                                    }
                                %>
                            </h4>
                            <p class="text-muted">Total Items Sold</p>
                        </div>
                        <div class="align-self-center">
                            <i class="fas fa-shopping-cart fa-2x"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card bg-warning text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h4 class="mb-0">
                                <%
                                    if (invoices != null && !invoices.isEmpty()) {
                                        double avgOrder = invoices.stream().mapToDouble(InvoiceDTO::getTotalAmount).average().orElse(0.0);
                                        out.print("Rs. " + String.format("%.2f", avgOrder));
                                    } else {
                                        out.print("Rs. 0.00");
                                    }
                                %>
                            </h4>
                            <p class="text-muted">Average Order Value</p>
                        </div>
                        <div class="align-self-center">
                            <i class="fas fa-chart-line fa-2x"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Invoices Table -->
    <div class="card">
        <div class="card-header">
            <h4><i class="fas fa-list"></i> Invoice List</h4>
        </div>
        <div class="card-body">
            <%
                if (invoices == null || invoices.isEmpty()) {
            %>
            <div class="text-center py-5">
                <i class="fas fa-file-invoice fa-3x text-muted mb-3"></i>
                <h4 class="text-muted">No invoices found</h4>
                <p class="text-muted">Start creating invoices to see them here.</p>
                <a href="books" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Create New Invoice
                </a>
            </div>
            <%
            } else {
            %>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead class="table-dark">
                    <tr>
                        <th>Invoice ID</th>
                        <th>Customer</th>
                        <th>Date</th>
                        <th>Items</th>
                        <th>Total Amount</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (InvoiceDTO invoice : invoices) {
                    %>
                    <tr>
                        <td>
                            <span class="badge bg-secondary"><%= invoice.getInvoiceId() %></span>
                        </td>
                        <td>
                            <div>
                                <strong><%= invoice.getCustomerName() %></strong><br>
                                <small class="text-muted">ID: <%= invoice.getCustomerId() %></small>
                            </div>
                        </td>
                        <td>
                            <div>
                                <strong><%= invoice.getInvoiceDate().toString().substring(0, 10) %></strong><br>
                                <small class="text-muted"><%= invoice.getInvoiceDate().toString().substring(11, 16) %></small>
                            </div>
                        </td>
                        <td>
                            <%
                                if (invoice.getItems() != null) {
                                    out.print(invoice.getItems().size() + " items");
                                } else {
                                    out.print("0 items");
                                }
                            %>
                        </td>
                        <td>
                            <strong class="text-primary">Rs. <%= String.format("%.2f", invoice.getTotalAmount()) %></strong>
                        </td>
                        <td>
                            <%
                                String statusClass = "";
                                String statusIcon = "";
                                switch (invoice.getPaymentStatus()) {
                                    case "PAID":
                                        statusClass = "success";
                                        statusIcon = "check-circle";
                                        break;
                                    case "PENDING":
                                        statusClass = "warning";
                                        statusIcon = "clock";
                                        break;
                                    case "CANCELLED":
                                        statusClass = "danger";
                                        statusIcon = "times-circle";
                                        break;
                                    default:
                                        statusClass = "secondary";
                                        statusIcon = "question-circle";
                                }
                            %>
                            <span class="badge bg-<%= statusClass %>">
                                <i class="fas fa-<%= statusIcon %>"></i> <%= invoice.getPaymentStatus() %>
                            </span>
                        </td>
                        <td>
                            <div class="btn-group" role="group">
                                <a href="bill?invoiceId=<%= invoice.getInvoiceId() %>" 
                                   class="btn btn-outline-primary btn-sm" title="View Bill">
                                    <i class="fas fa-eye"></i>
                                </a>
                                <button type="button" class="btn btn-outline-info btn-sm" 
                                        onclick="showInvoiceDetails('<%= invoice.getInvoiceId() %>')" title="Details">
                                    <i class="fas fa-info-circle"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>

<!-- Invoice Details Modal -->
<div class="modal fade" id="invoiceDetailsModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Invoice Details</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body" id="invoiceDetailsContent">
                <!-- Content will be loaded here -->
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function showInvoiceDetails(invoiceId) {
    // This function would typically make an AJAX call to get invoice details
    // For now, we'll just show a simple message
    document.getElementById('invoiceDetailsContent').innerHTML = 
        '<p>Loading details for invoice: ' + invoiceId + '</p>';
    
    var modal = new bootstrap.Modal(document.getElementById('invoiceDetailsModal'));
    modal.show();
}
</script>
</body>
</html>
