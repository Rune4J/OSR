package ethos.rune4j.domain.model.dto.loot;

/**
 * Represents a loot table data transfer object.
 */
public class LootTableDTO {

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

    public LootTableDTO() {
        // Empty constructor
    }

    public LootTableDTO(long id) {
        this.id = id;
    }

    /**
     * The unique identifier for the LootTable.
     */
    private long id;
}
