package ethos.rune4j.domain.model.entity.loot;


/**
 * Entity representing the association between a loot table and a container.
 */
public class LootTableContainerAssociation {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLootTableContainerId() {
        return lootTableContainerId;
    }

    public void setLootTableContainerId(long lootTableContainerId) {
        this.lootTableContainerId = lootTableContainerId;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "LootTableContainerAssociation{" +
                "id=" + id +
                ", lootTableContainerId=" + lootTableContainerId +
                ", tableId=" + tableId +
                ", weight=" + weight +
                '}';
    }

    public LootTableContainerAssociation() {
        // empty
    }

    public LootTableContainerAssociation(long id, long lootTableContainerId, long tableId, float weight) {
        this.id = id;
        this.lootTableContainerId = lootTableContainerId;
        this.tableId = tableId;
        this.weight = weight;
    }

    /**
     * The unique identifier for the association.
     */

    private long id;

    /**
     * The identifier of the loot table container.
     */
    private long lootTableContainerId;

    /**
     * The identifier of the table.
     */
    private long tableId;

    /**
     * The weight of the association.
     */
    private float weight;

}