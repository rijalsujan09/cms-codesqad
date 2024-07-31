package com.codesqad.cms.account.service;

import com.codesqad.cms.account.dto.UserDtoReturn;
import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.account.dto.UserDtoSave;

import java.util.List;

public interface UserService {
    public MessageDto createUser(UserDtoSave userDto);
    public UserDtoReturn getUser(Long userId);
    public MessageDto updateUser(Long userId, UserDtoSave userDto);
    public List<UserDtoReturn> getAllUser();
    public MessageDto deleteUser(Long userId);


}