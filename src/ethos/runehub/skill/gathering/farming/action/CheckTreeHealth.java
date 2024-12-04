package ethos.runehub.skill.gathering.farming.action;

import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.skill.SkillAction;

public class CheckTreeHealth extends SkillAction {


    @Override
    protected void onActionStart() {
        this.getActor().sendMessage("You inspect the tree for any signs of disease...");
    }

    @Override
    protected void onActionStop() {

    }

    @Override
    protected void onTick() {
        this.getActor().getSkillController().addXP(
                this.getSkillId(),
                this.getActor().getSkillController().getFarming().getXPFromCheckingHealth(context.getOccupiedById())
        );
        context.setCurrentGrowthStage(context.getCurrentGrowthStage() + 1); // TODO - We should probably calculate the stage changed so this can shared for all check health actions
        this.getActor().getSkillController().getFarming().savePatchContext(context);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(this.getActor().getX(), this.getActor().getY()));
        this.getActor().sendMessage("The tree is in perfect health!");
        this.stop();
    }

    @Override
    protected void onUpdate() {

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
    protected void validateWorldRequirements() {

    }

    @Override
    protected void updateAnimation() {

    }

    @Override
    protected void addItems(int id, int amount) {

    }

    public CheckTreeHealth(Player actor, PatchContext context) {
        super(actor, actor.getSkillController().getFarming().getId(), 3);
        this.context = context;
    }

    private final PatchContext context;
}
