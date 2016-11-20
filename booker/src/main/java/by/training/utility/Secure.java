package by.training.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.training.entity.UserEntity;
import by.training.exception.SecureException;

public abstract class Secure {

    public static String secureBySha(String rawPass, String salt) throws NoSuchAlgorithmException {
        MessageDigestPasswordEncoder encoder = new ShaPasswordEncoder();
        return encoder.encodePassword(rawPass, salt);
    }

    public static String encodeFilePassword(String filePath) throws SecureException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            try (InputStream in = new FileInputStream(filePath)) {
                while ((bytesCount = in.read(byteArray)) != -1) {
                    digest.update(byteArray, 0, bytesCount);
                }
            }

            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new SecureException(e.getMessage());
        }
    }

    public static UserEntity getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = auth.getPrincipal();
        try {
            return (UserEntity) user;
        } catch (ClassCastException e) {
            return null;
        }
    }

}
