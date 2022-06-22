package org.efire.net.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.efire.net.dto.CustomerDto;
import org.efire.net.mapper.CustomerMapper;
import org.efire.net.model.Customer;
import org.efire.net.repository.CustomerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    @Cacheable(cacheNames = "customerCache")
    public List<Customer> getAll() {
        waitSomeTime();
        return customerRepository.findAll();
    }

    @CacheEvict(cacheNames = "customerCache", allEntries = true)
    public Customer add(Customer customer) {
        return customerRepository.save(customer);
    }

    //Simulate delay process
    private void waitSomeTime() {
        log.warn("Long Wait Begin");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        log.warn("Long Wait End");
    }

    @CacheEvict(cacheNames = "customerCache", allEntries = true)
    public Customer update(Long customerId, CustomerDto dto) {
        waitSomeTime();
        if (customerRepository.existsById(customerId)) {
            var updatedCustomer = mapper.dtoToCustomer(dto);
            updatedCustomer.setId(customerId);
            return customerRepository.save(updatedCustomer);
        }
        throw new EntityNotFoundException("No Customer found.");
    }
}
