package by.training.utility;

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
