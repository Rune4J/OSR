package ethos.rune4j.entity.skill.farming;

public class CropMeta {

    public int getSeedId() {
        return seedId;
    }

    public void setSeedId(int seedId) {
        this.seedId = seedId;
    }

    public int getPlantXp() {
        return plantXp;
    }

    public void setPlantXp(int plantXp) {
        this.plantXp = plantXp;
    }

    public int getHarvestXp() {
        return harvestXp;
    }

    public void setHarvestXp(int harvestXp) {
        this.harvestXp = harvestXp;
    }

    public int getHarvestedId() {
        return harvestedId;
    }

    public void setHarvestedId(int harvestedId) {
        this.harvestedId = harvestedId;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    public int getSeedsPlanted() {
        return seedsPlanted;
    }

    public void setSeedsPlanted(int seedsPlanted) {
        this.seedsPlanted = seedsPlanted;
    }

    public int getHarvestMinimum() {
        return harvestMinimum;
    }

    public void setHarvestMinimum(int harvestMinimum) {
        this.harvestMinimum = harvestMinimum;
    }

    public int getCheckHealthXp() {
        return checkHealthXp;
    }

    public void setCheckHealthXp(int checkHealthXp) {
        this.checkHealthXp = checkHealthXp;
    }

    public int getCtsMin() {
        return ctsMin;
    }

    public void setCtsMin(int ctsMin) {
        this.ctsMin = ctsMin;
    }

    public int getCtsMax() {
        return ctsMax;
    }

    public void setCtsMax(int ctsMax) {
        this.ctsMax = ctsMax;
    }

    @Override
    public String toString() {
        return "CropMeta{" +
                "seedId=" + seedId +
                ", plantXp=" + plantXp +
                ", harvestXp=" + harvestXp +
                ", harvestMinimum=" + harvestMinimum +
                ", harvestedId=" + harvestedId +
                ", levelRequirement=" + levelRequirement +
                ", seedsPlanted=" + seedsPlanted +
                ", checkHealthXp=" + checkHealthXp +
                ", ctsMin=" + ctsMin +
                ", ctsMax=" + ctsMax +
                '}';
    }

    private int seedId;
    private int plantXp;
    private int harvestXp;
    private int harvestMinimum;
    private int harvestedId;
    private int levelRequirement;
    private int seedsPlanted;
    private int checkHealthXp;
    private int ctsMin;
    private int ctsMax;

}
