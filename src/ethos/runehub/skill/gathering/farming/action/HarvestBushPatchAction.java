package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvestBushPatchAction extends HarvestPatchAction {

    private static final Logger logger = LoggerFactory.getLogger(HarvestBushPatchAction.class.getName());


    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(2281);
    }

    @Override
    protected void onGather() {
        super.onGather();
        patchContext.setCurrentGrowthStage(patchContext.getCurrentGrowthStage() - 1);
        this.getActor().getSkillController().getFarming().savePatchContext(patchContext);
        this.getActor().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(this.getActor().getX(), this.getActor().getY()));
    }

    @Override
    public void onTick() {
        this.getActor().startAnimation(harvestAnimation);
        this.onGather();
    }

    @Override
    protected void validateWorldRequirements() {
        Preconditions.checkArgument(PreconditionUtils.isFalse(patchContext.getCurrentGrowthStage() == patchContext.getMaturityStage() - 4), "This bush has nothing to harvest.");
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

    public HarvestBushPatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext patchContext) {
        super(player,nodeId, nodeX, nodeY, patchContext, 2281,3);
        this.patchContext = patchContext;
    }

    private final PatchContext patchContext;
}
