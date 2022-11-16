package com.nhnacademy.controller;

import com.nhnacademy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "view/login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "password") String password,
            HttpServletRequest request) {

        if (userRepository.matches(id, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", userRepository.getUser(id));
        }

        return "view/index";
    }
}
