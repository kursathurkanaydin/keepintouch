package com.hk.whatsapp.controller;

import com.hk.whatsapp.dto.CreateMessageRequest;
import com.hk.whatsapp.dto.CreatePrivateChatRequest;
import com.hk.whatsapp.entity.Chat;
import com.hk.whatsapp.entity.Message;
import com.hk.whatsapp.entity.User;
import com.hk.whatsapp.exception.UserNotFoundException;
import com.hk.whatsapp.service.ChatService;
import com.hk.whatsapp.service.MessageService;
import com.hk.whatsapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;

    public UserController(UserService userService, ChatService chatService, MessageService messageService) {
        this.userService = userService;
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("email", authentication.getName());
        return "home";
    }

    @GetMapping("/my-chats")
    public String myChats(Model model) {
        try {
            User currentUser = userService.getCurrentUser();
            model.addAttribute("chats", currentUser.getChats());
            model.addAttribute("createPrivateChatRequest", new CreatePrivateChatRequest());
            model.addAttribute("currentUser", currentUser);
            return "my-chats";
        }
        catch (Exception e){
            return "redirect:/login";
        }
    }
    @PostMapping("/my-chats/create")
    public String createChat(@Valid @ModelAttribute("createPrivateChatRequest") CreatePrivateChatRequest createPrivateChatRequest, BindingResult result, Model model){
        if (result.hasErrors()){
            return "redirect:/user/my-chats";
        }
        try {
            User currentUser = userService.getCurrentUser();
            chatService.createPrivateChat(createPrivateChatRequest);
            model.addAttribute("chats", currentUser.getChats());
        } catch (UserNotFoundException e) {
            return "redirect:/user/my-chats?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }

        return "redirect:/user/my-chats";
    }

    @GetMapping("/chat/{chat_id}")
    public String chat(@PathVariable String chat_id, Model model) {
        User user = userService.getCurrentUser();
        Chat chat = chatService.getChatById(chat_id);
        if (!chatService.hasAccessToChat(chat, user)) {
            return "access-denied";
        }

        model.addAttribute("chat", chat);
        model.addAttribute("currentUserEmail", userService.getCurrentUser().getEmail());
        model.addAttribute("createMessageRequest", new CreateMessageRequest());

        return "chat";
    }

    @PostMapping("/chat/{chat_id}/send")
    public String sendMessage(@PathVariable String chat_id,
                              @Valid @ModelAttribute("createMessageRequest")
                              CreateMessageRequest createMessageRequest) {
        Chat chat = chatService.getChatById(chat_id);
        Message new_message = messageService.createMessage(createMessageRequest.getText(), chat);
        chat.getMessages().add(new_message);
        chatService.updateChat(chat);
        return "redirect:/user/chat/" + chat_id;
    }
}
