package io.github.msteblin.Marketplace.cartGood.dto;

import java.util.UUID;

// DTO для добавления товара в корзину
// customerId находится в эндпоинте
public record CartGoodCreationDTO(UUID sellerId, UUID msGoodId, int quantity) {
}
