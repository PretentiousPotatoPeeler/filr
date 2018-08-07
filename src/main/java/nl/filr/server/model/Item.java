package nl.filr.server.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Item {
    private String path;
    private String name;
    private Instant date;
    private boolean dir;
}
