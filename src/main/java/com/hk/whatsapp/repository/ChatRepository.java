package com.hk.whatsapp.repository;

import com.hk.whatsapp.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
    Chat findChatById(String chatId);
}
