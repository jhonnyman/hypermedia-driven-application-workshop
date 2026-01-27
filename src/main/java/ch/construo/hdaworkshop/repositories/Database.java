package ch.construo.hdaworkshop.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import ch.construo.hdaworkshop.dto.Cedent;
import ch.construo.hdaworkshop.dto.Claim;
import ch.construo.hdaworkshop.dto.ClaimStatus;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Database {

    private final List<Cedent> cedents = new ArrayList<>();

    private final ConcurrentHashMap<UUID, List<Claim>> claims = new ConcurrentHashMap<>();

    @PostConstruct
    public void setup() {
        generateDummyData();
    }

    public void generateDummyData() {
        var bob = new Cedent(Cedent.generateId(), "Bob");
        var jane = new Cedent(Cedent.generateId(), "Jane");
        cedents.add(jane);
        cedents.add(bob);

        claims.put(bob.id(),
                List.of(new Claim(Claim.generateId(), "Car crash", bob.id(), BigDecimal.TEN,
                        ClaimStatus.PAYED),
                        new Claim(Claim.generateId(), "Sports injury", bob.id(), BigDecimal.TWO,
                                ClaimStatus.OPEN),
                        new Claim(Claim.generateId(), "Dental care", bob.id(), BigDecimal.ONE,
                                ClaimStatus.REJECTED)));

        claims.put(jane.id(),
                List.of(new Claim(Claim.generateId(), "Vacations", jane.id(),
                        BigDecimal.valueOf(100), ClaimStatus.PAYED),
                        new Claim(Claim.generateId(), "Sports injury", jane.id(), BigDecimal.TWO,
                                ClaimStatus.OPEN),
                        new Claim(Claim.generateId(), "Dental care", jane.id(), BigDecimal.ONE,
                                ClaimStatus.REJECTED)));
    }


    public List<Cedent> listAllCedents() {
        return this.cedents;
    }

    public List<Claim> listAllClaims() {
        return claims.values().stream().flatMap(List::stream).toList();
    }

    public List<Claim> listCedentClaims(UUID cedentId) {
        if (!this.claims.containsKey(cedentId)) {
            return List.of();
        }
        return this.claims.get(cedentId);
    }
}
