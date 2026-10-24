package io.github.msteblin.Marketplace.account;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
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
