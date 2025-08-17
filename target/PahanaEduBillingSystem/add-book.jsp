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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Book - Pahana Edu Bookshop</title>
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
        .form-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-bottom: 2rem;
        }
        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e9ecef;
            padding: 0.75rem 1rem;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.5rem;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 8px;
            padding: 0.75rem 2rem;
            font-weight: 600;
            transition: transform 0.3s ease;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-secondary {
            border-radius: 8px;
            padding: 0.75rem 2rem;
            font-weight: 600;
        }
        .image-upload-area {
            border: 2px dashed #dee2e6;
            border-radius: 10px;
            padding: 2rem;
            text-align: center;
            background-color: #f8f9fa;
            transition: border-color 0.3s ease, background-color 0.3s ease;
            cursor: pointer;
        }
        .image-upload-area:hover {
            border-color: #667eea;
            background-color: #f0f2ff;
        }
        .image-upload-area.dragover {
            border-color: #667eea;
            background-color: #e8ecff;
        }
        .image-preview {
            max-width: 200px;
            max-height: 200px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            margin-top: 1rem;
        }
        .alert {
            border-radius: 10px;
            border: none;
        }
        .input-group-text {
            background-color: #f8f9fa;
            border: 2px solid #e9ecef;
            border-right: none;
            border-radius: 8px 0 0 8px;
        }
        .input-group .form-control {
            border-left: none;
            border-radius: 0 8px 8px 0;
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
                <h1><i class="fas fa-plus-circle me-2"></i>Add New Book</h1>
                <p class="mb-0">Add a new book to your inventory</p>
            </div>
            <div class="col-md-4 text-end">
                <a href="books" class="btn btn-outline-light btn-lg">
                    <i class="fas fa-arrow-left me-2"></i>Back to Books
                </a>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!-- Messages -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fas fa-exclamation-circle me-2"></i><%= error %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <%
        }
    %>

    <!-- Book Form -->
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="form-card">
                <form action="books" method="post" enctype="multipart/form-data" id="bookForm">
                    <input type="hidden" name="action" value="add" />
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="title" class="form-label">
                                <i class="fas fa-book me-1"></i>Book Title
                            </label>
                            <input type="text" class="form-control" id="title" name="title" 
                                   required placeholder="Enter book title">
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="author" class="form-label">
                                <i class="fas fa-user me-1"></i>Author
                            </label>
                            <input type="text" class="form-control" id="author" name="author" 
                                   required placeholder="Enter author name">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="price" class="form-label">
                                <i class="fas fa-tag me-1"></i>Price (Rs.)
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">Rs.</span>
                                <input type="number" class="form-control" id="price" name="price" 
                                       step="0.01" min="0" required placeholder="0.00">
                            </div>
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="stock" class="form-label">
                                <i class="fas fa-boxes me-1"></i>Stock Quantity
                            </label>
                            <input type="number" class="form-control" id="stock" name="stock" 
                                   min="0" required placeholder="Enter stock quantity">
                        </div>
                    </div>

                    <div class="mb-4">
                        <label for="image" class="form-label">
                            <i class="fas fa-image me-1"></i>Book Cover Image
                        </label>
                        <div class="image-upload-area" onclick="document.getElementById('image').click();">
                            <i class="fas fa-cloud-upload-alt fa-3x text-muted mb-3"></i>
                            <h5>Click to upload image</h5>
                            <p class="text-muted mb-0">or drag and drop</p>
                            <small class="text-muted">JPEG, PNG, GIF up to 5MB</small>
                        </div>
                        <input type="file" class="form-control d-none" id="image" name="image" 
                               accept="image/*" onchange="previewImage(this)">
                        <img id="imagePreview" class="image-preview d-none" />
                    </div>

                    <div class="d-flex justify-content-between mt-4">
                        <a href="books" class="btn btn-secondary">
                            <i class="fas fa-times me-2"></i>Cancel
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>Add Book
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Image preview functionality
    function previewImage(input) {
        const file = input.files[0];
        const preview = document.getElementById('imagePreview');
        const uploadArea = document.querySelector('.image-upload-area');
        
        if (file) {
            // Validate file size (5MB)
            if (file.size > 5 * 1024 * 1024) {
                alert('File size must be less than 5MB');
                input.value = '';
                return;
            }
            
            // Validate file type
            if (!file.type.startsWith('image/')) {
                alert('Please select a valid image file');
                input.value = '';
                return;
            }
            
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.classList.remove('d-none');
                uploadArea.style.display = 'none';
            };
            reader.readAsDataURL(file);
        } else {
            preview.classList.add('d-none');
            uploadArea.style.display = 'block';
        }
    }

    // Drag and drop functionality
    const uploadArea = document.querySelector('.image-upload-area');
    const fileInput = document.getElementById('image');

    uploadArea.addEventListener('dragover', function(e) {
        e.preventDefault();
        uploadArea.classList.add('dragover');
    });

    uploadArea.addEventListener('dragleave', function(e) {
        e.preventDefault();
        uploadArea.classList.remove('dragover');
    });

    uploadArea.addEventListener('drop', function(e) {
        e.preventDefault();
        uploadArea.classList.remove('dragover');
        
        const files = e.dataTransfer.files;
        if (files.length > 0) {
            fileInput.files = files;
            previewImage(fileInput);
        }
    });

    // Form validation
    document.getElementById('bookForm').addEventListener('submit', function(e) {
        const title = document.getElementById('title').value.trim();
        const author = document.getElementById('author').value.trim();
        const price = document.getElementById('price').value;
        const stock = document.getElementById('stock').value;
        
        if (title === '' || author === '' || price === '' || stock === '') {
            e.preventDefault();
            alert('Please fill in all required fields.');
            return false;
        }
        
        if (parseFloat(price) < 0) {
            e.preventDefault();
            alert('Price cannot be negative.');
            return false;
        }
        
        if (parseInt(stock) < 0) {
            e.preventDefault();
            alert('Stock quantity cannot be negative.');
            return false;
        }
    });
</script>
</body>
</html>
