package com.excel.in.service;

import com.excel.in.dto.AuthenticationRequest;
import com.excel.in.dto.AuthenticationResponse;

public interface LoginService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
