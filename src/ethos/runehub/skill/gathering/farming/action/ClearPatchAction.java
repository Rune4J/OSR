package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.entity.item.ItemInteractionContext;
import ethos.runehub.skill.gathering.GatheringSkillAction;
import ethos.runehub.skill.gathering.farming.FarmingConfig;
import ethos.runehub.skill.gathering.farming.crop.CropCache;
import ethos.runehub.skill.gathering.farming.crop.CropDAO;
import ethos.runehub.skill.gathering.tool.GatheringTool;
import ethos.runehub.skill.node.context.impl.GatheringNodeContext;
import ethos.runehub.skill.node.impl.gatherable.GatheringNode;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;

public class ClearPatchAction extends GatheringSkillAction {


    @Override
    protected void updateAnimation() {

    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected void validateItemRequirements() {
        Preconditions.checkArgument(this.getActor().getItems().playerHasItem(952), "You need a spade.");
    }

    @Override
    protected void onGather() {
        context.setCurrentGrowthStage(3); //we set it to 3 so the patch is empty and has no weeds
        context.setDiseasedState(0);
        context.setWateredState(0);
        context.setOccupiedById(0);
        context.setCompostId(0);
        context.setPatchProtectedState(0);
        context.setHarvestTime(0);
        context.setPlantTime(0);
        this.getActor().getSkillController().getFarming().savePatchContext(context);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(targetedNodeContext.getX(),targetedNodeContext.getY()));
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
        this.getActor().startAnimation(830);
        if (cycle <= 4) {
            this.getActor().startAnimation(830);
            cycle++;
        } else {
            this.onGather();
            this.getActor().startAnimation(65535);
            this.stop();
        }
    }

    @Override
    protected void validateLevelRequirements() {
        Preconditions.checkArgument(PreconditionUtils.isTrue(this.getActor().getSkillController().getLevel(this.getSkillId()) >= 1),
                "You need a ?"
                        + SkillDictionary.getSkillNameFromId(this.getSkillId())
                        + " level of at least #"
                        + 1
                        + " to do this.");

    }

    @Override
    protected void validateWorldRequirements() {
    }

    public ClearPatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext context) {
        super(player, SkillDictionary.Skill.FARMING.getId(), new GatheringNodeContext<>(nodeId, nodeX, nodeY, 0) {
            @Override
            public GatheringNode getNode() {
                return new GatheringNode(nodeId, 1, 4, -1L, 1000, SkillDictionary.Skill.FARMING.getId(), 1000);
            }
        }, 2);
        this.context = context;
    }

    private int cycle;
    private final PatchContext context;
}
