package com.hk.whatsapp;

import com.hk.whatsapp.entity.PrivateChat;
import com.hk.whatsapp.entity.Role;
import com.hk.whatsapp.entity.User;
import com.hk.whatsapp.repository.ChatRepository;
import com.hk.whatsapp.repository.MessageRepository;
import com.hk.whatsapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class WhatsappApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatsappApplication.class, args);
    }

}
