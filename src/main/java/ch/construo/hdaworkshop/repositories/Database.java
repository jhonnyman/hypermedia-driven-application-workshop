package ch.construo.hdaworkshop.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ch.construo.hdaworkshop.dto.Cedent;
import ch.construo.hdaworkshop.dto.Claim;
import ch.construo.hdaworkshop.dto.ClaimStatus;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Database {

    private List<Cedent> cedents = new ArrayList<>();

    private List<Claim> claims = new ArrayList<>();

    @PostConstruct
    public void setup() {
        generateDummyData();
    }

    public void generateDummyData() {
        var bob = new Cedent("Bob");
        var jane = new Cedent("Jane");
        cedents.add(jane);
        cedents.add(bob);

        claims.addAll(List.of(
                new Claim(Claim.generateId(), "Car crash", bob, BigDecimal.TEN, ClaimStatus.PAID),
                new Claim(Claim.generateId(), "Sports injury", bob, BigDecimal.TWO,
                        ClaimStatus.OPEN),
                new Claim(Claim.generateId(), "Dental care", bob, BigDecimal.ONE,
                        ClaimStatus.REJECTED)));

        claims.addAll(List.of(
                new Claim(Claim.generateId(), "Vacations", jane, BigDecimal.valueOf(100),
                        ClaimStatus.PAID),
                new Claim(Claim.generateId(), "Sports injury", jane, BigDecimal.TWO,
                        ClaimStatus.OPEN),
                new Claim(Claim.generateId(), "Dental care", jane, BigDecimal.ONE,
                        ClaimStatus.REJECTED)));
    }


    public List<Cedent> listAllCedents() {
        return this.cedents;
    }

    public List<Claim> listAllClaims() {
        return claims;
    }

    public List<Claim> listCedentClaims(String name) {
        return this.claims.stream().filter(c -> c.cedent().name().equals(name)).toList();
    }

    public void addCedent(Cedent cedent) {
        this.cedents.add(cedent);
    }

    public void addClaim(Claim claim) {
        this.claims.add(claim);
    }

    public void deleteCedent(String name) {
        this.cedents =
                new ArrayList<>(this.cedents.stream().filter(c -> !c.name().equals(name)).toList());
        deleteAllCedentClaims(name);
    }

    public void deleteAllCedentClaims(String name) {
        this.claims = new ArrayList<>(
                this.claims.stream().filter(c -> !c.cedent().name().equals(name)).toList());
    }

    public void deleteClaim(UUID id) {
        this.claims =
                new ArrayList<>(this.claims.stream().filter(c -> !c.id().equals(id)).toList());
    }

    public Optional<Claim> getClaim(UUID claimId) {
        return this.claims.stream().filter(c -> c.id().equals(claimId)).findAny();
    }

    public void updateClaim(Claim claim) {
        this.claims = new ArrayList<>(this.claims.stream().map(c -> {
            if (c.id().equals(claim.id())) {
                return claim;
            }
            return c;
        }).toList());
    }
}
