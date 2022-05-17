package com.zappts.MagicTheGathering.repository;

import com.zappts.MagicTheGathering.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface CardRespository extends JpaRepository<Card, Long> {
    @Query("select c from Card c where c.id in (:ids) and c.user.id = :userId")

    List<Card> findAllByIdsAndByIdUser(@PathParam("ids") List<Long> ids, @PathParam("userId") Long userId);
}
