package net.villenium.athena.client;

import net.villenium.athena.client.impl.StorageBuilder;
import net.villenium.athena.client.util.Constant;
import net.villenium.athena.client.util.Operator;

public class Test {

    public static void main(String[] args) {

        IAthenaStorage<User> storage = new StorageBuilder<User>()
                .create("users")
                .setGson(Constant.ATHENA_DEFAULT_GSON)
                .setType(User.class)
                .build();
        storage.start("localhost:1000", "test1", "1234567");

        ObjectPool<User> pool = storage.newObjectPool();
        pool.setDefaultObject(new User(
                null,
                10,
                5,
                1000));

        storage.find()
                .whereEquals("rarity", "UNCOMMON")
                .whereAnd("min_physical_damage", 10, Operator.MORE_OR_EQUALS)
                .and(35, Operator.LESS_OR_EQUALS)
                .where("critical_chance", 21, Operator.MORE_OR_EQUALS)
                .count(15)
                .findAll();
//        List<User> list = storage.findAll(User.class)
//                .where("age", 7, Operator.LESS)
//                .where("xuy", 16, Operator.MORE_OR_EQUALS)
//                .count(1)
//                .execute();
//        for (User user : list) {
//            System.out.println(user);
//        }
//
//        storage.findAll(User.class)
//                .whereAnd("age", 10, Operator.MORE_OR_EQUALS)
//                .and(18, Operator.LESS_OR_EQUALS);
//
//        User mother = new User("mother", 6, 14);
//        User mother2 = new User("mother2", 3, 15);
//        User mother3 = new User("mother3", 7, 16);
//        User mother4 = new User("mother4", 1, 17);
//        User test_user = new User("test_user", 5, 25);
//
//        storage.upsert(mother.getName(), mother);
//        storage.upsert(mother2.getName(), mother2);
//        storage.upsert(mother3.getName(), mother3);
//        storage.upsert(mother4.getName(), mother4);
//
//        storage.upsert(test_user.getName(), test_user);
//
//        for (User age : storage.findAll("age", 10, Operator.LESS_OR_EQUALS, User.class)) {
//            System.out.println(age.toString());
//        }
        //storage.stop();
    }

    //{"age":{"$gt":10,"$lt":19}}

}
