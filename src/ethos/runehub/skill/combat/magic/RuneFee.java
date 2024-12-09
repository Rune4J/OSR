package ethos.runehub.skill.combat.magic;

public class RuneFee {

    public int getRuneItemId() {
        return runeItemId;
    }

    public int getAmount() {
        return amount;
    }

    public int getRuneIdentifier() {
        return runeIdentifier;
    }

    @Override
    public String toString() {
        return "RuneFee{" +
                "runeItemId=" + runeItemId +
                ", amount=" + amount +
                ", runeIdentifier=" + runeIdentifier +
                '}';
    }

    public RuneFee(int runeIdentifier, int runeItemId, int amount) {
        this.runeIdentifier = runeIdentifier;
        this.runeItemId = runeItemId;
        this.amount = amount;
    }

    private final int runeItemId;
    private final int amount;
    private final int runeIdentifier;

}
