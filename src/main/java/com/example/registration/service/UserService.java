package com.example.registration.service;

import com.example.registration.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    List<UserDto> findAllusers();
}
