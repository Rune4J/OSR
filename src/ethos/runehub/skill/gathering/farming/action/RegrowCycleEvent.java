package ethos.runehub.skill.gathering.farming.action;

import ethos.event.Event;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubUtils;
import ethos.runehub.event.FixedScheduleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
// TODO - make a regrow time column for each crop in the database
public class RegrowCycleEvent extends Event<Player> {

    @Override
    public void execute() {
        patchContext.setCurrentGrowthStage(patchContext.getCurrentGrowthStage() - 1);
        this.getAttachment().getSkillController().getFarming().updateFarm(RunehubUtils.getRegionId(patchX, patchY));
        this.getAttachment().getSkillController().getFarming().savePatchContext(patchContext);
        this.stop();
    }

    public RegrowCycleEvent(Player attachment, PatchContext patchContext, int patchX, int patchY) {
        super(attachment, 12);
        this.patchContext = patchContext;
        this.patchX = patchX;
        this.patchY = patchY;
    }

    private final PatchContext patchContext;
    private final int patchX;
    private final int patchY;
}
