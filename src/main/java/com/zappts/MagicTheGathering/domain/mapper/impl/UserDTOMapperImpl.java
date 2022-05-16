package com.zappts.MagicTheGathering.domain.mapper.impl;

import com.zappts.MagicTheGathering.domain.dto.UserDTO;
import com.zappts.MagicTheGathering.domain.mapper.UserDTOMapper;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapperImpl implements UserDTOMapper {
    @Override
    public UserDTO execute(UserEntity user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
