package pl.harpi.samples.apache.commons.codec;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println(DigestUtils.md5Hex("John Smith"));
        System.out.println(UUID.randomUUID());

        // 1234567890123456789012345678901234567890
        // a721566f73aceca8ce02a40b791afc1c69b519ef
    }
}
