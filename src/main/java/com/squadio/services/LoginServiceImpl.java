package com.squadio.services;

import com.squadio.dao.UserEntityDao;
import com.squadio.entities.UserEntity;
import com.squadio.exception.BadRequestException;
import com.squadio.exception.DuplicateLoginException;
import com.squadio.requestDTO.LoginRequestDTO;
import com.squadio.responseDto.LoginResponseDTO;
import com.squadio.security.AuthenticatedUser;
import com.squadio.security.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final UserEntityDao userEntityDao;
    private final PasswordEncodingService passwordEncodingService;
    private final JWTService jwtService;
    @Override
    public LoginResponseDTO processLogin(LoginRequestDTO loginRequestDTO) {
        UserEntity userEntity = userEntityDao.findByUsername(loginRequestDTO.getUsername()).orElseThrow(() -> new BadRequestException("Invalid username or password"));
        String hashedPassword = userEntity.getPassword();
        if(!passwordEncodingService.matchedEncodedPassword(loginRequestDTO.getPassword(),hashedPassword)){
            throw new BadRequestException("Invalid username or password");
        }
        if(userEntity.getLastLoginDate() !=null){
            if(!userEntity.isLoggedout() && userEntity.getLastLoginDate().plusMinutes(5).isAfter(LocalDateTime.now())){
                throw new DuplicateLoginException("Duplicate login detected, please logout before you can login again");
            }
        }
        Map<String ,String> attributes = new HashMap<>();
        attributes.put("userId",userEntity.getUserId());
        attributes.put("id", String.valueOf(userEntity.getId()));
        attributes.put("system_group", userEntity.getRole().name());
        attributes.put("role", userEntity.getRole().name());
        attributes.put("name", userEntity.getName());
        attributes.put("username", userEntity.getUsername());

      String token = jwtService.expiringToken(attributes,"m1nTf1ntms_tsk", 5);
        userEntity.setLastLoginDate(LocalDateTime.now());
        userEntityDao.saveRecord(userEntity);
        return LoginResponseDTO.builder()
                .userId(userEntity.getUserId())
                .role(userEntity.getRole().name())
                .token(token)
                .build();
    }

    @Override
    public void processLogout(AuthenticatedUser currentUser) {
        log.info("logging out {}", currentUser.getUsername());
        UserEntity userEntity = userEntityDao.findByUsername(currentUser.getUsername()).orElseThrow(() -> new BadRequestException("Invalid username"));
        userEntity.setLoggedout(true);
        userEntityDao.saveRecord(userEntity);
    }
}
