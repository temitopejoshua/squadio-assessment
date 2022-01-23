package com.squadio.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class RandomGeneratorUtils {
    public static String generateRandomNumber(int length){

       return RandomStringUtils.randomNumeric(length);
    }
    public synchronized static String generateAccountId(){
        long sequenceId = LocalDateTime.now().toEpochSecond(ZoneOffset.MAX);
        return String.format("%s%019d%s", RandomStringUtils.randomNumeric(2),
                sequenceId,
                RandomStringUtils.randomNumeric(1));
    }

    public synchronized static String generateUserId(){
        long sequenceId = LocalDateTime.now().toEpochSecond(ZoneOffset.MAX);
        return String.format("%s%019d%s", RandomStringUtils.randomNumeric(1),
                sequenceId,
                RandomStringUtils.randomNumeric(1));
    }
}
