package nl.filr.server.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Item {
    private String name;
    private Instant date;
}
