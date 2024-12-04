package ethos.runehub.entity.player.action;

import ethos.model.players.Player;
import ethos.runehub.content.instance.BossArenaInstanceController;
import ethos.runehub.entity.player.action.impl.node.ClickNodeAction;
import ethos.runehub.entity.player.action.impl.node.SecondClickFarmingPatchAction;
import ethos.runehub.entity.player.action.impl.node.SecondClickFishingSpotAction;
import ethos.runehub.entity.player.action.impl.node.SecondClickSkillStationAction;
import ethos.runehub.merchant.MerchantCache;

import java.util.logging.Logger;

public class SecondClickNodeActionFactory {

    public static ClickNodeAction getAction(Player player, int nodeX, int nodeY, int nodeId) {
        Logger.getGlobal().fine("Second Click Action - " + nodeId + " " + nodeX + " " + nodeY);
        switch (nodeId) {
            case 7528:
                return new ClickNodeAction(player,nodeX,nodeY,nodeId) {
                    @Override
                    public void perform() {
                        MerchantCache.getInstance().read(nodeId).openShop(player);
                    }
                };
            case 6799:
            case 878:
            case 13542:
            case 15468:
            case 11017:
                return new SecondClickSkillStationAction(player, nodeX, nodeY, nodeId);
            case 11398:
                 return new ClickNodeAction(player,nodeX,nodeY,nodeId) {
                @Override
                public void perform() {
                    player.getPA().startLeverTeleport(3107 ,3228, 0);
                }
            };
            case 13620:
                return new ClickNodeAction(player,nodeX,nodeY,nodeId) {
                    @Override
                    public void perform() {
                        BossArenaInstanceController.getInstance().closeInstanceForPlayer(player);
                    }
                };
            case 20926:
            case 20927:
            case 20928:
            case 20929:
                return new SecondClickFishingSpotAction(player,nodeX,nodeY,nodeId);
            case 8550:
            case 8551:
            case 7847:
            case 8150:
            case 8554:
            case 8552:
            case 8556:
            case 8555:
            case 8553:
            case 8557:
            case 7849:
            case 7848:
            case 7850:
            case 8152:
            case 8151:
            case 8153:
            case 8175:
            case 8176:
            case 8173:
            case 8174:
            case 7577:
            case 7578:
            case 7580:
            case 7579:
            case 8391:
            case 8390:
            case 8389:
            case 8388:
            case 19147:
            case 7962:
            case 7965:
            case 7963:
            case 7964:
            case 8338:
            case 26579:
                return new SecondClickFarmingPatchAction(player, nodeX, nodeY, nodeId);
        }
        return null;
    }


}
