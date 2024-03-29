package ua.olegsyrotyuk.athena.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.olegsyrotyuk.athena.client.annotation.Id;
import ua.olegsyrotyuk.athena.client.annotation.Name;

@ToString
@Getter
@AllArgsConstructor
public class Purchase {

    @Id
    private final String id;
    private final String buyer;
    private final String product;
    private final int price;
    private final long time;
    @Name(name = "server_type")
    private final String serverType;
    @Name(name = "transaction_id")
    private final String transactionId;

}
