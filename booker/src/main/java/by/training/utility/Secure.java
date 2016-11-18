package by.training.utility;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.training.entity.UserEntity;

public abstract class Secure {

    public static String secureBySha(String rawPass, String salt) throws NoSuchAlgorithmException {
        MessageDigestPasswordEncoder encoder = new ShaPasswordEncoder();
        return encoder.encodePassword(rawPass, salt);
    }

    public static String encodeFileByMd5(InputStream inputStream)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = inputStream.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        inputStream.close();

        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
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
