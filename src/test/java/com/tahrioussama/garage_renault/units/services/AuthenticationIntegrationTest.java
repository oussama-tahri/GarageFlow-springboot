package com.tahrioussama.garage_renault.units.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahrioussama.garage_renault.security.dto.AuthenticationRequest;
import com.tahrioussama.garage_renault.security.dto.RegisterRequest;
import com.tahrioussama.garage_renault.security.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldReturnToken() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .firstname("Oussama")
                .lastname("TAHRI")
                .email("ouss@ot.com")
                .password("password123")
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void authenticate_ShouldReturnToken() throws Exception {
        // First register a user
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("Oussama")
                .lastname("TAHRI")
                .email("ouss@ot.com")
                .password("password123")
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        // Then try to authenticate
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email("ouss@ot.com")
                .password("password123")
                .build();

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
