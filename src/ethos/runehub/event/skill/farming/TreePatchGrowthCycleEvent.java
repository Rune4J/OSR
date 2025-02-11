package ethos.runehub.event.skill.farming;

import ethos.model.players.PlayerHandler;
import ethos.runehub.RunehubConstants;
import ethos.runehub.event.FixedScheduleEvent;
import ethos.runehub.skill.gathering.farming.FarmController;
import ethos.runehub.skill.gathering.farming.patch.PatchType;

import java.time.Duration;
import java.util.logging.Logger;

public class TreePatchGrowthCycleEvent extends FixedScheduleEvent {

    @Override
    public void execute() {
        PlayerHandler.getPlayers().forEach(player -> {
            if (player != null) {
                player.getSkillController().getFarming().advanceGrowthStage(1,32);
            }
        });
    }

    public TreePatchGrowthCycleEvent() {
        super(Duration.ofMinutes(RunehubConstants.TREE_PATCH_GROWTH_TICK_MINUTES).toMillis(), "tree-patch-growth-cycle");
    }
}
