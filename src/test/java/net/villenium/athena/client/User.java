package net.villenium.athena.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.villenium.athena.client.annotation.Id;
import net.villenium.athena.client.annotation.Name;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class User implements Serializable {

    @Id
    private String name;
    private String ip;
    private String server;
    private List<PermissionGroup> groups;
    @Name(name = "first_join")
    private long firstJoin;
    @Name(name = "last_join")
    private long lastJoin;
    @Name(name = "total_online")
    private long totalOnline;
}
