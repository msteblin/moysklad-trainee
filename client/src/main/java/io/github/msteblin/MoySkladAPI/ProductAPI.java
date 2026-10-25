package io.github.msteblin.MoySkladAPI;

import io.github.msteblin.MoySkladAPI.types.Product;
import io.github.msteblin.MoySkladAPI.types.Rows;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ProductAPI {
    @NonNull
    private final Core core;

    public List<Product> getAll() {
        Rows<Product> rows = core.restClient.get().uri("/entity/product")
                .headers(h -> h.setBearerAuth(core.msToken))
                .retrieve()
                .body(new ParameterizedTypeReference<Rows<Product>>() {});
        // TODO: convert to exception
        assert rows != null;
        log.debug("Rows: {}", rows);
        return rows.rows();
    }

    public Product get(UUID id) {
        return core.restClient.get().uri("/entity/product/" + id.toString())
                .headers(h -> h.setBearerAuth(core.msToken))
                .retrieve()
                .body(new ParameterizedTypeReference<Product>() {});
    }
}
