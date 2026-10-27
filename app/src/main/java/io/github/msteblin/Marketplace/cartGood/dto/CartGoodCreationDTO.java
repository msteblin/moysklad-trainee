package io.github.msteblin.Marketplace.cartGood.dto;

import java.util.UUID;

// DTO для добавления товара в корзину
public record CartGoodCreationDTO(UUID customerId, UUID sellerId, UUID msGoodId, int quantity) {
}
