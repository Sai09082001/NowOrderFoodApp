package com.example.noworderfoodapp;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class EncryptionUtils {

    private static final String AES_ALGORITHM = "AES";


    public static String encrypt(String plaintext, String password) throws Exception {
        SecretKey secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        String encryptedMessage = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);

        return encryptedMessage;
    }

    public static String decrypt(String ciphertext, String password) throws Exception {
        SecretKey secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.decode(ciphertext, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);

        return decryptedMessage;    }

    private static SecretKeySpec generateKey(String passphrase) {
        try {
            // Sử dụng một giá trị muối không trống để tạo khóa
            byte[] salt = "your_non_empty_salt".getBytes("UTF-8");

            // Số lần lặp và độ dài khóa có thể được điều chỉnh dựa trên yêu cầu bảo mật của bạn
            int iterationCount = 65536; // Điều chỉnh nếu cần
            int keyLength = 256; // Điều chỉnh nếu cần

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec keySpec = new PBEKeySpec(passphrase.toCharArray(), salt, iterationCount, keyLength);
            SecretKey secretKey = factory.generateSecret(keySpec);

            return new SecretKeySpec(secretKey.getEncoded(), AES_ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ một cách thích hợp (ví dụ: ghi log hoặc ném một ngoại lệ tùy chỉnh)
            return null;
        }
    }
}
