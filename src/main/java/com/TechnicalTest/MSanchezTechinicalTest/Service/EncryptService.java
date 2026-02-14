
package com.TechnicalTest.MSanchezTechinicalTest.Service;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class EncryptService {
    private static final String Algoritmo = "AES";
    
    private static final String SECRET_KEY = "12345678901234567890123456789012"; // 256 bits
    
    public static String encrypt(String input) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), Algoritmo);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public static String decrypt(String encrypted) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), Algoritmo);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encrypted);
        return new String(cipher.doFinal(decoded));
    }
}
