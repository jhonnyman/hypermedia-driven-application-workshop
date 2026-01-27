package ch.construo.hdaworkshop.dto;

import java.util.UUID;

public record Cedent(UUID id, String name) {

    public static UUID generateId() {
        return UUID.randomUUID();
    }
}
