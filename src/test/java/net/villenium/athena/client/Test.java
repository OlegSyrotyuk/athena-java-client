package net.villenium.athena.client;

import lombok.SneakyThrows;
import net.villenium.athena.client.impl.StorageManager;

public class Test {

    @SneakyThrows
    public static void main(String[] args) {
        StorageManager manager = new StorageManager();
        manager.start("localhost:2202", "client", "123456");
        IAthenaStorage<User> storage = manager.create("users", User.class);
        ObjectPool<User> objectPool = storage.newObjectPool();
        objectPool.setDefaultObject(new User(null, 10, 10, 10));
        User larr4k = objectPool.get("Larr4k");
        larr4k.setAge(19);
        objectPool.save(larr4k.getName());
        manager.stop();
    }

    //{"age":{"$gt":10,"$lt":19}}

}
