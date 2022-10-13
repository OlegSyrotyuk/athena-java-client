package net.villenium.athena.client;

import net.villenium.athena.client.impl.StorageBuilder;
import net.villenium.athena.client.util.Constant;

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

        storage.async().upsert(test_user.getName(), test_user, DataOptions.builder().cache(true).cacheTime(10).build());

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            User user = storage.getObject(i + "user", User.class);
            System.out.println(user.toString());
        }
        System.out.println((System.currentTimeMillis() - start) + "ms.");

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
