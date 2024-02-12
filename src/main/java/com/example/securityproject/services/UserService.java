package com.example.securityproject.services;

import com.example.securityproject.Entity.User;
import com.example.securityproject.config.JwtService;
import com.example.securityproject.dto.user.AuthenticateDto;
import com.example.securityproject.dto.user.RegisterDto;
import com.example.securityproject.enums.Role;
import com.example.securityproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User findUserByUsername(String username) throws EntityNotFoundException {
        User user = this.userRepository.findByUsername(username).get();
        if(user==null) throw new EntityNotFoundException("user not found !");
        return user;
    }
    public String register(RegisterDto registerDto) throws ValidationException {
        Optional<User> existingUser = this.userRepository.findByUsername(registerDto.getUsername());
        if(existingUser.isPresent()) throw new ValidationException("This username already exists !");
        User user = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();
        this.userRepository.save(user);
        String token = jwtService.generateToken(user);
        return token;
    }

    public String authenticate(AuthenticateDto authenticateDto){
        this.authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(authenticateDto.getUsername(), authenticateDto.getPassword())
        );
        User user = this.findUserByUsername(authenticateDto.getUsername());
        String token = jwtService.generateToken(user);
        return token;
    }

}
