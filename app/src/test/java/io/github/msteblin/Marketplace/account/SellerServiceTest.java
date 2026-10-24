package io.github.msteblin.Marketplace.account;

import io.github.msteblin.Marketplace.seller.Seller;
import io.github.msteblin.Marketplace.seller.SellerRepository;
import io.github.msteblin.Marketplace.seller.SellerService;
import io.github.msteblin.Marketplace.seller.dto.SellerCreationDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unused")
public class SellerServiceTest {
    @Mock
    SellerRepository sellerRepository;
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    SellerService sellerService;

    @Test
    public void createSeller() {
        final String token = "token";
        final Account account = mock(Account.class);

        when(sellerRepository.save(any(Seller.class))).thenAnswer(i -> i.getArgument(0));

        sellerService.createSeller(new SellerCreationDTO(account.getId(), token));
        ArgumentCaptor<Seller> captor = ArgumentCaptor.forClass(Seller.class);
        verify(sellerRepository).save(captor.capture());

        Seller seller = captor.getValue();
        assertThat(seller).isNotNull();
        assertThat(seller.getAccount().getId()).isEqualTo(account.getId());
        assertThat(seller.getMsToken()).isEqualTo(token);
    }
}
