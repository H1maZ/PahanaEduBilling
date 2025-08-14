<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Add Customer</title>
</head>
<body>
<h2>Add New Customer</h2>
<form action="customer" method="post"> <!-- ✅ FIXED: changed from addCustomer to customer -->
    <input type="hidden" name="action" value="add" /> <!-- ✅ important to set action -->

    <label for="accountNumber">Account Number:</label><br/>
    <input type="text" id="accountNumber" name="accountNumber" required /><br/><br/>

    <label for="name">Name:</label><br/>
    <input type="text" id="name" name="name" required /><br/><br/>

    <label for="address">Address:</label><br/>
    <input type="text" id="address" name="address" required /><br/><br/>

    <label for="phone">Phone:</label><br/>
    <input type="text" id="phone" name="phone" required /><br/><br/>

    <label for="unitsConsumed">Units Consumed:</label><br/>
    <input type="number" id="unitsConsumed" name="unitsConsumed" min="0" required /><br/><br/>

    <input type="submit" value="Add Customer" />
</form>
</body>
</html>
