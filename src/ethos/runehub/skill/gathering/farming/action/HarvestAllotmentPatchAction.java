package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvestAllotmentPatchAction extends HarvestPatchAction {

    private static final Logger logger = LoggerFactory.getLogger(HarvestAllotmentPatchAction.class.getName());


    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(830);
    }

    @Override
    protected void validateItemRequirements() {
        Preconditions.checkArgument(this.getActor().getItems().playerHasItem(952), "You need a @952");
    }

    @Override
    protected void onGather() {
        super.onGather();
        this.updateAchievementDiaries();
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

    private void updateAchievementDiaries() {
        if (patchContext.getOccupiedById() == 5318) {
            this.getActor().getAttributes().getAchievementController().completeAchievement(1483892139077748908L);
        }
    }

    public HarvestAllotmentPatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext patchContext) {
        super(player, nodeId, nodeX, nodeY, patchContext, 830);
        this.patchContext = patchContext;
    }

    private final PatchContext patchContext;
}
