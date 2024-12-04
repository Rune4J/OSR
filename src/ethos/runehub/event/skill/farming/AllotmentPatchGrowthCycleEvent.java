package ethos.runehub.event.skill.farming;

import ethos.model.players.PlayerHandler;
import ethos.runehub.RunehubConstants;
import ethos.runehub.event.FixedScheduleEvent;
import ethos.runehub.skill.gathering.farming.FarmController;
import ethos.runehub.skill.gathering.farming.patch.PatchType;

import java.time.Duration;
import java.util.logging.Logger;

public class AllotmentPatchGrowthCycleEvent extends FixedScheduleEvent {

    @Override
    public void execute() {
        Logger.getGlobal().info("Allotment growth cycle event");
        PlayerHandler.getPlayers().forEach(player -> {
            if (player != null) {
                // Attempt to grow allotment patches
                player.getSkillController().getFarming().advanceGrowthStage(1,0);
                player.getSkillController().getFarming().advanceGrowthStage(1,8);
            }
        });
    }

    public AllotmentPatchGrowthCycleEvent() {
        super(Duration.ofMinutes(RunehubConstants.ALLOTMENT_PATCH_GROWTH_TICK_MINUTES).toMillis(), "allotment-patch-growth-cycle");
    }
}
