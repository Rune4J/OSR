package ethos.rune4j.domain.model.entity.loot;

/**
 * Represents a loot table entity.
 */
public class LootTable {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LootTableDTO{" +
                "id=" + id +
                '}';
    }


    /**
     * The unique identifier for the loot table.
     */
    private long id;
}
