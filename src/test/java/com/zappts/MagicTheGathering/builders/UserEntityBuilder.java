package com.zappts.MagicTheGathering.builders;

import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import lombok.Builder;

@Builder
public class UserEntityBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "usuario1";

    @Builder.Default
    private String password = null;

    @Builder.Default
    private String role = "USER";

    public UserEntity toUserEntity() {
        return new UserEntity(id,
                name,
                password,
                role);
    }
}
