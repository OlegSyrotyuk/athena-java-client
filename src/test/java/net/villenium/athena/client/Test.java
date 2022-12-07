package net.villenium.athena.client;

import lombok.SneakyThrows;
import net.villenium.athena.client.impl.StorageManager;
import net.villenium.athena.client.util.Operator;

public class Test {

    @SneakyThrows
    public static void main(String[] args) {
        StorageManager manager = new StorageManager();
        manager.start("localhost:2202", "client", "123456");
        IAthenaStorage<User> storage = manager.create("users", User.class);
        ObjectPool<User> objectPool = storage.newObjectPool();
        objectPool.setDefaultObject(new User(null, 18, 10, 100));
        System.out.println(objectPool.get("piZda"));
        System.out.println(objectPool.get("PIZDA"));
        System.out.println(objectPool.get("piZDA"));
    }

    //{"age":{"$gt":10,"$lt":19}}

}
