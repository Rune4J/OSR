package ethos.runehub.skill.combat.magic.action;

import ethos.model.players.Player;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;

public class HomeModernTeleportSkillAction extends ModernTeleportSkillAction{

    @Override
    public void onTick() {
        super.onTick();
        if (this.getActor().getContext().getPlayerSaveData().getInstantTeleportCharges().value() <= 0) {
            this.getActor().getContext().getPlayerSaveData().setLastHomeTeleportTimestamp(System.currentTimeMillis());
        } else {
            this.getActor().getContext().getPlayerSaveData().getInstantTeleportCharges().decrement();
            this.getActor().sendMessage("#" + this.getActor().getContext().getPlayerSaveData().getInstantTeleportCharges().value() + " instant teleport charges remaining.");
        }
        this.getActor().getAttributes().getAchievementController().completeAchievement(-5365918924791133965L);
    }

    public HomeModernTeleportSkillAction(Player actor) {
        super(actor, new Rectangle(new Point(3102,3248),new Point(3106,3251)));
    }
}
