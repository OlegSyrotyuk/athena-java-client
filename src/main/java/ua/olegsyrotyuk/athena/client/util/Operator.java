package ua.olegsyrotyuk.athena.client.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Operator {
    MORE("$gt"),
    MORE_OR_EQUALS("$gte"),
    LESS("$lt"),
    LESS_OR_EQUALS("$lte"),
    EQUALS("$eq"),
    NOT_EQUALS("$ne");

    private final String value;
}
