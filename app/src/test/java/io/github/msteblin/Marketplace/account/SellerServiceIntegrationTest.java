package io.github.msteblin.Marketplace.account;

import io.github.msteblin.Marketplace.seller.Seller;
import io.github.msteblin.Marketplace.seller.SellerRepository;
import io.github.msteblin.Marketplace.seller.SellerService;
import io.github.msteblin.Marketplace.seller.dto.SellerCreationDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Testcontainers
@SuppressWarnings("unused")
public class SellerServiceIntegrationTest {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SellerService sellerService;

    @Container
    @ServiceConnection
    // TODO: decide if postgres:18-alpine is more appropriate
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Test
    public void createSeller() {
        final String token = "token";
        Account account = new Account();
        account = accountRepository.saveAndFlush(account);

        sellerService.createSeller(new SellerCreationDTO(account.getId(), token));

        List<Seller> sellers = sellerRepository.findAll();
        assertThat(sellers).hasSize(1);

        Seller seller = sellers.getFirst();
        assertThat(seller.getId()).isNotNull();
        assertThat(seller.getAccount().getId()).isEqualTo(account.getId());
        assertThat(seller.getMsToken()).isEqualTo(token);
    }
}
