package com.zappts.MagicTheGathering.service.impl;

import com.zappts.MagicTheGathering.domain.dto.UserDTO;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import com.zappts.MagicTheGathering.domain.mapper.UserDTOMapper;
import com.zappts.MagicTheGathering.domain.mapper.UserMapper;
import com.zappts.MagicTheGathering.exception.UserNotFoundException;
import com.zappts.MagicTheGathering.repository.UserRepository;
import com.zappts.MagicTheGathering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> listUsers() {
        List<UserEntity> userList = userRepository.findAll();
        return userList.stream().map(userDTOMapper::execute)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id) throws Exception {
        UserEntity user = getUserById(id);
        return userDTOMapper.execute(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity user = userMapper.execute(userDTO);
        user.setRole("USER");
        return userDTOMapper.execute(userRepository.save(user));
    }

    public UserEntity getUserById(Long id) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(id);
        }
        return userOptional.get();
    }

}
