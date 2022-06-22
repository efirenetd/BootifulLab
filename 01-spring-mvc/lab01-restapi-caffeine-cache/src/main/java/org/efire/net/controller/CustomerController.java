package org.efire.net.controller;

import lombok.AllArgsConstructor;
import org.efire.net.dto.CustomerDto;
import org.efire.net.mapper.CustomerMapper;
import org.efire.net.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    private CustomerMapper mapper;

    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto dto) {
        var customer = mapper.dtoToCustomer(dto);
        var newCustomer = customerService.add(customer);
        return ResponseEntity.ok(mapper.toDto(newCustomer));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerDto dto) {
        var updatedCustomer = customerService.update(customerId, dto);
        return ResponseEntity.ok(mapper.toDto(updatedCustomer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> retrieveAllCustomers() {
        return ResponseEntity.ok(mapper.toDtoList(customerService.getAll()));
    }

}
