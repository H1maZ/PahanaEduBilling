<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.pahana.dto.InvoiceDTO" %>
<%@ page import="com.pahana.dto.InvoiceItemDTO" %>
<%@ page import="java.util.List" %>
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
    <title>Invoice - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        @media print {
            .no-print { display: none !important; }
            .print-only { display: block !important; }
        }
        .invoice-header {
            border-bottom: 2px solid #dee2e6;
            padding-bottom: 20px;
            margin-bottom: 30px;
        }
        .total-row { font-weight: bold; }
        .invoice-table th {
            background-color: #f8f9fa;
            border-top: 2px solid #dee2e6;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <div class="no-print mb-3">
        <div class="d-flex justify-content-between align-items-center">
            <a href="billing-history" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left"></i> Back to Billing History
            </a>
            <button onclick="window.print()" class="btn btn-primary">
                <i class="fas fa-print"></i> Print Invoice
            </button>
        </div>
    </div>

    <%
        InvoiceDTO invoice = (InvoiceDTO) request.getAttribute("invoice");
        if (invoice == null) {
    %>
    <div class="alert alert-danger" role="alert">
        <h4>Invoice not found!</h4>
        <p>The requested invoice could not be found.</p>
        <a href="billing-history" class="btn btn-primary">Back to Billing History</a>
    </div>
    <%
    } else {
    %>
    <div class="card">
        <div class="card-body">
            <!-- Invoice Header -->
            <div class="invoice-header">
                <div class="row">
                    <div class="col-md-6">
                        <h2 class="text-primary mb-0">PAHANA EDU BOOKSHOP</h2>
                        <p class="text-muted mb-1">Educational Bookstore</p>
                        <p class="text-muted mb-0">123 Education Street, Colombo, Sri Lanka</p>
                        <p class="text-muted mb-0">Phone: +94 11 234 5678 | Email: info@pahana.edu</p>
                    </div>
                    <div class="col-md-6 text-end">
                        <h3 class="text-dark mb-2">INVOICE</h3>
                        <p class="mb-1"><strong>Invoice #:</strong> <%= invoice.getInvoiceId() %></p>
                        <p class="mb-1"><strong>Date:</strong> <%= invoice.getInvoiceDate().toString().substring(0, 10) %></p>
                        <p class="mb-1"><strong>Time:</strong> <%= invoice.getInvoiceDate().toString().substring(11, 16) %></p>
                        <p class="mb-0"><strong>Status:</strong> 
                            <span class="badge bg-success"><%= invoice.getPaymentStatus() %></span>
                        </p>
                    </div>
                </div>
            </div>

            <!-- Customer Information -->
            <div class="row mb-4">
                <div class="col-md-6">
                    <h5 class="text-dark mb-3">Bill To:</h5>
                    <p class="mb-1"><strong>Customer ID:</strong> <%= invoice.getCustomerId() %></p>
                    <p class="mb-1"><strong>Name:</strong> <%= invoice.getCustomerName() %></p>
                </div>
                <div class="col-md-6 text-end">
                    <h5 class="text-dark mb-3">Invoice Summary:</h5>
                    <p class="mb-1"><strong>Total Items:</strong> 
                        <%
                            if (invoice.getItems() != null) {
                                out.print(invoice.getItems().size());
                            } else {
                                out.print("0");
                            }
                        %>
                    </p>
                    <p class="mb-1"><strong>Total Amount:</strong> 
                        <span class="text-primary fw-bold fs-5">Rs. <%= String.format("%.2f", invoice.getTotalAmount()) %></span>
                    </p>
                </div>
            </div>

            <!-- Items Table -->
            <div class="table-responsive">
                <table class="table table-bordered invoice-table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Book Title</th>
                        <th>Quantity</th>
                        <th>Unit Price</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<InvoiceItemDTO> items = invoice.getItems();
                        if (items != null && !items.isEmpty()) {
                            int itemNumber = 1;
                            for (InvoiceItemDTO item : items) {
                    %>
                    <tr>
                        <td><%= itemNumber++ %></td>
                        <td><%= item.getBookTitle() %></td>
                        <td><%= item.getQuantity() %></td>
                        <td>Rs. <%= String.format("%.2f", item.getUnitPrice()) %></td>
                        <td>Rs. <%= String.format("%.2f", item.getTotalPrice()) %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    </tbody>
                    <tfoot>
                    <tr class="total-row">
                        <td colspan="4" align="right"><strong>Grand Total:</strong></td>
                        <td><strong>Rs. <%= String.format("%.2f", invoice.getTotalAmount()) %></strong></td>
                    </tr>
                    </tfoot>
                </table>
            </div>

            <!-- Footer -->
            <div class="row mt-4">
                <div class="col-md-8">
                    <div class="alert alert-info" role="alert">
                        <h6 class="alert-heading">Payment Terms:</h6>
                        <ul class="mb-0">
                            <li>Payment is due upon receipt of this invoice</li>
                            <li>Please include invoice number with your payment</li>
                            <li>For any questions, please contact us at info@pahana.edu</li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="text-center">
                        <p class="text-muted mb-2">Thank you for your business!</p>
                        <div class="border-top pt-2">
                            <small class="text-muted">Pahana Edu Bookshop</small><br>
                            <small class="text-muted">Educational Excellence Through Quality Books</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
</body>
</html>
