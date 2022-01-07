package com.sainnt.blogrestapi.service.impl;

import com.sainnt.blogrestapi.dto.LoginDto;
import com.sainnt.blogrestapi.dto.SignupDto;
import com.sainnt.blogrestapi.entity.Role;
import com.sainnt.blogrestapi.entity.User;
import com.sainnt.blogrestapi.exception.ResourceNotFoundException;
import com.sainnt.blogrestapi.repository.RoleRepository;
import com.sainnt.blogrestapi.repository.UserRepository;
import com.sainnt.blogrestapi.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public boolean userByUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean userByEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void signUp(SignupDto signupDto, String[] roles) {
        User user = mapToEntity(signupDto);
        user.setRoles( Arrays.stream(roles)
                .map(role->roleRepository.findByName(role).orElseThrow(()->new ResourceNotFoundException("Role","name",role)))
                .collect(Collectors.toSet()));
        userRepository.save(user);
    }

    private User mapToEntity(SignupDto signupDto){
        User user = mapper.map(signupDto, User.class);
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        return user;
    }


}
