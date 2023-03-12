package net.villenium.athena.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import net.villenium.athena.client.annotation.Id;

import java.util.Map;

@Data
@AllArgsConstructor
public class TestJson implements Cloneable {

    @Id
    private final String name;
    private int level;

    @Override
    protected TestJson clone() throws CloneNotSupportedException {
        return (TestJson) super.clone();
    }

}
