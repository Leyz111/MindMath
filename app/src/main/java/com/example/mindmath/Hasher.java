package com.example.mindmath;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import android.util.Base64;

public class Hasher {
    public static String hashString(String stringToHash, String entropy) {
        byte[] salt = entropy.toLowerCase().trim().getBytes();

        if (salt.length < 8) {
            salt = (entropy + "salt_padding").getBytes();
        }

        Argon2Parameters parameters = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                .withIterations(2)
                .withMemoryAsKB(32768)
                .withParallelism(1)
                .withSalt(salt)
                .build();

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(parameters);

        byte[] result = new byte[32];
        generator.generateBytes(stringToHash.toCharArray(), result);

        return Base64.encodeToString(result, Base64.NO_WRAP);
    }
}
