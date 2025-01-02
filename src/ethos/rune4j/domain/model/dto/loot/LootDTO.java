package ethos.rune4j.domain.model.dto.loot;

/**
 * Represents a loot data transfer object.
 */
public class LootDTO {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public float getBaseChance() {
        return baseChance;
    }

    public void setBaseChance(float baseChance) {
        this.baseChance = baseChance;
    }

    public boolean isSecondary() {
        return secondary;
    }

    public void setSecondary(boolean secondary) {
        this.secondary = secondary;
    }

    public boolean isTertiary() {
        return tertiary;
    }

    public void setTertiary(boolean tertiary) {
        this.tertiary = tertiary;
    }

    public long getSubTableId() {
        return subTableId;
    }

    public void setSubTableId(long subTableId) {
        this.subTableId = subTableId;
    }

    @Override
    public String toString() {
        return "LootDTO{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", itemId=" + itemId +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", baseChance=" + baseChance +
                ", secondary=" + secondary +
                ", tertiary=" + tertiary +
                ", subTableId=" + subTableId +
                '}';
    }

    public LootDTO() {
        // Empty constructor
    }

    public LootDTO(long id, long tableId, int itemId, int minAmount, int maxAmount, float baseChance, boolean secondary, boolean tertiary, long subTableId) {
        this.id = id;
        this.tableId = tableId;
        this.itemId = itemId;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.baseChance = baseChance;
        this.secondary = secondary;
        this.tertiary = tertiary;
        this.subTableId = subTableId;
    }

    /**
     * The unique identifier for the LootEntity.
     */
    private long id;

    /**
     * The ID of the loot table associated with this loot.
     */
    private long tableId;

    /**
     * The ID of the item associated with this loot.
     */
    private int itemId;

    /**
     * The minimum amount of the item that can be looted.
     */
    private int minAmount;

    /**
     * The maximum amount of the item that can be looted.
     */
    private int maxAmount;

    /**
     * The base chance of looting the item.
     * This field has a precision of 7 and a scale of 6.
     */
    private float baseChance;

    /**
     * Can this item be returned in a secondary roll?
     */
    private boolean secondary;

    /**
     * Can this item be returned in a tertiary roll?
     */
    private boolean tertiary;

    /**
     * The ID of the sub-table associated with this loot.
     */
    private long subTableId;
}
