package ethos.runehub.entity.player.action.impl.node;

import ethos.model.players.Player;
import ethos.runehub.entity.item.ItemInteractionContext;
import ethos.runehub.entity.player.action.Action;
import ethos.runehub.entity.player.action.ActionPriority;

public abstract class ItemOnNodeAction extends Action {

    public ItemOnNodeAction(Player player, ItemInteractionContext itemInteractionContext) {
        super(ActionPriority.MEDIUM, false, true, false);
        this.player = player;
        this.itemInteractionContext = itemInteractionContext;
    }

    protected final Player player;
    protected final ItemInteractionContext itemInteractionContext;
}
