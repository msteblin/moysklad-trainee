package io.github.msteblin.Marketplace.seller;

import io.github.msteblin.Marketplace.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@ToString(exclude = {"account", "msToken"})
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NonNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private final Account account;
    @NonNull
    @Column(unique = true, name = "ms_token")
    private String msToken;
}
