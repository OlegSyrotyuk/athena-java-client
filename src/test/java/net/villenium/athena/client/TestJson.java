package net.villenium.athena.client;

import lombok.AllArgsConstructor;
import net.villenium.athena.client.annotation.Id;

import java.util.Map;

@AllArgsConstructor
public class TestJson {

    @Id
    private final String name;
    private final int level;

}
