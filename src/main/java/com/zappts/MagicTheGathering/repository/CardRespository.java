package com.zappts.MagicTheGathering.repository;

import com.zappts.MagicTheGathering.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRespository extends JpaRepository<Card, Long> {
}
