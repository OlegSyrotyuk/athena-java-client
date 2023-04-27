package ua.olegsyrotyuk.athena.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.olegsyrotyuk.athena.client.annotation.Id;

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
