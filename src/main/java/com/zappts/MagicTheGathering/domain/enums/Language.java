package com.zappts.MagicTheGathering.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {

    JAPANESE("Japanese"),
    ENGLISH("English"),
    PORTUGUESE("Portuguese");

    private final String language;
}
