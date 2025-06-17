package com.example.registration.service.impl;

import com.example.registration.dto.UserDto;
import com.example.registration.entity.Role;
import com.example.registration.entity.User;
import com.example.registration.repository.Rolerepository;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Array;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    Rolerepository rolerepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,Rolerepository rolerepository ,PasswordEncoder  passwordEncoder) {
        this.userRepository = userRepository;
        this.rolerepository = rolerepository;
        this.passwordEncoder =passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {

        User user = new User();
        user.setName(userDto.getFirstName()+ " "+userDto.getLastName());
                user.setEmail(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = rolerepository.findByName("ROLE_ADMIN");
        if(role==null){
            role= checkRoleExist();
        }
       user.setRoles(Arrays.asList(role));

        userRepository.save(user);

    }

    @Override
    public List<UserDto> findAllusers() {

        List<User> users =userRepository.findAll();
        return users.stream().map((user)->MaptoUserDto(user)).collect(Collectors.toUnmodifiableList());

    }

    private UserDto MaptoUserDto(User user){

        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());

        return userDto;


    }

    private Role checkRoleExist(){
        Role role = new  Role();
        role.setName("ROLE_ADMIN");
        return rolerepository.save(role);
    }
}
