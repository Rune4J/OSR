package ethos.rune4j.entity.skill.farming;

public class PatchMetaState {

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getPatchLocation() {
        return patchLocation;
    }

    public void setPatchLocation(int patchLocation) {
        this.patchLocation = patchLocation;
    }

    public int getCompostState() {
        return compostState;
    }

    public void setCompostState(int compostState) {
        this.compostState = compostState;
    }

    public int getProtectedState() {
        return protectedState;
    }

    public void setProtectedState(int protectedState) {
        this.protectedState = protectedState;
    }

    public int getHarvestedCount() {
        return harvestedCount;
    }

    public void setHarvestedCount(int harvestedCount) {
        this.harvestedCount = harvestedCount;
    }

    public long getPatchStateId() {
        return patchStateId;
    }

    public void setPatchStateId(long patchStateId) {
        this.patchStateId = patchStateId;
    }

    public long getPlantTime() {
        return plantTime;
    }

    public void setPlantTime(long plantTime) {
        this.plantTime = plantTime;
    }

    public long getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(long harvestTime) {
        this.harvestTime = harvestTime;
    }

    @Override
    public String toString() {
        return "PatchMetaState{" +
                "playerId=" + playerId +
                ", regionId=" + regionId +
                ", patchLocation=" + patchLocation +
                ", compostState=" + compostState +
                ", protectedState=" + protectedState +
                ", harvestedCount=" + harvestedCount +
                ", patchStateId=" + patchStateId +
                ", plantTime=" + plantTime +
                ", harvestTime=" + harvestTime +
                '}';
    }

    private long playerId;
    private int regionId;
    private int patchLocation;
    private int compostState;
    private int protectedState;
    private int harvestedCount;
    private long patchStateId;
    private long plantTime;
    private long harvestTime;
}
