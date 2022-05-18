package com.zappts.MagicTheGathering.service.impl;

import com.zappts.MagicTheGathering.domain.dto.UserDTO;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import com.zappts.MagicTheGathering.domain.mapper.UserDTOMapper;
import com.zappts.MagicTheGathering.domain.mapper.UserMapper;
import com.zappts.MagicTheGathering.exception.ForbiddenException;
import com.zappts.MagicTheGathering.exception.UserNotFoundException;
import com.zappts.MagicTheGathering.exception.UsernameAlreadyExistsException;
import com.zappts.MagicTheGathering.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = getUserByUsername(username);
        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public List<UserDTO> listUsers() {
        List<UserEntity> userList = userRepository.findAll();
        return userList.stream().map(userDTOMapper::execute)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id) throws Exception {
        UserEntity user = getUserById(id);
        return userDTOMapper.execute(user);
    }

    public UserDTO createUser(UserDTO userDTO) throws UsernameAlreadyExistsException {
        UserEntity user = userMapper.execute(userDTO);
        user.setRole("USER");
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(user.getUsername());
        if (userEntityOptional.isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
        return userDTOMapper.execute(userRepository.save(user));
    }

    public UserEntity getUserById(Long id) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()){
            throw new UserNotFoundException(id);
        }
        return userOptional.get();
    }

    public UserEntity getLoggedUser() throws UsernameNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return getUserByUsername(username);

    }

    private UserEntity getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException(username);
        }
        return userOptional.get();
    }

    public void verifyIfUserHasPermission(UserEntity user) throws ForbiddenException {
        UserEntity loggedUser = getLoggedUser();
        if (!Objects.equals(loggedUser.getId(), user.getId())){
            throw new ForbiddenException();
        }
    }
}
