package io.github.msteblin.Marketplace.customer.dto;

import java.util.UUID;

// DTO для создания покупателя
public record CustomerCreationDTO(UUID accountId, String name) {
}
