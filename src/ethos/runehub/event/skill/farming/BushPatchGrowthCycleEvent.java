package ethos.runehub.event.skill.farming;

import ethos.model.players.PlayerHandler;
import ethos.runehub.RunehubConstants;
import ethos.runehub.event.FixedScheduleEvent;
import ethos.runehub.skill.gathering.farming.FarmController;
import ethos.runehub.skill.gathering.farming.patch.PatchType;

import java.time.Duration;
import java.util.logging.Logger;

public class BushPatchGrowthCycleEvent extends FixedScheduleEvent {

    @Override
    public void execute() {
        PlayerHandler.getPlayers().forEach(player -> {
            if (player != null) {
                player.getSkillController().getFarming().advanceGrowthStage(1,32);
            }
        });
    }

    public BushPatchGrowthCycleEvent() {
        super(Duration.ofMinutes(RunehubConstants.BUSH_PATCH_GROWTH_TICK_MINUTES).toMillis(), "bush-patch-growth-cycle");
    }
}
