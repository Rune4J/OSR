// THIS CLASS HANDLES THE HUNGER SYSTEM MANAGED BY HEALTHMANAGER.JAVA

package ethos.runehub.entity.player.action.impl.item;

import ethos.model.players.Player;
import ethos.runehub.entity.player.action.impl.ClickItemAction;

import java.util.HashSet;
import java.util.Set;

public class FirstClickFoodAction extends ClickItemAction {

    private static final Set<Integer> ITEM_IDS = new HashSet<>();

    static {
        // Add the item IDs you want to check for
    	  ITEM_IDS.add(7934);
//        ITEM_IDS.add(####); 
//        ITEM_IDS.add(####);
//        ITEM_IDS.add(####);
//        ITEM_IDS.add(####);
    }

    public FirstClickFoodAction(Player player, int playerX, int playerY, int itemId) {
        super(player, playerX, playerY, itemId);
    }

    @Override
    public void perform() {
        if (ITEM_IDS.contains(itemId)) {
            player.handleItemConsumption(itemId);
            player.sendMessage("@red@You longer feel hungry@bla@.");
        } else {
            player.sendMessage("This doesn't make you feel any less hungry. You need @blu@ Field Rations.");
        }
    }
}