package com.zappts.MagicTheGathering.domain.entity;

import com.zappts.MagicTheGathering.domain.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String edition;

    @Enumerated(EnumType.STRING)
    private Language language;

    private Boolean isFoil;

    private BigDecimal price;

    private Integer numberOfCardsOfTheSameType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
