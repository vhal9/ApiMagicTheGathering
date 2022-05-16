package com.zappts.MagicTheGathering.domain.mapper;

import com.zappts.MagicTheGathering.domain.dto.UserDTO;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    UserEntity execute(UserDTO userDTO);
}
