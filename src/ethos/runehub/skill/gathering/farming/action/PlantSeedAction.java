package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.entity.item.ItemInteractionContext;
import ethos.runehub.skill.gathering.GatheringSkillAction;
import ethos.runehub.skill.gathering.tool.GatheringTool;
import ethos.runehub.skill.node.context.impl.GatheringNodeContext;
import ethos.runehub.skill.node.impl.gatherable.GatheringNode;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;

public class PlantSeedAction extends GatheringSkillAction {


    @Override
    protected void updateAnimation() {
//        this.getActor().startAnimation(2291);
    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected void validateItemRequirements() {
        int validPatchLocation = this.getActor().getSkillController().getFarming().getCropState(interactionContext.getUsedId()).getPatchLocation();
//        Preconditions.checkArgument(CropDAO.getInstance().getAllEntries().stream().anyMatch(crop -> crop.getId() == interactionContext.getUsedId()),"You can not plant this.");
        Preconditions.checkArgument(
                validPatchLocation == 0
                        ? context.getPatchLocationId() == 0 || context.getPatchLocationId() == 8
                        : context.getPatchLocationId() == validPatchLocation,
                "You cannot plant this here."
        );
        Preconditions.checkArgument(this.getActor().getItems().playerHasItem(interactionContext.getUsedId(), this.getActor().getSkillController().getFarming().getSeedsRequiredToPlant(interactionContext.getUsedId())), "You need @" + interactionContext.getUsedId() + " x #" + this.getActor().getSkillController().getFarming().getSeedsRequiredToPlant(interactionContext.getUsedId()) + ".");
        Preconditions.checkArgument(this.getActor().getItems().playerHasItem(5343), "You do not have a valid tool.");
    }

    @Override
    protected void onGather() {
        this.getActor().startAnimation(2291);
        this.getActor().getItems().deleteItem2(interactionContext.getUsedId(), this.getActor().getSkillController().getFarming().getSeedsRequiredToPlant(interactionContext.getUsedId()));
        context.setOccupiedById(interactionContext.getUsedId());
        context.setCurrentGrowthStage(0);
        context.setPlantTime(System.currentTimeMillis());
        this.getActor().getSkillController().getFarming().savePatchContext(context);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(interactionContext.getX(), interactionContext.getY()));
        this.getActor().getPA().addSkillXP(this.getActor().getSkillController().getFarming().getXPFromPlanting(interactionContext.getUsedId()), SkillDictionary.Skill.FARMING.getId(), true);
    }

    @Override
    protected GatheringTool getGetBestAvailableTool() throws NullPointerException {
        return new GatheringTool(
                5343,
                1,
                SkillDictionary.Skill.FARMING.getId(),
                1.0,
                0,
                0f,
                2291
        );
    }

    @Override
    public void onTick() {
        if (cycle <= this.getActor().getSkillController().getFarming().getSeedsRequiredToPlant(interactionContext.getUsedId())) {
            this.getActor().startAnimation(2291);
            this.updateAnimation();
            cycle++;
        } else {
            this.onGather();
            this.stop();
        }
    }

    @Override
    protected void validateLevelRequirements() {
        Preconditions.checkArgument(PreconditionUtils.isTrue(this.getActor().getSkillController().getLevel(this.getSkillId()) >= 1),
                "You need a ?"
                        + SkillDictionary.getSkillNameFromId(this.getSkillId())
                        + " level of at least #"
                        + this.getActor().getSkillController().getFarming().getSeedLevelRequirement(interactionContext.getUsedId())
                        + " to do this.");

    }

    @Override
    protected void validateWorldRequirements() {
        Preconditions.checkArgument((context.getOccupiedById() == 0 && context.getCurrentGrowthStage() == 3), "You must clear this patch before you can plant these.");
    }

    public PlantSeedAction(Player player, ItemInteractionContext interactionContext, PatchContext context) {
        super(player, SkillDictionary.Skill.FARMING.getId(), new GatheringNodeContext<>(interactionContext.getUsedWithId(), interactionContext.getX(), interactionContext.getY(), 0) {
            @Override
            public GatheringNode getNode() {
                return new GatheringNode(interactionContext.getUsedWithId(), 1, 4, -1L, 1000, SkillDictionary.Skill.FARMING.getId(), 1000);
            }
        }, 2);
        this.interactionContext = interactionContext;
        this.context = context;
    }

    private int cycle;
    private final ItemInteractionContext interactionContext;
    private final PatchContext context;
}
