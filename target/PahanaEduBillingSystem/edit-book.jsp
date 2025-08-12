<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahana.dao.BookDAO" %>
<%@ page import="com.pahana.dao.DBConnection" %>
<%@ page import="com.pahana.dto.BookDTO" %>
<%@ page import="java.sql.Connection" %>
<%
    // Check if user is logged in
    if (session.getAttribute("loggedIn") == null) {
        response.sendRedirect("login");
        return;
    }
%>

<%
    String bookIdParam = request.getParameter("bookId");
    BookDTO book = null;

    if (bookIdParam != null) {
        try {
            BookDAO bookDAO = new BookDAO();
            book = bookDAO.getBookById(Integer.parseInt(bookIdParam));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    if (book == null) {
%>
<!DOCTYPE html>
<html>
<head>
    <title>Book Not Found - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="alert alert-danger" role="alert">
            <h4>Book not found.</h4>
            <a href="books" class="btn btn-primary">Back to Book List</a>
        </div>
    </div>
</body>
</html>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Book - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .image-preview {
            max-width: 200px;
            max-height: 200px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 10px;
        }
        .current-image {
            max-width: 150px;
            max-height: 150px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 10px;
        }
        .no-image {
            width: 150px;
            height: 150px;
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
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Edit Book</h3>
                </div>
                <div class="card-body">
                    <form action="books" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="update" />
                        <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />

                        <div class="mb-3">
                            <label for="title" class="form-label">Title:</label>
                            <input type="text" class="form-control" id="title" name="title" value="<%= book.getTitle() %>" required />
                        </div>

                        <div class="mb-3">
                            <label for="author" class="form-label">Author:</label>
                            <input type="text" class="form-control" id="author" name="author" value="<%= book.getAuthor() %>" required />
                        </div>

                        <div class="mb-3">
                            <label for="price" class="form-label">Price:</label>
                            <input type="number" class="form-control" id="price" name="price" step="0.01" value="<%= book.getPrice() %>" required />
                        </div>

                        <div class="mb-3">
                            <label for="stock" class="form-label">Stock:</label>
                            <input type="number" class="form-control" id="stock" name="stock" value="<%= book.getStock() %>" required />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Current Image:</label>
                            <div>
                                <% if (book.getImageData() != null && book.getImageData().length > 0) { %>
                                    <img src="book-image?id=<%= book.getBookId() %>" alt="<%= book.getTitle() %>" class="current-image" />
                                <% } else { %>
                                    <div class="no-image">No Image</div>
                                <% } %>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="image" class="form-label">Update Image (optional):</label>
                            <input type="file" class="form-control" id="image" name="image" accept="image/*" />
                            <div class="form-text">Upload a new image to replace the current one. Leave empty to keep current image.</div>
                            <img id="imagePreview" class="image-preview" style="display: none;" />
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Update Book</button>
                            <a href="books" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
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
