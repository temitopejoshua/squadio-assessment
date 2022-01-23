package com.squadio;

import com.squadio.exception.BadRequestException;
import com.squadio.exception.DuplicateLoginException;
import com.squadio.requestDTO.LoginRequestDTO;
import com.squadio.responseDto.LoginResponseDTO;
import com.squadio.security.AuthenticatedUser;
import com.squadio.security.JwtTokenProvider;
import com.squadio.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Slf4j
class SquadioApplicationTests {

	private final LoginService loginService;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public SquadioApplicationTests(LoginService loginService, JwtTokenProvider jwtTokenProvider) {
		this.loginService = loginService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void successfulLoginTestAdmin(){
		LoginRequestDTO adminLogin = new LoginRequestDTO();
		adminLogin.setUsername("Admin");
		adminLogin.setPassword("admin");
		LoginResponseDTO loginResponseDTO = loginService.processLogin(adminLogin);
		log.info("Login Response {} ", loginResponseDTO);
		String token = loginResponseDTO.getToken();
		assertTrue(StringUtils.isNotBlank(token));
	}

	@Test
	void successfulLoginTestUser(){
		LoginRequestDTO adminLogin = new LoginRequestDTO();
		adminLogin.setUsername("Mohammed");
		adminLogin.setPassword("user");
		LoginResponseDTO loginResponseDTO = loginService.processLogin(adminLogin);
		log.info("Login Response {} ", loginResponseDTO);
		String token = loginResponseDTO.getToken();
		assertTrue(StringUtils.isNotBlank(token));

	}

	@Test
	void wrongUsernameTest(){
		LoginRequestDTO adminLogin = new LoginRequestDTO();
		adminLogin.setUsername("Demo");
		adminLogin.setPassword("user");
		assertThrows(BadRequestException.class, () ->loginService.processLogin(adminLogin) );
	}

	@Test
	void wrongPasswordTest(){
		LoginRequestDTO adminLogin = new LoginRequestDTO();
		adminLogin.setUsername("Mohammed");
		adminLogin.setPassword("pass");
		assertThrows(BadRequestException.class, () ->loginService.processLogin(adminLogin));
	}

	@Test
	void duplicateLoginTest(){
		LoginRequestDTO adminLogin = new LoginRequestDTO();
		adminLogin.setUsername("Mohammed");
		adminLogin.setPassword("user");
		LoginResponseDTO loginResponseDTO = loginService.processLogin(adminLogin);
		log.info("Login Response {} ", loginResponseDTO);
		assertThrows(DuplicateLoginException.class, () ->loginService.processLogin(adminLogin));
	}



	@Test
	void loginLogoutLoginTest(){
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
		loginRequestDTO.setUsername("Mohammed");
		loginRequestDTO.setPassword("user");
		LoginResponseDTO loginResponseDTO = loginService.processLogin(loginRequestDTO);
		String token = loginResponseDTO.getToken();
		Optional<AuthenticatedUser> optionalAuthenticatedUser =  jwtTokenProvider.findUserByToken(token);
		loginService.processLogout(optionalAuthenticatedUser.get());

		loginResponseDTO = loginService.processLogin(loginRequestDTO);
		 token = loginResponseDTO.getToken();
		assertTrue(StringUtils.isNotBlank(token));
	}

}
