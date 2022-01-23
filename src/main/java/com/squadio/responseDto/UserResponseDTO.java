package com.squadio.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private String  userId;
    private String name;
    private String role;
    private String username;

}
