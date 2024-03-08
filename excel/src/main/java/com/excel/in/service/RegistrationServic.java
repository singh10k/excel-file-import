package com.excel.in.service;

import com.excel.in.dto.AuthenticationResponse;
import com.excel.in.dto.RegisterRequest;

public interface RegistrationServic {
    AuthenticationResponse registration(RegisterRequest request);
}
