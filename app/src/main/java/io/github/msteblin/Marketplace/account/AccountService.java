package io.github.msteblin.Marketplace.account;

import io.github.msteblin.Marketplace.account.dto.AccountCreationDTO;
import io.github.msteblin.Marketplace.account.dto.AccountDTO;
import io.github.msteblin.Marketplace.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class AccountService {
    @Autowired
    @SuppressWarnings("unused")
    private AccountRepository accountRepository;

    @Autowired
    @SuppressWarnings("unused")
    private PasswordEncoder passwordEncoder;

    @Transactional
    public AccountDTO createAccount(AccountCreationDTO accountCreationDTO) {
        Account account = new Account(accountCreationDTO.login(), passwordEncoder.encode(accountCreationDTO.password()));
        Account savedAccount = accountRepository.save(account);
        log.debug("Account: {}, saved account: {}", account, savedAccount);
        log.info("Created account, id={}", savedAccount.getId());
        return AccountDTO.fromAccount(savedAccount);
    }

    @Transactional
    public AccountDTO getAccount(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("account id=" + id));
        log.debug("Account: {}", account);
        log.info("Returning account, id={}", account.getId());
        return AccountDTO.fromAccount(account);
    }

    @Transactional
    public void deleteAccount(UUID id) {
        log.info("Deleting account, id={}", id);
        accountRepository.deleteById(id);
    }
}
