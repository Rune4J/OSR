package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.skill.gathering.GatheringSkillAction;
import ethos.runehub.skill.gathering.tool.GatheringTool;
import ethos.runehub.skill.node.context.impl.GatheringNodeContext;
import ethos.runehub.skill.node.impl.gatherable.GatheringNode;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvestPatchAction extends GatheringSkillAction {

    private static final Logger logger = LoggerFactory.getLogger(HarvestPatchAction.class.getName());


    @Override
    protected void updateAnimation() {
    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(harvestAnimation);
    }

    @Override
    protected void validateItemRequirements() {
    }

    @Override
    protected void validateInventory() { // Override this as we don't care if the player has space or not
    }

    @Override
    protected void onGather() {
        int harvestedItemId = this.getActor().getSkillController().getFarming().getHarvestedItemId(patchContext.getOccupiedById());
        this.getActor().getItems().addOrDropItem(harvestedItemId, 1);
        this.getActor().sendMessage("You harvest a @" + harvestedItemId);
        this.getActor().getPA().addSkillXP(this.getActor().getSkillController().getFarming().getXPFromHarvesting(patchContext.getOccupiedById()), this.getSkillId(), true);
    }

    @Override
    protected GatheringTool getGetBestAvailableTool() throws NullPointerException {
        return new GatheringTool(
                952,
                1,
                SkillDictionary.Skill.FARMING.getId(),
                1.0,
                0,
                0f,
                830
        );
    }

    @Override
    public void onTick() {
        this.getActor().startAnimation(harvestAnimation);
        if (!hasHarvestedMinimum()) {
            patchContext.setHarvested(patchContext.getHarvested() + 1);
            this.onGather();
        } else if (this.getActor().getSkillController().getFarming().getBaseHarvestChanceToSave(patchContext.getOccupiedById()) <= this.getBaseRoll()) {
            patchContext.setHarvested(patchContext.getHarvested() + 1);
            this.onGather();
        } else {
            this.resetPatch();
            this.stop();
        }

        this.getActor().getSkillController().getFarming().savePatchContext(patchContext);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(targetedNodeContext.getX(), targetedNodeContext.getY()));

    }

    @Override
    protected void validateLevelRequirements() {
        Preconditions.checkArgument(PreconditionUtils.isTrue(this.getActor().getSkillController().getLevel(this.getSkillId()) >= this.getActor().getSkillController().getFarming().getSeedLevelRequirement(patchContext.getOccupiedById())),
                "You need a ?"
                        + SkillDictionary.getSkillNameFromId(this.getSkillId())
                        + " level of at least #"
                        + this.getActor().getSkillController().getFarming().getSeedLevelRequirement(patchContext.getOccupiedById())
                        + " to do this.");

    }

    @Override
    protected void validateWorldRequirements() {
        Preconditions.checkArgument(patchContext.getOccupiedById() > 0, "The patch is empty.");
    }

    protected void resetPatch() {
        patchContext.setOccupiedById(0);
        patchContext.setCurrentGrowthStage(3);
        patchContext.setDiseasedState(0);
        patchContext.setWateredState(0);
        patchContext.setCompostId(0);
        patchContext.setPlantTime(0);
        patchContext.setHarvestTime(0);
        patchContext.setPatchProtectedState(0);
        patchContext.setHarvested(0);
    }

    private boolean hasHarvestedMinimum() {
        return patchContext.getHarvested() >=
                (
                        this.getActor().getSkillController().getFarming().getMinimumHarvestAmount(patchContext.getOccupiedById())
                        + this.getActor().getSkillController().getFarming().getHarvestLifeBonus(patchContext)
                );
    }

    public HarvestPatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext patchContext, int harvestAnimation) {
        super(player, SkillDictionary.Skill.FARMING.getId(), new GatheringNodeContext<>(nodeId, nodeX, nodeY, 0) {
            @Override
            public GatheringNode getNode() {
                return new GatheringNode(nodeId, 1, 4, -1L, 1000, SkillDictionary.Skill.FARMING.getId(), 1000);
            }
        }, 3);
        this.patchContext = patchContext;
        this.harvestAnimation = harvestAnimation;
    }

    public HarvestPatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext patchContext, int harvestAnimation, int ticks) {
        super(player, SkillDictionary.Skill.FARMING.getId(), new GatheringNodeContext<>(nodeId, nodeX, nodeY, 0) {
            @Override
            public GatheringNode getNode() {
                return new GatheringNode(nodeId, 1, 4, -1L, 1000, SkillDictionary.Skill.FARMING.getId(), 1000);
            }
        }, ticks);
        this.patchContext = patchContext;
        this.harvestAnimation = harvestAnimation;
    }

    protected final PatchContext patchContext;
    protected final int harvestAnimation;
}
