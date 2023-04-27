package ua.olegsyrotyuk.athena.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindRequest {

    private String conditions;
    private int count;
}
