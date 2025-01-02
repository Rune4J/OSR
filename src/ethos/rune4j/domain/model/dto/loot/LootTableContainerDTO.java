package ethos.rune4j.domain.model.dto.loot;


import ethos.rune4j.domain.model.entity.loot.LootTableContainerType;

public class LootTableContainerDTO {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public LootTableContainerType getType() {
        return type;
    }

    public void setType(LootTableContainerType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LootTableContainerDTO{" +
                "id=" + id +
                ", containerId=" + containerId +
                ", type=" + type +
                '}';
    }

    public LootTableContainerDTO() {
        // empty
    }

    public LootTableContainerDTO(long id, int containerId, LootTableContainerType type) {
        this.id = id;
        this.containerId = containerId;
        this.type = type;
    }

    private long id;

    private int containerId;

    private LootTableContainerType type;


}
