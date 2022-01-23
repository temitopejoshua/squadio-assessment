package com.squadio.entities;

import com.squadio.constants.RoleTypeConstant;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends AbstractBaseEntity<Long> {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleTypeConstant role;

    private String name;

    private String username;

    private String password;

    private String userId;

    private LocalDateTime lastLoginDate;

    private boolean isLoggedout;
}
