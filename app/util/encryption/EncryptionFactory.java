package util.encryption;

public class EncryptionFactory {
    private static Encryption encryption;
    private EncryptionFactory(){

    }
    public static Encryption getInstance() {
        if(encryption == null){
            encryption = new TripleDESEncryption();
        }
        return encryption;
    }
}
