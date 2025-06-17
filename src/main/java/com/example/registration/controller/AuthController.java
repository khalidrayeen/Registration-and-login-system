package com.example.registration.controller;


import com.example.registration.dto.UserDto;
import com.example.registration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/index"})
    public String home(){

        return"index";

    }


    @GetMapping("/login")
    public String login(){
        return"login";
    }

@GetMapping("/register")
    public String register(Model model){

        UserDto userDto = new UserDto();
        model.addAttribute("user",userDto);

        return "register";
    }



    @PostMapping("/register/save")
    public String save(@Valid @ModelAttribute ("user") UserDto userDto,
                       BindingResult bindingResult,
                       Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("user",userDto);
            return "register";
        }
        userService.saveUser(userDto);

        return "redirect:/register?success";


    }


    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<UserDto> userDtos = userService.findAllusers();
        model.addAttribute("users",userDtos);
        return "users";
    }
}
