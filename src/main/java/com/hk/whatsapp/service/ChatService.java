package com.hk.whatsapp.service;

import com.hk.whatsapp.dto.CreatePrivateChatRequest;
import com.hk.whatsapp.entity.Chat;
import com.hk.whatsapp.entity.PrivateChat;
import com.hk.whatsapp.entity.User;
import com.hk.whatsapp.exception.UserNotFoundException;
import com.hk.whatsapp.repository.ChatRepository;
import com.hk.whatsapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;


    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public void createPrivateChat(CreatePrivateChatRequest createPrivateChatRequest) {
        try {
            User user1 = userService.getCurrentUser();
            User user2 = userService.getUserByEmail(createPrivateChatRequest.getToEmail());
            if (user1.getCommunicatedMails().contains(user2.getEmail())) {
                throw new RuntimeException("User already has communicated mails");
            }
            PrivateChat privateChat = new PrivateChat();
            privateChat.setUser1(user1);
            privateChat.setUser2(user2);
            privateChat.setMessages(new ArrayList<>());
            privateChat.setParticipants(new HashSet<>());
            privateChat.setName(user2.getEmail());
            privateChat.getParticipants().add(user1);
            privateChat.getParticipants().add(user2);
            chatRepository.save(privateChat);
            user1.getChats().add(privateChat);
            user2.getChats().add(privateChat);
            user1.getCommunicatedMails().add(user2.getEmail());
            user2.getCommunicatedMails().add(user1.getEmail());
            userService.updateUser(user1);
            userService.updateUser(user2);
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Chat getChatById(String chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }

    public void updateChat(Chat chat) {
        chatRepository.save(chat);
    }

    public  boolean hasAccessToChat(Chat chat, User user) {
        return chat.getParticipants().contains(user);
    }
}
