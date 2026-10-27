package io.github.msteblin.Marketplace.cartGood.dto;

import io.github.msteblin.Marketplace.cartGood.CartGood;
import io.github.msteblin.Marketplace.customer.Customer;
import io.github.msteblin.Marketplace.customer.dto.CustomerDTO;
import io.github.msteblin.Marketplace.seller.Seller;
import io.github.msteblin.Marketplace.seller.dto.SellerDTO;

import java.time.OffsetDateTime;
import java.util.UUID;

// DTO для получения элемента корзины с БД
public record CartGoodDTO(CustomerDTO customer, SellerDTO seller, UUID msGoodId, int quantity, OffsetDateTime addedAt) {
    public static CartGoodDTO fromCartGood(CartGood cartGood) {
        return new CartGoodDTO(
                CustomerDTO.fromCustomer(cartGood.getCustomer()),
                SellerDTO.fromSeller(cartGood.getSeller()),
                cartGood.getId().getMsGoodId(),
                cartGood.getQuantity(),
                cartGood.getAddedAt()
        );
    }
}
