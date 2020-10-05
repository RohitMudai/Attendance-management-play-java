package util.encryption;

public interface Encryption {
    String encrypt(String clearText);
    String decrypt(String cipherText);
}
