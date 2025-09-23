package my.sebastien.amurix.ledger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.sebastien.amurix.ledger.dto.AccountCreateRequest;
import my.sebastien.amurix.ledger.dto.AccountResponse;
import my.sebastien.amurix.ledger.entity.Account;
import my.sebastien.amurix.ledger.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request) {
        Account account = Account.builder()
                .ownerId(request.ownerId())
                .currency(request.currency())
                .build();

        Account savedAcc = accountRepository.save(account);
        log.info("Created account id={} ownerId={} currency={}", savedAcc.getId(), savedAcc.getOwnerId(), savedAcc.getCurrency());
        return toResponse(savedAcc);
    }

    private AccountResponse toResponse(Account acc) {
        return AccountResponse.builder()
                .id(acc.getId())
                .ownerId(acc.getOwnerId())
                .currency(acc.getCurrency())
                .status(acc.getStatus())
                .createdAt(acc.getCreatedAt())
                .updatedAt(acc.getUpdatedAt())
                .build();
    }
}
