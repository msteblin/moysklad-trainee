package io.github.msteblin.Marketplace.account;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString(exclude = {"hash"})
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login", nullable = false, unique = true)
    @NonNull
    private String login;

    @Column(name = "hash", nullable = false)
    @NonNull
    private String hash;

    public Account(UUID id) {
        this.id = id;
        this.login = "";
        this.hash = "";
    }
}
