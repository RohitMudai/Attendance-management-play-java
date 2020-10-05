package models;

import util.encryption.Encryption;
import util.encryption.EncryptionFactory;

public class LoginDto {
    private String email;
    private String password;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Encryption encryption = EncryptionFactory.getInstance();
        String enryptedPassword = encryption.encrypt(password);
        this.password = enryptedPassword;
    }
}
