package by.batseko.library.util;

import by.batseko.library.exception.EncryptionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class Encryption {
    private static final Logger LOGGER = LogManager.getLogger(Encryption.class);

    private static final int ENCRYPTION_ITERATIONS = 1024;
    private static final int KEY_LENGTH = 128;
    private static final int SALT_SIZE = 16;
    private static final byte[] CONSTANT_INNER_SALT = {29, 49, -57, 117, -38, -26, -43, 118, -93, -117, 116, 45, 49, -99, 61, -72};
    private static final String ENCRYPTION_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String DIVIDER = ":";
    private static final int SALT_INDEX = 0;
    private static final int HASH_INDEX = 1;

    private final SecureRandom secureRandom;
    private final Base64.Encoder encoder;
    private final Base64.Decoder decoder;
    private SecretKeyFactory secretKeyFactory;

    public Encryption() {
        secureRandom = new SecureRandom();
        encoder = Base64.getEncoder();
        decoder = Base64.getDecoder();
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.fatal(String.format("No such algorithm: %s", ENCRYPTION_ALGORITHM), e);
        }
    }

    public String generateHash(String password) throws EncryptionException {
        byte[] dynamicSalt = getSalt();
        byte[] hash = generateTransitionalHash(password, CONSTANT_INNER_SALT);
        hash = generateTransitionalHash(getStringFromHash(hash), dynamicSalt);
        return encoder.encodeToString(dynamicSalt) + DIVIDER + encoder.encodeToString(hash);
    }

    public boolean validatePassword(String inputPassword, String storedPassword) throws EncryptionException {
        String[] parts = storedPassword.split(DIVIDER);
        byte[] dynamicSalt = decoder.decode(parts[SALT_INDEX]);
        byte[] storedHash = decoder.decode(parts[HASH_INDEX]);
        byte[] hash = generateTransitionalHash(inputPassword, CONSTANT_INNER_SALT);
        hash = generateTransitionalHash(getStringFromHash(hash), dynamicSalt);
        return Arrays.equals(storedHash, hash);
    }

    private byte[] generateTransitionalHash(String password, byte[] salt) throws EncryptionException {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, ENCRYPTION_ITERATIONS, KEY_LENGTH);
        try {
            return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        } catch (InvalidKeySpecException e) {
            LOGGER.error("Encryption KeySpec exception: ", e);
            throw new EncryptionException(e);
        }
    }

    private byte[] getSalt() {
        byte[] salt = new byte[SALT_SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private String getStringFromHash(byte[] hash) {
        return new BigInteger(1, hash).toString(16);
    }
}
