package io.github.msteblin.Marketplace.customer;

import io.github.msteblin.Marketplace.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    @NonNull
    private Account account;

    @Column(name = "name", nullable = false)
    @NonNull
    String name;

    public Customer(UUID id) {
        this.id = id;
    }
}
