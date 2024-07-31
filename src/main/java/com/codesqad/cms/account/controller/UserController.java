package com.codesqad.cms.account.controller;

import com.codesqad.cms.account.dto.UserDtoReturn;
import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.account.dto.UserDtoSave;
import com.codesqad.cms.account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDto> createUser(@Valid @RequestBody UserDtoSave userDto) {
        MessageDto message = this.userService.createUser(userDto);
        return new ResponseEntity<MessageDto>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<MessageDto> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserDtoSave userDto) {
        MessageDto message = this.userService.updateUser(userId, userDto);
        return new ResponseEntity<MessageDto>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        MessageDto message = this.userService.deleteUser(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDtoReturn> getUser(@PathVariable("userId") Long userId) {
        UserDtoReturn userDto = this.userService.getUser(userId);
        return new ResponseEntity<UserDtoReturn>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDtoReturn>> getAllUser() {
       String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("\nuserName: " + userName+"\n");
        List<UserDtoReturn> allUsers = this.userService.getAllUser();
        return new ResponseEntity<List<UserDtoReturn>>(allUsers, HttpStatus.OK);
    }

}
