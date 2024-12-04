package ethos.runehub.event.skill.farming;

import ethos.model.players.PlayerHandler;
import ethos.runehub.RunehubConstants;
import ethos.runehub.RunehubUtils;
import ethos.runehub.event.FixedScheduleEvent;
import ethos.runehub.skill.gathering.farming.FarmController;
import ethos.runehub.skill.gathering.farming.patch.PatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WeedGrowthCycleEvent extends FixedScheduleEvent {

    private static final Logger logger = LoggerFactory.getLogger(WeedGrowthCycleEvent.class.getName());

    @Override
    public void execute() {
        logger.info("Weeds growth cycle event");
        PlayerHandler.getPlayers().forEach(player -> {
            if (player != null) {
                // We attempt to grow weeds in all patch types but they will only grow if the patch is considered overgrown state
                logger.info("Growth tick for patch: {}", 0);
                player.getSkillController().getFarming().advanceGrowthStage(1, 0,true);

                logger.info("Growth tick for patch: {}", 8);
                player.getSkillController().getFarming().advanceGrowthStage(1, 8,true);

                logger.info("Growth tick for patch: {}", 16);
                player.getSkillController().getFarming().advanceGrowthStage(1, 16,true);

                logger.info("Growth tick for patch: {}", 24);
                player.getSkillController().getFarming().advanceGrowthStage(1, 24,true);

                logger.info("Growth tick for patch: {}", 32);
                player.getSkillController().getFarming().advanceGrowthStage(1, 32,true);

            }
        });
    }

    public WeedGrowthCycleEvent() {
        super(Duration.ofMinutes(RunehubConstants.WEED_PATCH_GROWTH_TICK_MINUTES).toMillis(), "weeds-growth-cycle");
    }
}
