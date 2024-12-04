package ethos.runehub.event.skill.farming;

import ethos.model.players.PlayerHandler;
import ethos.runehub.RunehubConstants;
import ethos.runehub.event.FixedScheduleEvent;
import ethos.runehub.skill.gathering.farming.FarmController;
import ethos.runehub.skill.gathering.farming.patch.PatchType;

import java.time.Duration;
import java.util.logging.Logger;

public class FlowerPatchGrowthCycleEvent extends FixedScheduleEvent {

    @Override
    public void execute() {
        Logger.getGlobal().info("Flower patch growth cycle event");
        PlayerHandler.getPlayers().forEach(player -> {
            if (player != null) {
                player.getSkillController().getFarming().advanceGrowthStage(1,16);
            }
        });
    }

    public FlowerPatchGrowthCycleEvent() {
        super(Duration.ofMinutes(RunehubConstants.FLOWER_PATCH_GROWTH_TICK_MINUTES).toMillis(), "flower-patch-growth-cycle");
    }
}
