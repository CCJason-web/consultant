package com.cjt.consultant.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password+"sall", BCrypt.gensalt());
        return hashedPassword;
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        boolean passwordMatch = BCrypt.checkpw(password+"sall", hashedPassword);
        return passwordMatch;
    }
}
