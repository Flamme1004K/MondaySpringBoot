package com.themoim.user.repository;

import com.themoim.user.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserIdEquals(String userId);
}
