package com.zappts.MagicTheGathering.service;

import com.zappts.MagicTheGathering.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listUsers();
    UserDTO findUserById(Long id) throws Exception;
    UserDTO createUser(UserDTO userDTO);

}
