<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahana.dao.CustomerDAO" %>
<%@ page import="com.pahana.dao.DBConnection" %>
<%@ page import="com.pahana.model.Customer" %>
<%@ page import="java.sql.Connection" %>

<%
    String accountNumberParam = request.getParameter("accountNumber");
    Customer customer = null;

    // quick debug (uncomment if you want a visible debug line)
    // out.println("DEBUG accountNumberParam = " + accountNumberParam + "<br/>");

    if (accountNumberParam != null && !accountNumberParam.isEmpty()) {
        try (Connection conn = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(conn);
            // treat accountNumber as String everywhere (no parse)
            customer = customerDAO.getCustomerByAccountNumber(accountNumberParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    if (customer == null) {
%>
<p>Customer not found.</p>
<a href="customers">Back to Customer List</a>
<%
        return;
    }
%>

<html>
<head>
    <title>Edit Customer</title>
</head>
<body>
<h2>Edit Customer</h2>

<form action="customer" method="post">
    <input type="hidden" name="action" value="update" />
    <input type="hidden" name="accountNumber" value="<%= customer.getAccountNumber() %>" />

    <label>Name:</label>
    <input type="text" name="name" value="<%= customer.getName() %>" required />
    <br/><br/>

    <label>Address:</label>
    <input type="text" name="address" value="<%= customer.getAddress() %>" />
    <br/><br/>

    <label>Phone:</label>
    <input type="text" name="phone" value="<%= customer.getPhone() %>" />
    <br/><br/>

    <label>Units Consumed:</label>
    <input type="number" name="unitsConsumed" value="<%= customer.getUnitsConsumed() %>" />
    <br/><br/>

    <button type="submit">Update Customer</button>
</form>

<br/>
<a href="customers">Back to Customer List</a>
</body>
</html>
