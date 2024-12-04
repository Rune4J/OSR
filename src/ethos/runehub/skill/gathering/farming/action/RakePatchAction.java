package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.entity.skill.farming.PatchMetaState;
import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.content.journey.JourneyStepType;
import ethos.runehub.entity.skill.farming.FarmingPatchState;
import ethos.runehub.entity.skill.farming.PlayerPatchState;
import ethos.runehub.skill.gathering.GatheringSkillAction;
import ethos.runehub.skill.gathering.farming.FarmController;
import ethos.runehub.skill.gathering.farming.FarmingConfig;
import ethos.runehub.skill.gathering.tool.GatheringTool;
import ethos.runehub.skill.node.context.impl.GatheringNodeContext;
import ethos.runehub.skill.node.impl.gatherable.GatheringNode;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RakePatchAction extends GatheringSkillAction {

    private static final Logger logger = LoggerFactory.getLogger(RakePatchAction.class.getName());

    @Override
    protected void updateAnimation() {
//        this.getActor().startAnimation(2273);
    }

    @Override
    protected void onEvent() {
        logger.info("On event");
    }

    @Override
    protected void validateItemRequirements() {
        Preconditions.checkArgument(this.getActor().getItems().playerHasItem(5341), "You do not have a valid tool.");
    }

    @Override
    protected void onGather() {
//        this.addItems(6055, 1);
//        this.getActor().getPA().addSkillXP(4, this.getSkillId(), true);
////        this.getActor().getAttributes().getJourneyController().checkJourney(5310519480822153480L,1);
//        this.getActor().getAttributes().getJourneyController().checkJourney(6055,1, JourneyStepType.COLLECTION);
//        cycle++;
//        config.incrementCycle();
//        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(this.getTargetedNodeContext().getX(), this.getTargetedNodeContext().getY()));
    }

    @Override
    protected GatheringTool getGetBestAvailableTool() throws NullPointerException {
        return new GatheringTool(
                5341,
                1,
                SkillDictionary.Skill.FARMING.getId(),
                1.0,
                0,
                0f,
                2273
        );
    }

    @Override
    public void onTick() {
        if (context.getCurrentGrowthStage() < 3) {
            this.getActor().startAnimation(2273);
            this.getActor().getSkillController().addXP(this.getSkillId(), 4);
            context.setCurrentGrowthStage(context.getCurrentGrowthStage() + 1);
            context.setHarvestTime(System.currentTimeMillis());
            this.getActor().getSkillController().getFarming().savePatchContext(context);
            this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(this.getTargetedNodeContext().getX(), this.getTargetedNodeContext().getY()));
            this.getActor().getAttributes().getJourneyController().checkJourney(6055, 1, JourneyStepType.COLLECTION);
            this.addItems(6055, 1);
            if (context.getCurrentGrowthStage() == 3) {
                this.stop();
            }
        } else {
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
        // We ignore this as the ID will change with each weed stage
    }

    public RakePatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext context) {
        super(player, SkillDictionary.Skill.FARMING.getId(), new GatheringNodeContext<>(nodeId, nodeX, nodeY, 0) {
            @Override
            public GatheringNode getNode() {
                return new GatheringNode(nodeId, 1, 4, -1L, 1000, SkillDictionary.Skill.FARMING.getId(), 1000); // We could put this in the db but whatever
            }
        }, 4);
        this.context = context;
    }

    private final PatchContext context;
}
