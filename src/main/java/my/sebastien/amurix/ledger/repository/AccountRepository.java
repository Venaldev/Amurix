package my.sebastien.amurix.ledger.repository;

import my.sebastien.amurix.ledger.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountById(Long ownerId);
}
