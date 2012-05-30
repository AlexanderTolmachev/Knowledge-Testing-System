package ru.spbstu.appmaths.knowledgetesting;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 28.05.12
 */
public class PasswordEncoder {
    public String encodePassword(String password) {
        String encodedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            BigInteger hash = new BigInteger(1, messageDigest.digest());
            encodedPassword = hash.toString(16);
        } catch (NoSuchAlgorithmException e) {

        }
        return encodedPassword;
    }

//    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = PasswordEncoder.getInstance();
//
//        System.out.println(passwordEncoder.encodePassword("test-passwordvfsvfdjvbhkvbhjkfvbhjskjhbfhvkjhsbhfkvjhsbhvkfbhskjfbvhfksjhdfbvhskjhfbvhkshjfbvhfksjhfbhvjksjhdfbvhjksjhdbfhvjksjhdfbhjvks"));
//    }
}
