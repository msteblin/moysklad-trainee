package io.github.msteblin.Marketplace.customer.dto;

import io.github.msteblin.Marketplace.account.Account;
import io.github.msteblin.Marketplace.account.dto.AccountDTO;
import io.github.msteblin.Marketplace.customer.Customer;

import java.util.UUID;

// DTO для получения покупателя с БД
public record CustomerDTO(UUID id, AccountDTO account, String name) {
    public static CustomerDTO fromCustomer(Customer customer) {
        Account customersAccount = customer.getAccount();
        return new CustomerDTO(customer.getId(), AccountDTO.fromAccount(customersAccount), customer.getName());
    }
}
