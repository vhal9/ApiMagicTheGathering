package com.zappts.MagicTheGathering.repository;

import com.zappts.MagicTheGathering.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
