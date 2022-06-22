package org.efire.net.mapper;

import org.efire.net.dto.CustomerDto;
import org.efire.net.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CustomerMapper {
    @Mapping(source = "customerId", target = "id")
    Customer dtoToCustomer(CustomerDto dto);

    @Mapping(source = "id", target = "customerId")
    CustomerDto toDto(Customer customer);

    @Mapping(source = "id", target = "customerId")
    List<CustomerDto> toDtoList(List<Customer> customers);
}
