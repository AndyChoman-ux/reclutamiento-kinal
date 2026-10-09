package main.java.com.vyorg.reclutamiento.security;

import main.java.com.vyorg.reclutamiento.security.jdbcrypt.BCrypt;

public class PasswordHasher {

    public String hash(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }

    public boolean coincide(String contrasena, String hash) {
        return BCrypt.checkpw(contrasena, hash);
    }
}
