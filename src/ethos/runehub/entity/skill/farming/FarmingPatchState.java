package ethos.runehub.entity.skill.farming;

/**
 * Represents a player's farming patch's state
 * @author Phantasye
 */
public class FarmingPatchState {

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public int getWatered() {
        return watered;
    }

    public void setWatered(int watered) {
        this.watered = watered;
    }

    public int getDiseased() {
        return diseased;
    }

    public void setDiseased(int diseased) {
        this.diseased = diseased;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(int growthStage) {
        this.growthStage = growthStage;
    }

    public int getSeedId() {
        return seedId;
    }

    public void setSeedId(int seedId) {
        this.seedId = seedId;
    }

    @Override
    public String toString() {
        return "FarmingPatchState{" +
                "stateId=" + stateId +
                ", watered=" + watered +
                ", diseased=" + diseased +
                ", growthStage=" + growthStage +
                ", seedId=" + seedId +
                '}';
    }

    private long stateId;
    private int watered;
    private int diseased;
    private int growthStage;
    private int seedId;
}
