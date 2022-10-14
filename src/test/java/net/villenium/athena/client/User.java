package net.villenium.athena.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @Id
    private final String name;
    private int age;
    private int xuy;

}
