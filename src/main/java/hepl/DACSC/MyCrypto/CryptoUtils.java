package hepl.DACSC.MyCrypto;

import javax.crypto.*;
import java.security.*;

public class CryptoUtils {
    public static byte[] CryptSymDES(SecretKey cle, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher chiffrementE = Cipher.getInstance("DES/ECB/PKCS5Padding", "BC");
        chiffrementE.init(Cipher.ENCRYPT_MODE, cle);
        return chiffrementE.doFinal(data);
    }

    public static byte[] DecryptSymDES(SecretKey cle, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher chiffrementD = Cipher.getInstance("DES/ECB/PKCS5Padding", "BC");
        chiffrementD.init(Cipher.DECRYPT_MODE, cle);
        return chiffrementD.doFinal(data);
    }

    public static byte[] CryptAsymRSA(PublicKey cle, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher chiffrementE = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        chiffrementE.init(Cipher.ENCRYPT_MODE, cle);
        return chiffrementE.doFinal(data);
    }

    public static byte[] DecryptAsymRSA(PrivateKey cle, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher chiffrementD = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        chiffrementD.init(Cipher.DECRYPT_MODE, cle);
        return chiffrementD.doFinal(data);
    }

    public static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        signature.initSign(privateKey);
        signature.update(data);

        return signature.sign();
    }

    public static boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
        try{
            Signature s = Signature.getInstance("SHA256withRSA", "BC");
            s.initVerify(publicKey);
            s.update(data);
            return s.verify(signature);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public static SecretKey generateSessionKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        try{
            KeyGenerator kg = KeyGenerator.getInstance("DES", "BC");
            kg.init(128);

            return kg.generateKey();
        }
        catch(Exception e){
            throw new NoSuchAlgorithmException("Erreur");
        }
    }

    public static byte[] generateSalt(){
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        return salt;
    }

    public static byte[] generateDigest(String login, String password, byte[] salt) throws NoSuchAlgorithmException {
        try{

            MessageDigest digest = MessageDigest.getInstance("SHA-256", "BC");
            digest.update(login.getBytes());
            digest.update(password.getBytes());
            digest.update(salt);

            return digest.digest();
        }
        catch(Exception e){
            throw new NoSuchAlgorithmException("Erreur : " + e);
        }
    }
}
