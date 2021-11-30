package com.example.authenticationunittesting.controller;

import com.example.authenticationunittesting.dto.authdto.AuthRequest;
import com.example.authenticationunittesting.dto.authdto.AuthResponse;
import com.example.authenticationunittesting.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationServiceMock;

    @Test
    public void login_success() throws Exception {
        AuthRequest request = new AuthRequest("samuel", "123");
        when(authenticationServiceMock.handleLogin(request)).thenReturn(new AuthResponse("TOKEN"));

        MockHttpServletRequestBuilder makeRequest = MockMvcRequestBuilders.post("api/auth/login")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(makeRequest).andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"token\":TOKEN}"))
                .andReturn();
    }

    @Test
    public void login_failed() throws Exception {
        AuthRequest request = new AuthRequest("samuel", "123");
        when(authenticationServiceMock.handleLogin(request)).thenReturn(null);

        MockHttpServletRequestBuilder makeRequest = MockMvcRequestBuilders.post("api/auth/login")
                .accept(MediaType.APPLICATION_JSON);

//        mockMvc.perform(makeRequest).andExpect(status().())
//                .andExpect(content().)
//                .andReturn();
    }
}
