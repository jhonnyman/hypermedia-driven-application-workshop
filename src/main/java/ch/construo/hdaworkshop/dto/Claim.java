package ch.construo.hdaworkshop.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record Claim(UUID id, String cause, Cedent cedent, BigDecimal amount, ClaimStatus status) {

    public static UUID generateId() {
        return UUID.randomUUID();
    }
}
