package ethos.runehub.skill.combat.magic.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;

public class HomeSlowTeleportSkillAction extends SlowTeleportSkillAction{

    @Override
    public void validateItemRequirements() {
        Preconditions.checkArgument(this.hasBeen(this.getActor().getContext().getPlayerSaveData().getLastHomeTeleportTimestamp() + getTeleportDelay(), System.currentTimeMillis()),
                "You must wait another "
                        + this.getMSAsMinutes(
                        this.timeBetweenMs(
                                this.getActor().getContext().getPlayerSaveData().getLastHomeTeleportTimestamp() + getTeleportDelay(),
                                System.currentTimeMillis()
                        ))
                        + " minutes before doing this.");
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.getActor().getContext().getPlayerSaveData().getTeleportCharges().value() <= 0) {
            this.getActor().getContext().getPlayerSaveData().setLastHomeTeleportTimestamp(System.currentTimeMillis());
        } else {
            this.getActor().getContext().getPlayerSaveData().getTeleportCharges().decrement();
            this.getActor().sendMessage("#" + this.getActor().getContext().getPlayerSaveData().getTeleportCharges().value() + " teleport charges remaining.");
        }
        this.getActor().getAttributes().getAchievementController().completeAchievement(-5365918924791133965L);
        this.stop();
    }

    protected long getTeleportDelay() {
        // 30 minutes
        long rechargeTime = 30 * 60000L;
        return (long) (rechargeTime - (rechargeTime * this.getActor().getTeleportRechargeReduction()));
    }

    private long timeBetweenMs(long timestamp1, long timestamp2) {
        return timestamp1 - timestamp2;
    }

    private int getMSAsMinutes(long ms) {
        return (int) (ms / 60000);
    }

    private boolean hasBeen(long timeMS, long timestamp) {
        return timestamp > timeMS;
    }

    public HomeSlowTeleportSkillAction(Player actor) {
        super(actor, new Rectangle(new Point(3102,3248),new Point(3106,3251)));
    }

}
