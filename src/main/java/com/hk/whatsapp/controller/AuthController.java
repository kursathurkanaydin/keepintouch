package com.hk.whatsapp.controller;

import com.hk.whatsapp.dto.CreatePrivateChatRequest;
import com.hk.whatsapp.dto.CreateUserRequest;
import com.hk.whatsapp.entity.User;
import com.hk.whatsapp.exception.UserNotFoundException;
import com.hk.whatsapp.service.ChatService;
import com.hk.whatsapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String welcome() {
        return "welcome";
    }
    @GetMapping("/register")
    public String showRegisterPage(Model model){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        model.addAttribute("createUserRequest", createUserRequest);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("createUserRequest") CreateUserRequest request
            , BindingResult result){
        if (result.hasErrors()){
            return "register";
        }
        userService.userRegister(request);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}
