package com.hk.whatsapp.service;

import com.hk.whatsapp.dto.CreateMessageRequest;
import com.hk.whatsapp.entity.Chat;
import com.hk.whatsapp.entity.Message;
import com.hk.whatsapp.entity.User;
import com.hk.whatsapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Message createMessage(String text, Chat chat) {
        Message message = new Message();
        message.setSenderMail(userService.getCurrentUser().getEmail());
        message.setChatId(chat.getId());
        message.setText(text);
        return saveMessage(message);
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

}
