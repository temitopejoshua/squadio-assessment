package com.squadio.services;

import com.squadio.requestDTO.LoginRequestDTO;
import com.squadio.responseDto.LoginResponseDTO;
import com.squadio.security.AuthenticatedUser;

public interface LoginService {
    LoginResponseDTO processLogin(LoginRequestDTO loginRequestDTO);
    void processLogout(AuthenticatedUser currentUser);
}
