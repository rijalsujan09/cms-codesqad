package com.codesqad.cms.account.repository;


import com.codesqad.cms.account.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsername(String userName);
    UserAccount findByEmail(String username);
}
