package com.pahana.service;

import com.pahana.dao.CustomerDAO;
import com.pahana.dto.CustomerDTO;
import com.pahana.model.Customer;
import com.pahana.dao.DBConnection;  // Assuming you have this utility to get Connection

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    // Helper method to get DAO with a DB connection
    private CustomerDAO getCustomerDAO() throws SQLException {
        Connection connection = DBConnection.getConnection();  // Your existing DB connection util
        return new CustomerDAO(connection);
    }

    // Convert Model to DTO
    private CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;
        return new CustomerDTO(
                customer.getAccountNumber(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getUnitsConsumed()
        );
    }


    // Convert DTO to Model
    private Customer toModel(CustomerDTO dto) {
        if (dto == null) return null;
        Customer customer = new Customer();
        customer.setAccountNumber(dto.getAccountNumber());
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customer.setUnitsConsumed(dto.getUnitsConsumed());
        return customer;
    }

    // Get all customers (convert list of com.pahana.model to DTO)
    public List<CustomerDTO> getAllCustomers() {
        try (Connection connection = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            List<Customer> customers = customerDAO.getAllCustomers();
            List<CustomerDTO> dtos = new ArrayList<>();
            for (Customer c : customers) {
                dtos.add(toDTO(c));
            }
            return dtos;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Get single customer by account number
    public CustomerDTO getCustomerByAccountNumber(String accountNumber) {
        try (Connection connection = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);
            return toDTO(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add a new customer
    public boolean addCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getName() == null || customerDTO.getName().isBlank()) {
            return false; // name is required
        }
        Customer customer = toModel(customerDTO);
        try (Connection connection = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            return customerDAO.addCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update customer details
    public boolean updateCustomer(CustomerDTO customerDTO) {
        Customer customer = toModel(customerDTO);
        try (Connection connection = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            return customerDAO.updateCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a customer by account number
    public boolean deleteCustomer(String accountNumber) {
        try (Connection connection = DBConnection.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            return customerDAO.deleteCustomer(accountNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
