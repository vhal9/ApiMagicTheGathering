package com.zappts.MagicTheGathering.domain.mapper;

import com.zappts.MagicTheGathering.domain.dto.UserDTO;
import com.zappts.MagicTheGathering.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDTOMapper {
    UserDTO execute(User user);
}
