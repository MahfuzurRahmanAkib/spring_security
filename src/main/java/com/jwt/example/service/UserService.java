package com.jwt.example.service;

import com.jwt.example.dto.ResponseDto.JwtResponse;
import com.jwt.example.dto.requestDto.JwtRequest;
import com.jwt.example.dto.requestDto.UserRequest;
import com.jwt.example.entity.User;
import com.jwt.example.repository.UserRepository;
import com.jwt.example.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtHelper jwtHelper) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }

    /**
     * Find All The Users
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Create User
     */
    public User create(UserRequest userRequest) throws Exception {
        User user = new User();
        convertToSaveEntity(user, userRequest);
        User saveUser;
        try {
            saveUser = userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(" Data Not Saved !!");
        }
        return saveUser;
    }

    /**
     * Login Using Email and Password
     */
    public JwtResponse login(JwtRequest request) {
        doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername())
                .build();
        return response;
    }

    /**
     * Convert Method For Save
     */
    private User convertToSaveEntity(User user, UserRequest userRequest) {
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setAbout(userRequest.getAbout());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return user;
    }

    /**
     * Authenticate If User Is Valid Or Invalid
     */
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    /**
     * Exception Handle
     */
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
