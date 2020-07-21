package com.wycliffe.services.utils;

import java.security.NoSuchAlgorithmException;

import com.wycliffe.services.viewModel.UserView;
import org.springframework.stereotype.Component;

@Component
public class BCryptHashing {

    public String enrcyptPassword(String originalPassword) {
        UserView user = new UserView();
        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        System.out.println(generatedSecuredPasswordHash);

        boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
        System.out.println(matched);
        user.setPassword(generatedSecuredPasswordHash);
        return generatedSecuredPasswordHash;
    }
//        public static void main(String[] args) throws NoSuchAlgorithmException
//        {
//            String  originalPassword = "password";
//            String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
//            System.out.println(generatedSecuredPasswordHash);
//
//            boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
//            System.out.println(matched);
//        }
}

