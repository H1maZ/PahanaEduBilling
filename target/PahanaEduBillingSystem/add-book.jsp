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
    <title>Add Book - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .image-preview {
            max-width: 200px;
            max-height: 200px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 10px;
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
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Add New Book</h3>
                </div>
                <div class="card-body">
                    <form action="books" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="add" />
                        
                        <div class="mb-3">
                            <label for="title" class="form-label">Title:</label>
                            <input type="text" class="form-control" id="title" name="title" required />
                        </div>

                        <div class="mb-3">
                            <label for="author" class="form-label">Author:</label>
                            <input type="text" class="form-control" id="author" name="author" required />
                        </div>

                        <div class="mb-3">
                            <label for="price" class="form-label">Price:</label>
                            <input type="number" class="form-control" id="price" name="price" step="0.01" min="0" required />
                        </div>

                        <div class="mb-3">
                            <label for="stock" class="form-label">Stock:</label>
                            <input type="number" class="form-control" id="stock" name="stock" min="0" required />
                        </div>

                        <div class="mb-3">
                            <label for="image" class="form-label">Book Image:</label>
                            <input type="file" class="form-control" id="image" name="image" accept="image/*" />
                            <div class="form-text">Upload an image for the book (JPEG, PNG, GIF). Max size: 5MB</div>
                            <img id="imagePreview" class="image-preview" style="display: none;" />
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Add Book</button>
                            <a href="books" class="btn btn-secondary">Back to Books</a>
                        </div>
                    </form>

                    <%-- Display error message if set --%>
                    <%
                        String error = (String) request.getAttribute("error");
                        if (error != null) {
                    %>
                    <div class="alert alert-danger mt-3" role="alert">
                        <%= error %>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Image preview functionality
    document.getElementById('image').addEventListener('change', function(event) {
        const file = event.target.files[0];
        const preview = document.getElementById('imagePreview');
        
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        } else {
            preview.style.display = 'none';
        }
    });
</script>
</body>
</html>
