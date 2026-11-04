package io.github.msteblin.Marketplace.account;

import io.github.msteblin.Marketplace.exception.NotFoundException;
import io.github.msteblin.Marketplace.seller.Seller;
import io.github.msteblin.Marketplace.seller.SellerRepository;
import io.github.msteblin.Marketplace.seller.SellerService;
import io.github.msteblin.Marketplace.seller.dto.SellerCreationDTO;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SellerServiceMetricsTest {
    @Mock
    SellerRepository sellerRepository;
    @Mock
    AccountRepository accountRepository;

    SellerService sellerService;
    SimpleMeterRegistry meterRegistry;

    @BeforeEach
    void setup() {
        meterRegistry = new SimpleMeterRegistry();
        SellerService target = new SellerService(sellerRepository, accountRepository);
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(new CountedAspect(meterRegistry));
        sellerService = factory.getProxy();
    }

    @Test
    void incrementSuccessCounter() {
        final String token = "token";
        final Account account = mock(Account.class);

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(sellerRepository.save(any(Seller.class))).thenAnswer(i -> i.getArgument(0));

        sellerService.createSeller(new SellerCreationDTO(account.getId(), token));

        Search search = meterRegistry.find("seller.create.count");
        assertThat(getCount(search.tag("result", "success"))).isEqualTo(1);
        assertThat(getCount(search.tag("result", "failure"))).isEqualTo(0);
    }

    @Test
    void incrementFailureCounter() {
        final String token = "token";
        final Account account = mock(Account.class);

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(sellerRepository.save(any(Seller.class))).thenThrow(new RuntimeException(""));

        assertThatThrownBy(() -> sellerService.createSeller(new SellerCreationDTO(account.getId(), token)))
                .isInstanceOf(RuntimeException.class);

        Search search = meterRegistry.find("seller.create.count");
        assertThat(getCount(search.tag("result", "failure"))).isEqualTo(1);
        assertThat(getCount(search.tag("result", "success"))).isEqualTo(0);
    }

    @Test
    void incrementNotFoundCounter() {
        final String token = "token";
        final Account account = mock(Account.class);

        when(accountRepository.findById(account.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sellerService.createSeller(new SellerCreationDTO(account.getId(), token)))
                .isInstanceOf(NotFoundException.class);

        Search search = meterRegistry.find("seller.create.count").tag("result", "failure");
        assertThat(getCount(search.tag("exception", NotFoundException.class.getSimpleName()))).isEqualTo(1);
        assertThat(getCount(search.tag("exception", "none"))).isEqualTo(0);
    }

    double getCount(Search search) {
        Counter counter = search.counter();
        return counter != null ? counter.count() : 0;
    }
}
