package com.squadio.services;

public interface PasswordEncodingService {
    String encodePassword(String value);
    boolean matchedEncodedPassword(String inputValue, String encodedPassword);
}
