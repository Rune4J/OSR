package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.Server;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.skill.Skill;
import ethos.runehub.skill.gathering.tool.GatheringTool;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;

public class ChopDownTreePatchAction extends HarvestPatchAction {

    @Override
    protected boolean depleteNode() { // All trees have a 1/8 chance of depleting
        final int efficiencyBonus = (int) this.getActor().getSkillController().getWoodcutting().getEfficiencyBonus();
        System.out.printf("Efficiency Bonus: %d\n", efficiencyBonus);
        final int playerBaseRoll = Skill.SKILL_RANDOM.nextInt(8 + efficiencyBonus);

        return playerBaseRoll == 0;
    }

    @Override
    protected void onDeplete() {
        patchContext.setCurrentGrowthStage(patchContext.getCurrentGrowthStage() + 1);
        this.getActor().getSkillController().getFarming().savePatchContext(patchContext);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(targetedNodeContext.getX(), targetedNodeContext.getY()));
        this.getActor().sendMessage("The tree has been depleted.");
        Server.getEventHandler().submit(new RegrowCycleEvent(this.getActor(), patchContext, targetedNodeContext.getX(), targetedNodeContext.getY()));
    }

    @Override
    protected void validateItemRequirements() {
        Preconditions.checkArgument(this.getGetBestAvailableTool() != null, "You need a hatchet.");
    }

    @Override
    protected void validateLevelRequirements() { // since harvesting this patch is a woodcutting action, we need to check the player's woodcutting level
        Preconditions.checkArgument(PreconditionUtils.isTrue(this.getActor().getSkillController().getLevel(SkillDictionary.Skill.WOODCUTTING.getId()) >= this.getActor().getSkillController().getFarming().getSeedLevelRequirement(patchContext.getOccupiedById())),
                "You need a ?"
                        + SkillDictionary.getSkillNameFromId(SkillDictionary.Skill.WOODCUTTING.getId())
                        + " level of at least #"
                        + this.getActor().getSkillController().getFarming().getSeedLevelRequirement(patchContext.getOccupiedById())
                        + " to do this.");

    }

    @Override
    protected void onGather() {
        int harvestedItemId = this.getActor().getSkillController().getFarming().getHarvestedItemId(patchContext.getOccupiedById());
        this.getActor().getItems().addOrDropItem(harvestedItemId, 1);
        this.getActor().sendMessage("You get some @" + harvestedItemId);
        this.getActor().getPA().addSkillXP(this.getActor().getSkillController().getFarming().getXPFromHarvesting(patchContext.getOccupiedById()), SkillDictionary.Skill.WOODCUTTING.getId(), true);
    }

    @Override
    public void onTick() {
        this.getActor().startAnimation(harvestAnimation);
        if (!depleteNode()) {
            double cts = this.getActor().getSkillController().getFarming().getHarvestChanceToSaveWithPower(patchContext.getOccupiedById());
            double baseRoll = this.getBaseRoll();
            System.out.printf("CTS: %f\n", cts);
            System.out.printf("Base Roll: %f\n", baseRoll);
            if (cts >= baseRoll) {
                this.onGather();
            }
        } else {
            this.onDeplete();
            this.onGather();
//            this.resetPatch();
            this.stop();
        }
    }

    @Override
    protected GatheringTool getGetBestAvailableTool() throws NullPointerException { // we need to override this to return the best available tool for woodcutting (a hatchet)
        return this.getActor().getSkillController().getWoodcutting().getBestAvailableTool();
    }


    public ChopDownTreePatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext patchContext) {
        super(player, nodeId, nodeX, nodeY, patchContext, player.getSkillController().getWoodcutting().getBestAvailableTool() != null ?
                player.getSkillController().getWoodcutting().getBestAvailableTool().getAnimationId() : -1);
    }
}
