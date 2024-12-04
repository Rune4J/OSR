package ethos.runehub.skill.gathering.farming.action;

import com.google.common.base.Preconditions;
import ethos.model.players.Player;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.skill.gathering.tool.GatheringTool;
import ethos.runehub.skill.gathering.tool.GatheringToolLoader;
import ethos.util.PreconditionUtils;
import org.runehub.api.util.SkillDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HarvestHerbPatchAction extends HarvestPatchAction {

    private static final Logger logger = LoggerFactory.getLogger(HarvestHerbPatchAction.class.getName());


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

    @Override
    protected GatheringTool getGetBestAvailableTool() throws NullPointerException {
        final List<GatheringTool> availableTools = GatheringToolLoader.getInstance().readAll().stream().filter(tool -> tool.getSkillId() == this.getSkillId()).filter(tool -> this.getActor().getSkillController().getLevel(this.getSkillId()) >= tool.getLevelRequired()).collect(Collectors.toList());
        final int bestToolLevel = availableTools.stream().mapToInt(GatheringTool::getLevelRequired).max().orElse(1);

        return availableTools.stream().filter(tool -> tool.getLevelRequired() == bestToolLevel).findFirst().orElse(null);
    }

    @Override
    protected void validateItemRequirements() {
        Preconditions.checkArgument(this.hasSecateurs(), "You need secateurs to harvest this");
    }

    private boolean hasSecateurs() {
        return (this.getActor().getItems().playerHasItem(5329))
                || (this.getActor().getItems().playerHasItem(7409) || this.getActor().getItems().isWearingItem(7409))
                || (this.getActor().getItems().playerHasItem(7411) || this.getActor().getItems().isWearingItem(7411));
    }

    public HarvestHerbPatchAction(Player player, int nodeId, int nodeX, int nodeY, PatchContext patchContext) {
        super(player,nodeId, nodeX, nodeY, patchContext, 2282);
        this.patchContext = patchContext;
    }

    private final PatchContext patchContext;
}
