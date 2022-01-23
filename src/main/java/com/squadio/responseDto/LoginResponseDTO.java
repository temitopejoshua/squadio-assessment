package com.squadio.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String role;
    private String  userId;
    private String token;
}
