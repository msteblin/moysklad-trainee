package io.github.msteblin.Marketplace.customer;

import io.github.msteblin.Marketplace.account.Account;
import io.github.msteblin.Marketplace.customer.dto.CustomerCreationDTO;
import io.github.msteblin.Marketplace.customer.dto.CustomerDTO;
import io.github.msteblin.Marketplace.exception.NotFoundException;
import io.github.msteblin.Marketplace.seller.Seller;
import io.github.msteblin.Marketplace.seller.dto.SellerCreationDTO;
import io.github.msteblin.Marketplace.seller.dto.SellerDTO;
import io.github.msteblin.MoySkladAPI.types.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    @SuppressWarnings("unused")
    private CustomerRepository customerRepository;

    // TODO: create an account automatically
    @Transactional
    public CustomerDTO createCustomer(CustomerCreationDTO customerCreationDTO) {
        // TODO: add token encryption
        Customer customer = new Customer(new Account(customerCreationDTO.accountId()), customerCreationDTO.name());
        Customer savedCustomer = customerRepository.save(customer);
        log.debug("Seller: {}, saved seller: {}", customer, savedCustomer);
        log.info("Created seller, id={}", savedCustomer.getId());
        return CustomerDTO.fromCustomer(savedCustomer);
    }

    @Transactional
    public CustomerDTO getCustomer(UUID id) {
        // TODO: commonize
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer id=" + id));
        // TODO: add request id
        log.debug("Seller: {}", customer);
        log.info("Returning customer, id={}", customer.getId());
        return CustomerDTO.fromCustomer(customer);
    }

    @Transactional
    public void deleteCustomer(UUID id) {
        log.info("Deleting customer, id={}", id);
        customerRepository.deleteById(id);
    }
}
