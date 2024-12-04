package ethos.rune4j.entity.skill.farming;

public class PatchState {

    public long getPatchStateId() {
        return patchStateId;
    }

    public void setPatchStateId(long patchStateId) {
        this.patchStateId = patchStateId;
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

    public int getPatchLocation() {
        return patchLocation;
    }

    public void setPatchLocation(int patchLocation) {
        this.patchLocation = patchLocation;
    }

    @Override
    public String toString() {
        return "PatchState{" +
                "patchStateId=" + patchStateId +
                ", watered=" + watered +
                ", diseased=" + diseased +
                ", growthStage=" + growthStage +
                ", seedId=" + seedId +
                ", patchLocation=" + patchLocation +
                '}';
    }

    private long patchStateId;
    private int watered;
    private int diseased;
    private int growthStage;
    private int seedId;
    private int patchLocation;
}
