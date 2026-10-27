package io.github.msteblin.Marketplace.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "login", nullable = false, unique = true)
    @NonNull
    private String login;

    @Column(name = "hash", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @NonNull
    private String hash;

    public Account(UUID id) {
        this.id = id;
        this.login = "";
        this.hash = "";
    }
}
