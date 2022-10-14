package net.villenium.athena.client;

import net.villenium.athena.client.impl.StorageBuilder;
import net.villenium.athena.client.util.Constant;
import net.villenium.athena.client.util.Operator;

import java.security.SecureRandom;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        IAthenaStorage storage = new StorageBuilder()
                .create("users")
                .setGson(Constant.ATHENA_DEFAULT_GSON)
                .build();
        storage.start("localhost:1000", "test1", "1234567");

        User mother = new User("mother", 72);
        User test_user = new User("test_user", 45535);

        storage.async().upsert(test_user.getName(), test_user, Constant.WITHOUT_CACHING);

        for (User age : storage.findAll("age", 10, Operator.LESS_OR_EQUALS, User.class)) {
            System.out.println(age.toString());
        }
        //storage.stop();
    }

    public static String generateToken(int count) {
        String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

}
