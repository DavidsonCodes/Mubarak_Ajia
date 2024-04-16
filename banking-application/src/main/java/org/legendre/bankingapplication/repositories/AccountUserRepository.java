package org.legendre.bankingapplication.repositories;

import org.legendre.bankingapplication.models.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {
    AccountUser findByUsername(String username);
}
