package com.hk.whatsapp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrivateChatRequest {
    @NotBlank(message = "Recipient email is required")
    @Email(message = "Please enter a valid email address")
    private String toEmail;
}
