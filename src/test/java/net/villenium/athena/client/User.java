package net.villenium.athena.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.villenium.athena.client.annotation.Id;
import net.villenium.athena.client.annotation.Name;

@Data
@AllArgsConstructor
public class User {

    @Id
    private final String name;
    private int age;
    private int xuy;
    @Name(name = "min_damage")
    private int minimalDamage;
}
