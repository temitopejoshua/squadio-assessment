package com.squadio.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {
    private static final String KEY = "m1nt980#90lt3ch?";
    private static final String IV ="m1!45%78{8$t3ch@";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private static SecretKeySpec generateKey(String secretKey) {
        return new SecretKeySpec(secretKey.getBytes(), "AES");
    }

    private static IvParameterSpec generateIv(String iv) {
        return new IvParameterSpec(iv.getBytes());
    }

    public static String encryptObject(String input) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(KEY), generateIv(IV));
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public static String decryptObject(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(KEY), generateIv(IV));
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(strToDecrypt));

            return new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }
}


