package net.villenium.athena.client.impl.find;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindRequest {

    private String conditions;
    private int count;
}
