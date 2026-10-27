package io.github.msteblin.Marketplace.cartGood;

import io.github.msteblin.Marketplace.customer.Customer;
import io.github.msteblin.Marketplace.seller.Seller;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cart_goods")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class CartGood {
    @EmbeddedId
    private CartGoodId id;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Customer customer;

    // TODO: cascade deletion?
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Seller seller;

    // TODO: decide if the check constraint belongs here
    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    @Column(name = "added_at", nullable = false)
    @NonNull
    private OffsetDateTime addedAt = OffsetDateTime.now();

    public CartGood(Customer customer, UUID msGoodId, Seller seller, int quantity) {
        this.customer = customer;
        this.seller = seller;
        this.quantity = quantity;
        this.id = new CartGoodId(customer.getId(), msGoodId);
        this.addedAt = OffsetDateTime.now();
    }
}
