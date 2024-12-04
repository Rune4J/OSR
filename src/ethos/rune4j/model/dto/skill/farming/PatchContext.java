package ethos.rune4j.model.dto.skill.farming;

public class PatchContext {

    public int getPatchLocationId() {
        return patchLocationId;
    }

    public void setPatchLocationId(int patchLocationId) {
        this.patchLocationId = patchLocationId;
    }

    public int getPatchProtectedState() {
        return patchProtectedState;
    }

    public void setPatchProtectedState(int patchProtectedState) {
        this.patchProtectedState = patchProtectedState;
    }

    public int getCurrentGrowthStage() {
        return currentGrowthStage;
    }

    public void setCurrentGrowthStage(int currentGrowthStage) {
        this.currentGrowthStage = currentGrowthStage;
    }

    public int getCompostId() {
        return compostId;
    }

    public void setCompostId(int compostId) {
        this.compostId = compostId;
    }

    public int getOccupiedById() {
        return occupiedById;
    }

    public void setOccupiedById(int occupiedById) {
        this.occupiedById = occupiedById;
    }

    public int getDiseasedState() {
        return diseasedState;
    }

    public void setDiseasedState(int diseasedState) {
        this.diseasedState = diseasedState;
    }

    public int getWateredState() {
        return wateredState;
    }

    public void setWateredState(int wateredState) {
        this.wateredState = wateredState;
    }

    public int getMaturityStage() {
        return maturityStage;
    }

    public void setMaturityStage(int maturityStage) {
        this.maturityStage = maturityStage;
    }

    public int getDiseaseChance() {
        return diseaseChance;
    }

    public void setDiseaseChance(int diseaseChance) {
        this.diseaseChance = diseaseChance;
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

    public int getHarvested() {
        return harvested;
    }

    public void setHarvested(int harvested) {
        this.harvested = harvested;
    }

    @Override
    public String toString() {
        return "PatchContext{" +
                "patchLocationId=" + patchLocationId +
                ", patchProtectedState=" + patchProtectedState +
                ", compostId=" + compostId +
                ", currentGrowthStage=" + currentGrowthStage +
                ", occupiedById=" + occupiedById +
                ", diseasedState=" + diseasedState +
                ", wateredState=" + wateredState +
                ", maturityStage=" + maturityStage +
                ", diseaseChance=" + diseaseChance +
                ", plantTime=" + plantTime +
                ", harvestTime=" + harvestTime +
                ", harvested=" + harvested +
                '}';
    }

    private int patchLocationId;
    private int patchProtectedState;
    private int compostId;
    private int currentGrowthStage;
    private int occupiedById;
    private int diseasedState;
    private int wateredState;
    private int maturityStage;
    private int diseaseChance;
    private long plantTime;
    private long harvestTime;
    private int harvested;

}
