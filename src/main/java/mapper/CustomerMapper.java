package mapper;

import com.pahana.model.Customer;
import com.pahana.dto.CustomerDTO;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(
                customer.getAccountNumber(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getUnitsConsumed()
        );
    }

    public static Customer toEntity(CustomerDTO dto) {
        return new Customer(
                dto.getAccountNumber(),
                dto.getName(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getUnitsConsumed()
        );
    }
}
