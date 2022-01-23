package com.squadio.security;

import com.squadio.dao.UserEntityDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenProvider extends AbstractUserDetailsAuthenticationProvider {
    private UserEntityDao userEntityDao;
    private JWTService jwtService;
    private final String TOKEN_SECRET = "m1nTf1ntms_tsk";
   public JwtTokenProvider(UserEntityDao userEntityDao, JWTService jwtService) {
       this.jwtService = jwtService;
       this.userEntityDao = userEntityDao;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("additionalAuthenticationChecks called");
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String token = String.valueOf(authentication.getCredentials());
        return findUserByToken(token).orElseThrow(() -> new UsernameNotFoundException("Invalid authorization token."));
    }

    public Optional<AuthenticatedUser> findUserByToken(String token) {
        Map<String, String> attributes = jwtService.verify(token, TOKEN_SECRET);
        if(attributes.isEmpty()) {
            return Optional.empty();
        }
        if(!attributes.containsKey("userId")) {
            return Optional.empty();
        }
        String userId = attributes.get("userId");
        Long id = Long.valueOf(attributes.get("id"));

        String systemGroup = attributes.getOrDefault("system_group", "");
        String privilegeCodes = attributes.getOrDefault("role", "");
        String name = attributes.getOrDefault("name", "");
        String username = attributes.getOrDefault("username", "");


        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUserId(userId);
        authenticatedUser.setId(id);

        authenticatedUser.setPassword(userId);
        authenticatedUser.setName(name);
        authenticatedUser.setUsername(username);
        if(!systemGroup.isEmpty()) {
            authenticatedUser.addAuthority(systemGroup);
        }
        if(!privilegeCodes.isEmpty()) {
            Arrays.stream(privilegeCodes.split(":")).forEach(authenticatedUser::addAuthority);
        }
        return Optional.of(authenticatedUser);
    }
}
