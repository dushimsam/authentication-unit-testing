package com.example.authenticationunittesting.service;


import com.example.authenticationunittesting.dto.authdto.AuthRequest;
import com.example.authenticationunittesting.dto.authdto.AuthResponse;
import com.example.authenticationunittesting.dto.userdto.UserDtoGet;
import com.example.authenticationunittesting.exception.ApiRequestException;
import com.example.authenticationunittesting.model.User;
import com.example.authenticationunittesting.respository.UserRepository;
import com.example.authenticationunittesting.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User userByName = userRepository.findByUserName(usernameOrEmail);
        User userByEmail = userRepository.findByEmail(usernameOrEmail);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (userByEmail != null) {
            return new org.springframework.security.core.userdetails.User(userByEmail.getUsername(), userByEmail.getPassword(), authorities);
        } else if (userByName != null) {
            return new org.springframework.security.core.userdetails.User(userByName.getUsername(), userByName.getPassword(), authorities);
        } else {
            throw new ApiRequestException("Wrong Credentials");
        }
    }

    public User getByLogin(String login) {
        User userByName = userRepository.findByUserName(login);
        User userByEmail = userRepository.findByEmail(login);

        if (userByName != null) {
            return userByName;
        } else if (userByEmail != null) {
            return userByEmail;
        } else {
            return null;
        }
    }

    public AuthResponse handleLogin(AuthRequest authRequest) {
        User user = getByLogin(authRequest.getLogin());
        if (user == null)
            return null;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            return null;
        }
        return new AuthResponse(jwtUtil.generateToken(new UserDtoGet(user)));
    }
}
