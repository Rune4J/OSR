package ethos.runehub.entity.skill.farming;

/**
 * Represents a player's farming patch
 * @author Phantasye
 */
public class PlayerPatchState {

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

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public int getCompostState() {
        return compostState;
    }

    public void setCompostState(int compostState) {
        this.compostState = compostState;
    }

    public int getHarvestedCount() {
        return harvestedCount;
    }

    public void setHarvestedCount(int harvestedCount) {
        this.harvestedCount = harvestedCount;
    }

    public int getPatchId() {
        return patchId;
    }

    public void setPatchId(int patchId) {
        this.patchId = patchId;
    }


    @Override
    public String toString() {
        return "PlayerPatchState{" +
                "playerId=" + playerId +
                ", regionId=" + regionId +
                ", stateId=" + stateId +
                ", patchId=" + patchId +
                ", compostState=" + compostState +
                ", harvestedCount=" + harvestedCount +
                '}';
    }

    private long playerId;
    private int regionId;
    private long stateId;
    private int patchId;
    private int compostState;
    private int harvestedCount;
}
