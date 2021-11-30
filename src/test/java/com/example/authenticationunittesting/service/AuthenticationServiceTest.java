package com.example.authenticationunittesting.service;

import com.example.authenticationunittesting.dto.authdto.AuthRequest;
import com.example.authenticationunittesting.dto.authdto.AuthResponse;
import com.example.authenticationunittesting.dto.userdto.UserDtoGet;
import com.example.authenticationunittesting.dto.userdto.UserDtoPost;
import com.example.authenticationunittesting.model.User;
import com.example.authenticationunittesting.respository.UserRepository;
import com.example.authenticationunittesting.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {


    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void login_Success() {
        AuthRequest dto = new AuthRequest("samuel", "123");
        User user = new User(1L, "Samuel Dush", "samuel", "dushsam100@gmail.com", "123");

        when(userRepositoryMock.findByUserName(dto.getLogin())).thenReturn(user);
        when(userRepositoryMock.findByEmail(dto.getLogin())).thenReturn(null);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), dto.getPassword())
        )).thenReturn(null);

        AuthResponse authResponse = new AuthResponse("TOKEN");
        when(jwtUtil.generateToken(new UserDtoGet(user))).thenReturn(String.valueOf(authResponse));

        assertEquals(String.valueOf(new AuthResponse("TOKEN")), authenticationService.handleLogin(dto).getToken());

    }


    @Test
    public void login_Failed() {
        AuthRequest dto = new AuthRequest("samuel", "123");

        when(userRepositoryMock.findByUserName(dto.getLogin())).thenReturn(null);
        when(userRepositoryMock.findByEmail(dto.getLogin())).thenReturn(null);

        assertNull(authenticationService.handleLogin(dto));
    }
}
