package ch.construo.hdaworkshop.dto;

public enum ClaimStatus {
    OPEN(true), REJECTED(false), PAYED(false);

    private final boolean editable;

    ClaimStatus(boolean b) {
        this.editable = b;
    }

    public boolean isEditable() {
        return this.editable;
    }
}
