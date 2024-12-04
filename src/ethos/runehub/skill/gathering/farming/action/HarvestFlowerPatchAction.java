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

public class HarvestFlowerPatchAction extends HarvestPatchAction {

    private static final Logger logger = LoggerFactory.getLogger(HarvestFlowerPatchAction.class.getName());


    @Override
    protected void onActionStart() {
        this.getActor().startAnimation(2286);
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

    public HarvestFlowerPatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext patchContext) {
        super(player,nodeId, nodeX, nodeY, patchContext, 2286);
        this.patchContext = patchContext;
    }

    private final PatchContext patchContext;
}
