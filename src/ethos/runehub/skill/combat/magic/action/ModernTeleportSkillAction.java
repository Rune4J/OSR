package ethos.runehub.skill.combat.magic.action;

import ethos.model.players.Player;
import ethos.runehub.skill.Skill;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;

public class ModernTeleportSkillAction extends AbstractTeleportSkillAction{


    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(714);

    }

    @Override
    protected void onActionStop() {
        this.getActor().startAnimation(715);
        this.getActor().gfx0(-1);
        this.getActor().teleporting = false;
    }

    @Override
    protected void onTick() {
        final Point selectedTeleportPoint = teleportArea.getAllPoints().get(Skill.SKILL_RANDOM.nextInt(teleportArea.getAllPoints().size()));
        this.getActor().getPA().movePlayer(selectedTeleportPoint.getX(),selectedTeleportPoint.getY(),0);
        this.stop();
    }

    @Override
    protected void onUpdate() {
        if (this.getElapsedTicks() == 2) {
            this.getActor().gfx100(308);
        }
    }

    @Override
    protected void validateInventory() {
    }

    @Override
    protected void validateLevelRequirements() {
    }

    @Override
    protected void validateItemRequirements() {

    }

    @Override
    protected void updateAnimation() {
        this.getActor().startAnimation(793);
    }

    @Override
    protected void addItems(int id, int amount) {
    }

    public ModernTeleportSkillAction(Player actor, Rectangle teleportArea) {
        super(actor, 4, teleportArea);
    }
}
