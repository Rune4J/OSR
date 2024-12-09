package ethos.runehub.skill.combat.magic.action;

import ethos.model.players.Player;
import ethos.runehub.skill.Skill;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;

public class SlowTeleportSkillAction extends AbstractTeleportSkillAction{


    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(4850);
    }

    @Override
    protected void onActionStop() {
        this.getActor().startAnimation(65535);
        this.getActor().gfx0(-1);
        this.getActor().teleporting = false;
    }

    @Override
    protected void onTick() {
        final Point selectedTeleportPoint = teleportArea.getAllPoints().get(Skill.SKILL_RANDOM.nextInt(teleportArea.getAllPoints().size()));
        this.getActor().getPA().movePlayer(selectedTeleportPoint.getX(),selectedTeleportPoint.getY(),0);
    }

    @Override
    protected void onUpdate() {
        if (this.getElapsedTicks() == 3) {
            this.getActor().startAnimation(4853);
            this.getActor().gfx0(802);
        } else if (this.getElapsedTicks() == 6) {
            this.getActor().startAnimation(4855);
            this.getActor().gfx0(803);
        } else if (this.getElapsedTicks() == 9) {
            this.getActor().startAnimation(4857);
            this.getActor().gfx0(804);
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

    }

    @Override
    protected void addItems(int id, int amount) {

    }

    public SlowTeleportSkillAction(Player actor, Rectangle teleportArea) {
        super(actor, 12, teleportArea);
    }


}
