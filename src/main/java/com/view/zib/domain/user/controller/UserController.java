package com.view.zib.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1")
@RestController
public class UserController {

    @GetMapping("/api/v1/users")
    public String getUsers() {
        return "users";
    }
}
