package ethos.runehub.entity.player;

import ethos.runehub.skill.gathering.farming.crop.CropCache;

public class PlayerFarmingSave {

    public int getBits() {
        if (cropId != 0)
            return (CropCache.getInstance().read(cropId).getChildIndex() + stage + (getWateredStatus() << 6 | getDiseasedStatus() << 7) << patchGroupId);
        return (stage + (getWateredStatus() << 6 | getDiseasedStatus() << 7) << patchGroupId);
    }

    private int getWateredStatus() {
        return watered ? 1 : 0;
    }

    private int getDiseasedStatus() {
        return diseased ? 1 : 0;
    }

    public long getPlayerId() {
        return playerId;
    }

    public int getRegionId() {
        return regionId;
    }

    public int getPatchGroupId() {
        return patchGroupId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public boolean isDiseased() {
        return diseased;
    }

    public void setDiseased(boolean diseased) {
        this.diseased = diseased;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public int getCompost() {
        return compost;
    }

    public void setCompost(int compost) {
        this.compost = compost;
    }

    public int getHarvested() {
        return harvested;
    }

    public void setHarvested(int harvested) {
        this.harvested = harvested;
    }

    public void setPatchGroupId(int patchGroupId) {
        this.patchGroupId = patchGroupId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getPatchTypeId() {
        return patchTypeId;
    }

    public void setPatchTypeId(int patchTypeId) {
        this.patchTypeId = patchTypeId;
    }

    @Override
    public String toString() {
        return "PlayerFarmingSave{" +
                "playerId=" + playerId +
                ", regionId=" + regionId +
                ", patchGroupId=" + patchGroupId +
                ", cropId=" + cropId +
                ", stage=" + stage +
                ", diseased=" + diseased +
                ", watered=" + watered +
                ", compost=" + compost +
                ", harvested=" + harvested +
                ", patchTypeId=" + patchTypeId +
                '}';
    }

    public PlayerFarmingSave(long playerId) {
        this.playerId = playerId;
    }

    private final long playerId;
    private int regionId;
    private int patchGroupId;
    private int cropId;
    private int stage;
    private boolean diseased;
    private boolean watered;
    private int compost;
    private int harvested;
    private int patchTypeId;
}
