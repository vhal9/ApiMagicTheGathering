package com.zappts.MagicTheGathering.repository;

import com.zappts.MagicTheGathering.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRespository extends JpaRepository<Card, Long> {
}
