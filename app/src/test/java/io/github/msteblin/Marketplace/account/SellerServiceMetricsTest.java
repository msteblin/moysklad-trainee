package io.github.msteblin.Marketplace.account;

import io.github.msteblin.Marketplace.seller.Seller;
import io.github.msteblin.Marketplace.seller.SellerRepository;
import io.github.msteblin.Marketplace.seller.SellerService;
import io.github.msteblin.Marketplace.seller.dto.SellerCreationDTO;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SellerServiceMetricsTest {
    @Mock
    SellerRepository sellerRepository;

    SellerService sellerService;
    SimpleMeterRegistry meterRegistry;

    @BeforeEach
    void setup() {
        meterRegistry = new SimpleMeterRegistry();
        SellerService target = new SellerService(sellerRepository);
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(new CountedAspect(meterRegistry));
        sellerService = factory.getProxy();
    }

    @Test
    void incrementSuccessCounter() {
        final String token = "token";
        final Account account = mock(Account.class);

        when(sellerRepository.save(any(Seller.class))).thenAnswer(i -> i.getArgument(0));

        sellerService.createSeller(new SellerCreationDTO(account.getId(), token));

        assertThat(getCount(meterRegistry, "success")).isEqualTo(1);
        assertThat(getCount(meterRegistry, "failure")).isEqualTo(0);
    }

    @Test
    void incrementFailureCounter() {
        final String token = "token";
        final Account account = mock(Account.class);

        when(sellerRepository.save(any(Seller.class))).thenThrow(new RuntimeException("Test runtime exception"));

        assertThatThrownBy(() -> sellerService.createSeller(new SellerCreationDTO(account.getId(), token)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Test runtime exception");

        assertThat(getCount(meterRegistry, "failure")).isEqualTo(1);
        assertThat(getCount(meterRegistry, "success")).isEqualTo(0);
    }

    double getCount(SimpleMeterRegistry registry, String type) {
        Counter counter = registry.find("seller.create.count").tag("result", type).counter();
        return counter != null ? counter.count() : 0;
    }
}
