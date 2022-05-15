package com.zappts.MagicTheGathering.repository;

import com.zappts.MagicTheGathering.domain.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
}
