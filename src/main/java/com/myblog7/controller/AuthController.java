package com.myblog7.controller;

import com.myblog7.config.security.JwtTokenProvider;
import com.myblog7.entity.Role;
import com.myblog7.entity.User;
import com.myblog7.payload.LoginDto;
import com.myblog7.payload.SignUpDto;
import com.myblog7.repository.RoleRepository;
import com.myblog7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //http://localhost:8080/api/auth/signup
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser1(@RequestBody LoginDto loginDto){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.",
                HttpStatus.OK);
    }
    //http://localhost:8080/api/auth/signin
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser1(@RequestBody SignUpDto signUpDto){
        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully",
                HttpStatus.OK);
    }

// this sign in and sign up for jwt token so i write here
        @Autowired
        private JwtTokenProvider tokenProvider;
        @PostMapping("/signin1")
        public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                        loginDto){
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
// get token form tokenProvider
            String token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JWTAuthResponse(token));
        }
        @PostMapping("/signup1")
        public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
// add check for username exists in a DB
            if(userRepository.existsByUsername(signUpDto.getUsername())){
                return new ResponseEntity<>("Username is already taken!",
                        HttpStatus.BAD_REQUEST);
            }
// add check for email exists in DB
            if(userRepository.existsByEmail(signUpDto.getEmail())){
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }
// create user object
            User user = new User();
            user.setName(signUpDto.getName());
            user.setUsername(signUpDto.getUsername());
            user.setEmail(signUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            Role roles = roleRepository.findByName("ROLE_ADMIN").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
    }
