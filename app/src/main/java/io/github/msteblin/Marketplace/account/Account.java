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

    @NonNull
    @Column(unique = true)
    private String login;
    @NonNull
    private String hash;

    public Account(UUID id) {
        this.id = id;
        this.login = "";
        this.hash = "";
    }
}
