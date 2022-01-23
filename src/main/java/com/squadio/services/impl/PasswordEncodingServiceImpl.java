package com.squadio.services.impl;

import com.squadio.services.PasswordEncodingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncodingServiceImpl implements PasswordEncodingService {
    private BCryptPasswordEncoder passwordEncoder;
    public PasswordEncodingServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encodePassword(String value) {
        return passwordEncoder.encode(value);
    }

    @Override
    public boolean matchedEncodedPassword(String inputValue, String encodedPassword) {
        return passwordEncoder.matches(inputValue, encodedPassword);
    }
}
