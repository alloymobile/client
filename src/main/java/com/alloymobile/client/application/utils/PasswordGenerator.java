package com.alloymobile.client.application.utils;

import org.bson.types.ObjectId;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;


@Component
public class PasswordGenerator implements PasswordEncoder {

    private final String secret;
    private final Integer iteration;
    private final Integer keylength;

    public PasswordGenerator(Environment env) {
        this.secret = env.getProperty("client.password.encoder.secret");
        this.iteration = Integer.parseInt(env.getProperty("client.password.encoder.iteration"));
        this.keylength = Integer.parseInt(env.getProperty("client.password.encoder.keylength"));
    }

    /**
     * More info (https://www.owasp.org/index.php/Hashing_Java) 404 :(
     * @param cs password
     * @return encoded password
     */
    @Override
    public String encode(CharSequence cs) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }

    public String phoneTokenGenerator(){
        int number = new Random().nextInt(999999);
        return String.format("%06d", number);
    }

    public String emailTokenGenerator(){
        return new ObjectId().toString();
    }
}
