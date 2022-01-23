package com.squadio.controller;

import com.squadio.requestDTO.LoginRequestDTO;
import com.squadio.responseDto.ApiResponse;
import com.squadio.responseDto.LoginResponseDTO;
import com.squadio.security.AuthenticatedUser;
import com.squadio.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> processLogin(@RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = loginService.processLogin(loginRequestDTO);
        ApiResponse<LoginResponseDTO> response = new ApiResponse<>("Processed Successfully", loginResponseDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> processLogout(@AuthenticationPrincipal AuthenticatedUser authenticatedUser){
        loginService.processLogout(authenticatedUser);
        ApiResponse<Object> response = new ApiResponse<>("Logout Successfully");
        return ResponseEntity.ok(response);
    }

}
