package io.github.msteblin.Marketplace.customer;

import io.github.msteblin.Marketplace.customer.dto.CustomerCreationDTO;
import io.github.msteblin.Marketplace.customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@SuppressWarnings("unused")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createSeller(@RequestBody CustomerCreationDTO customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") UUID id) {
        return customerService.getCustomer(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteCustomer(id);
    }
}
