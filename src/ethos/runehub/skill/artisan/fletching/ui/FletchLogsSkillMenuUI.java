package ethos.runehub.skill.artisan.fletching.ui;

import ethos.model.players.Player;
import ethos.runehub.skill.artisan.fletching.Fletchable;
import ethos.runehub.skill.artisan.fletching.FletchingSkillMenuUI;
import ethos.runehub.skill.artisan.fletching.action.FletchLogsAction;

public class FletchLogsSkillMenuUI extends FletchingSkillMenuUI {

    @Override
    protected void create(Fletchable item, int actions) {
        this.close();
        this.getPlayer().getSkillController().getFletching().train(new FletchLogsAction(this.getPlayer(),item,actions));
    }

    public FletchLogsSkillMenuUI(Player player, int baseId) {
        super(player, baseId);
    }
}
