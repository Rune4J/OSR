package ethos.runehub.event.skill.farming;

import ethos.model.players.PlayerHandler;
import ethos.runehub.event.FixedScheduleEvent;
import ethos.runehub.skill.gathering.farming.FarmController;
import ethos.runehub.skill.gathering.farming.patch.PatchType;

import java.time.Duration;
import java.util.logging.Logger;

public class HerbPatchGrowthCycleEvent extends FixedScheduleEvent {

    @Override
    public void execute() {
        PlayerHandler.getPlayers().forEach(player -> {
            if (player != null) {
                player.getSkillController().getFarming().advanceGrowthStage(1,24);
            }
        });
    }

    public HerbPatchGrowthCycleEvent() {
        super(Duration.ofMinutes(20).toMillis(), "herb-patch-growth-cycle");
    }
}
