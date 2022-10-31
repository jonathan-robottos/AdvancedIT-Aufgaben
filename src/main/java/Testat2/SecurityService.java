package Testat2;

import javax.crypto.*;
import java.security.*;

public class SecurityService {
    public KeyPair gnerateKey() {
        KeyPair kp;
        KeyPairGenerator keyGen = null;

        try{
            keyGen = KeyPairGenerator.getInstance("RSA");
        }catch (NoSuchAlgorithmException e){}

        keyGen.initialize(1024);
        kp = keyGen.generateKeyPair();

        return kp;
    }

    public byte[] encrypt(String message, PublicKey pk){
        byte[] encryptedMessage = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            encryptedMessage = cipher.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return encryptedMessage;
    }

    public String decrypt(byte[] message, PrivateKey pk){
        byte[] decryptedMessage = null;
        Cipher cipher;
        
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pk);

            decryptedMessage = cipher.doFinal(message);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return new String(decryptedMessage);
    }
}
