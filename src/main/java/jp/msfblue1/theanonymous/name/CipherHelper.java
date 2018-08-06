package jp.msfblue1.theanonymous.name;

/**
 * Created by msfblue1 on 2017/11/17.
 */
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CipherHelper {

    /**
     * 文字列を16文字の秘密鍵でAES暗号化してBase64した文字列で返す
     */
    public static String encrypt(String originalSource, String secretKey, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] originalBytes = originalSource.getBytes();
        byte[] encryptBytes = cipher(true, originalBytes, secretKey, algorithm);
        byte[] encryptBytesBase64 = Base64.getEncoder().encode(encryptBytes);
        return new String(encryptBytesBase64);
    }

    /**
     * Base64されたAES暗号化文字列を元の文字列に復元する
     */
    public static String decrypt(String encryptBytesBase64String, String secretKey, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {

        byte[] encryptBytes = Base64.getDecoder().decode(encryptBytesBase64String);
        byte[] originalBytes = cipher(false, encryptBytes, secretKey, algorithm);
        return new String(originalBytes);
    }

    /**
     * 暗号化/複合化の共通部分
     */
    private static byte[] cipher(boolean isEncrypt, byte[] source, String secretKey, String algorithm) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] secretKeyBytes = secretKey.getBytes();

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        if (isEncrypt) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }

        return cipher.doFinal(source);
    }
}
