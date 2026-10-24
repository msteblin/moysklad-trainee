package io.github.msteblin.MoySkladAPI;

import io.github.msteblin.MoySkladAPI.types.Product;
import io.github.msteblin.MoySkladAPI.types.Rows;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductAPI {
    @NonNull
    private final Core core;

    public List<Product> getAll(String token) {
        Rows<Product> rows = core.restClient.get().uri("/entity/product")
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .body(new ParameterizedTypeReference<Rows<Product>>() {});
        assert rows != null;
        return rows.rows();
    }

    public Product get(String token, UUID id) {
        return core.restClient.get().uri("/entity/product/" + id.toString())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .body(new ParameterizedTypeReference<Product>() {});
    }
}
