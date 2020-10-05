package util.encryption;



import play.Logger;
import play.shaded.oauth.org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class TripleDESEncryption implements Encryption{
    private static final String SHARED_KEY = "256EE615CB64085E0E1386895E5E6BC28A621F7C314016DC";
    private static String algorithm = "DESede";
    private static String transformation = "DESede/CBC/PKCS5Padding";

    @Override
    public String encrypt(String clearText) {
        String cipherHexText = null;
        try{
            if(!clearText.isEmpty()) {
                byte[] keyValue = Hex.decodeHex(SHARED_KEY.toCharArray());

                DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);

                /* Initialization Vector of 8 bytes set to zero. */
                IvParameterSpec iv = new IvParameterSpec(new byte[8]);

                SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
                Cipher encrypter = Cipher.getInstance(transformation);
                encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

                byte[] input = clearText.getBytes("UTF-8");

                byte[] encrypted = encrypter.doFinal(input);
                cipherHexText = new String(Hex.encodeHex(encrypted)).toUpperCase();
            }
        } catch (Exception e){
            Logger.error("Encryption Exception", e);
        }
        return cipherHexText;
    }

    @Override
    public String decrypt(String cipherText) {
        String decryptedClearText = null;
        try{
            if(!cipherText.isEmpty()) {
                byte[] keyValue = Hex.decodeHex(SHARED_KEY.toCharArray());

                DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);

                /* Initialization Vector of 8 bytes set to zero. */
                IvParameterSpec iv = new IvParameterSpec(new byte[8]);

                SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);

                Cipher decrypter = Cipher.getInstance(transformation);
                decrypter.init(Cipher.DECRYPT_MODE, key, iv);

                byte[] decrypted = decrypter.doFinal(Hex.decodeHex(cipherText.toCharArray()));
                decryptedClearText = new String(decrypted, "UTF-8");
            }
        } catch (Exception e){
            Logger.error("Encryption Error", e);
        }
        return decryptedClearText;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
