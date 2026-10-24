package io.github.msteblin.MoySkladAPI.types;

import java.util.UUID;

// TODO: limit string sizes
public record Product(UUID id, String name, String externalCode) {
}
