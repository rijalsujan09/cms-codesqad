package com.codesqad.cms.account.service.impl;

import com.codesqad.cms.account.dto.UserDtoSave;
import com.codesqad.cms.account.dto.UserDtoReturn;
import com.codesqad.cms.account.entity.UserAccount;
import com.codesqad.cms.account.repository.AuthorityRepository;
import com.codesqad.cms.account.repository.UserRepository;
import com.codesqad.cms.account.service.UserService;
import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MessageDto createUser(UserDtoSave userDto) {
        UserAccount user = modelMapper.map(userDto, UserAccount.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        this.userRepository.save(user);
        return new MessageDto("User created successfully");
    }

    @Override
    public MessageDto updateUser(Long userId, UserDtoSave userDto) {
        UserAccount user = this.userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %s not found!", userId)));
        user.setFirstName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        this.userRepository.save(user);
        return new MessageDto("User updated successfully");
    }


    @Override
    public UserDtoReturn getUser(Long userId) {
        UserAccount user = this.userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %s not found!", userId)));
        return modelMapper.map(user, UserDtoReturn.class);
    }


    @Override
    public List<UserDtoReturn> getAllUser() {
        return this.userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDtoReturn.class)).collect(Collectors.toList());
    }

    @Override
    public MessageDto deleteUser(Long userId) {
        if (!this.userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %s not found!", userId));
        }
        this.userRepository.deleteById(userId);
        return new MessageDto("User deleted successfully");
    }
}
