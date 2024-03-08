package com.excel.in.service.imp;

import com.excel.in.dto.AuthenticationResponse;
import com.excel.in.dto.RegisterRequest;
import com.excel.in.model.User;
import com.excel.in.repository.UserRepository;
import com.excel.in.service.RegistrationServic;
import com.excel.in.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImp implements RegistrationServic {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse registration(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
       userRepository.save(user);
        var token = jwtUtils.generateToken(user);

        return AuthenticationResponse.builder()
                .access_token(token)
                .build();
    }

}
