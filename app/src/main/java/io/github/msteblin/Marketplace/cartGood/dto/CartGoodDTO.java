package io.github.msteblin.Marketplace.cartGood.dto;

import io.github.msteblin.Marketplace.cartGood.CartGood;
import io.github.msteblin.Marketplace.customer.Customer;
import io.github.msteblin.Marketplace.seller.Seller;

import java.time.OffsetDateTime;
import java.util.UUID;

// DTO для получения элемента корзины с БД
public record CartGoodDTO(Customer customer, Seller seller, UUID msGoodId, int quantity, OffsetDateTime addedAt) {
    public static CartGoodDTO fromCartGood(CartGood cartGood) {
        return new CartGoodDTO(cartGood.getCustomer(), cartGood.getSeller(), cartGood.getId().getMsGoodId(), cartGood.getQuantity(), cartGood.getAddedAt());
    }
}
