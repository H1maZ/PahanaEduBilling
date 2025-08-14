<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.model.Customer" %>

<html>
<head><title>Customer List</title></head>
<body>
<%
  String msg = request.getParameter("message");
  String err = request.getParameter("error");
  if (msg != null) {
%>
<p style="color: green;"><%= msg %></p>
<%
  }
  if (err != null) {
%>
<p style="color: red;"><%= err %></p>
<%
  }
%>

<h2>Customer List</h2>

<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>Account Number</th>
    <th>Name</th>
    <th>Address</th>
    <th>Phone</th>
    <th>Units Consumed</th>
    <th>Actions</th>
  </tr>
  <%
    List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
    if (customerList != null && !customerList.isEmpty()) {
      for (Customer c : customerList) {
  %>
  <tr>
    <td><%= c.getAccountNumber() %></td>
    <td><%= c.getName() %></td>
    <td><%= c.getAddress() %></td>
    <td><%= c.getPhone() %></td>
    <td><%= c.getUnitsConsumed() %></td>
    <td>
      <!-- Edit button -->
      <form action="edit-customer.jsp" method="get" style="display:inline;">
        <input type="hidden" name="accountNumber" value="<%= c.getAccountNumber() %>" />
        <button type="submit">Edit</button>
      </form>


      <!-- Delete button -->
      <form action="customer" method="post" style="display:inline;"
            onsubmit="return confirm('Are you sure you want to delete this customer?');">
        <input type="hidden" name="action" value="delete" />
        <input type="hidden" name="accountNumber" value="<%= c.getAccountNumber() %>" />
        <button type="submit">Delete</button>
      </form>
    </td>
  </tr>
  <%
    }
  } else {
  %>
  <tr><td colspan="6">No customers found.</td></tr>
  <%
    }
  %>
</table>

<br>
<a href="add-customer.jsp">Add New Customer</a>

</body>
</html>
