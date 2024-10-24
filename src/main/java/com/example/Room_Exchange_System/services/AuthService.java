package com.example.Room_Exchange_System.services;

import com.example.Room_Exchange_System.ExceptionHandling.DuplicateResourceException;
import com.example.Room_Exchange_System.ExceptionHandling.UserNotFoundException;
import com.example.Room_Exchange_System.domain.DTO.AuthorizationDTO;
import com.example.Room_Exchange_System.domain.Entity.AuthorizationEntity;
import com.example.Room_Exchange_System.mappers.impl.AuthorizationMapper;
import com.example.Room_Exchange_System.repositories.AuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final AuthorizationMapper authorizationMapper;
    private final PasswordEncoder passwordEncoder;
    public AuthService(AuthRepository authRepository, AuthorizationMapper authorizationMapper, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.authorizationMapper = authorizationMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthorizationDTO encodePassWord(AuthorizationDTO user) {
        return  AuthorizationDTO.builder()
                .email(user.getEmail())
                .passcode(passwordEncoder.encode(user.getPasscode()))
                .build();
    }

    public ResponseEntity<?> createUser(AuthorizationDTO user) {
        boolean checkUser = authRepository.existsById(user.getEmail());
        if (checkUser) {
            throw new DuplicateResourceException(user.getEmail());
        }
        AuthorizationDTO encodedUser = encodePassWord(user);
        AuthorizationEntity newUser = authorizationMapper.mapFrom(encodedUser);
        AuthorizationEntity savedUser = authRepository.save(newUser);
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Register");
    }

    public AuthorizationDTO findUser(String user) {
        AuthorizationEntity authEntity = authRepository.findById(user)
                .orElseThrow(() -> new UserNotFoundException(user+" does not exist"));

        return authorizationMapper.mapTo(authEntity);
    }

    public void deleteUser(String user) {
        AuthorizationDTO authorizationDTO = findUser(user);
        if (authorizationDTO != null) {
            authRepository.deleteById(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AuthorizationEntity> authEntity = authRepository.findById(email);
        if (authEntity.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }

        return User.withUsername(authEntity.get().getEmail())
                .password(authEntity.get().getPasscode()) // Using passcode directly as it is not encoded
                .roles("USER") // Assigning default role; modify as per your requirement
                .build();
    }

    public ResponseEntity<?> login(AuthorizationDTO user) {
        AuthorizationDTO savedEntity = findUser(user.getEmail());
        System.out.println(savedEntity);
        System.out.println(user);

        if (passwordEncoder.matches(user.getPasscode(), savedEntity.getPasscode())){
            return ResponseEntity.ok("Logged In Successfully");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }
}
