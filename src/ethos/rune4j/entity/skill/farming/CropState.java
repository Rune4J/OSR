package ethos.rune4j.entity.skill.farming;

public class CropState {

    public int getSeedId() {
        return seedId;
    }

    public void setSeedId(int seedId) {
        this.seedId = seedId;
    }

    public int getCropIndex() {
        return cropIndex;
    }

    public void setCropIndex(int cropIndex) {
        this.cropIndex = cropIndex;
    }

    public int getDiseaseSuccessThreshold() {
        return diseaseSuccessThreshold;
    }

    public void setDiseaseSuccessThreshold(int diseaseSuccessThreshold) {
        this.diseaseSuccessThreshold = diseaseSuccessThreshold;
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public int getMaturityStages() {
        return maturityStages;
    }

    public void setRegrow(int maturityStages) {
        this.maturityStages = maturityStages;
    }

    public int getPatchLocation() {
        return patchLocation;
    }

    public void setPatchLocation(int patchLocation) {
        this.patchLocation = patchLocation;
    }

    @Override
    public String toString() {
        return "CropState{" +
                "seedId=" + seedId +
                ", cropIndex=" + cropIndex +
                ", diseaseSuccessThreshold=" + diseaseSuccessThreshold +
                ", stages=" + stages +
                ", maturityStages=" + maturityStages +
                ", patchLocation=" + patchLocation +
                '}';
    }

    private int seedId;
    private int cropIndex;
    private int diseaseSuccessThreshold;
    private int stages;
    // maturity stages are not the same as stages. Stages represents the stages in the growth cycle, maturity stages applies to patches that regrow and represents the number of stages of regrowth
    private int maturityStages;
    private int patchLocation;
}
