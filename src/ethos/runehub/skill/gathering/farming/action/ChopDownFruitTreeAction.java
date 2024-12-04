package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.Server;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.skill.gathering.woodcutting.action.ActiveWoodcuttingSkillAction;
import ethos.util.PreconditionUtils;

import java.util.logging.Logger;

public class ChopDownFruitTreeAction extends ActiveWoodcuttingSkillAction {

    @Override
    protected boolean depleteNode() {
        context.setCurrentGrowthStage(context.getCurrentGrowthStage() + 18);
       return true; // these will always deplete
    }


    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(this.getActor().getContext().getPlayerSaveData().getSkillAnimationOverrideMap().containsKey(this.getSkillId()) ?
                this.getActor().getContext().getPlayerSaveData().getSkillAnimationOverrideMap().get(this.getSkillId()) :
                this.getActor().getSkillController().getWoodcutting().getBestAvailableTool().getAnimationId());
    }

    @Override
    public void onTick() {
        this.onGather();
    }

    @Override
    protected void validateLevelRequirements() {
        // There are no level requirements for chopping down a fruit tree

    }

    @Override
    protected void validateWorldRequirements() {
        // This is a handled by the harvesting action
    }

    @Override
    protected void onGather() {
        this.getActor().getItems().addOrDropItem(2511,1);
        this.addXp(25);
        context.setCurrentGrowthStage(context.getCurrentGrowthStage() + 19);
        this.getActor().getSkillController().getFarming().savePatchContext(context);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(this.getActor().getX(), this.getActor().getY()));
        this.stop();
    }

    public ChopDownFruitTreeAction(Player actor, int nodeId, int nodeX, int nodeY, int nodeZ, PatchContext context) {
        super(actor, actor.getSkillController().getWoodcutting().getId(), nodeId, nodeX, nodeY, nodeZ);
        this.context = context;
    }

    private final PatchContext context;
}
