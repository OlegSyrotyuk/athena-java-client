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
        User test = objectPool.get("Test");
        test.setAge(43);
        objectPool.save(test.getName());
        manager.stop();

        storage.find()
                .whereEquals("rarity", "UNCOMMON")
                .whereAnd("min_physical_damage", 10, Operator.MORE_OR_EQUALS)
                .and(35, Operator.LESS_OR_EQUALS)
                .where("critical_chance", 21, Operator.MORE_OR_EQUALS)
                .count(15)
                .findAll();
    }

    //{"age":{"$gt":10,"$lt":19}}

}
