package io.github.msteblin.Marketplace.cartGood;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartGoodId implements Serializable {

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "ms_good_id")
    private UUID msGoodId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartGoodId that)) return false;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(msGoodId, that.msGoodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, msGoodId);
    }
}
