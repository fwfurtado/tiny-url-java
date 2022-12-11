package com.github.fwfurtado.tinyurl.datasource.hash;

import com.github.fwfurtado.tinyurl.configuration.TinyURLProperties;
import com.github.fwfurtado.tinyurl.core.services.CollisionService;
import com.github.fwfurtado.tinyurl.core.services.HashService;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;
import java.util.zip.CRC32;

@Component
public class HashGenerator implements HashService {

    private final MessageDigest digest;
    private final String salt;
    private final int saltLength;
    private final int hashLength;

    public HashGenerator(TinyURLProperties properties) throws NoSuchAlgorithmException {

        var generatorProperties = properties.generator();

        this.hashLength = generatorProperties.length();
        this.salt = generatorProperties.salt();

        this.saltLength = salt.length();

        this.digest = MessageDigest.getInstance("SHA-256");
    }

    @Override
    public String generate(String input) {
        return applyHash(input);
    }

    @Override
    public String generateWithPrefix(String input, int length) {
        var text = salt.charAt(length % saltLength) + input;
        return applyHash(text);
    }


    private String applyHash(String input) {
        var bytes = digest.digest(input.getBytes());
        var hash = bytesToHex(bytes);

        return hash.substring(0, hashLength);
    }

    private String bytesToHex(byte[] bytes) {
        var hexString = new StringBuilder(2 * bytes.length);

        for (byte b : bytes) {
            var hex = Integer.toHexString(0xff & b);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

}
