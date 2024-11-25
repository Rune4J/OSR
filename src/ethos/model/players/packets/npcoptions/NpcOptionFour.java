package ethos.model.players.packets.npcoptions;

import java.util.Objects;

import ethos.Server;
import ethos.model.npcs.pets.PetHandler;
import ethos.model.players.Player;
import ethos.model.players.skills.agility.AgilityHandler;
import ethos.runehub.entity.player.action.FirstClickNPCActionFactory;
import ethos.runehub.entity.player.action.FourthClickNPCActionFactory;

/*
 * @author Matt
 * Handles all 4th options on non playable characters.
 */

public class NpcOptionFour {

	public static void handleOption(Player player, int npcType) {
		if (Server.getMultiplayerSessionListener().inAnySession(player)) {
			return;
		}
		player.clickNpcType = 0;
		player.rememberNpcIndex = player.npcClickIndex;
		player.npcClickIndex = 0;
		if (PetHandler.isPet(npcType)) {
			if (Objects.equals(PetHandler.getOptionForNpcId(npcType), "fourth")) {
				if (PetHandler.pickupPet(player, npcType, true))
					return;
			}
		}
		switch (npcType) {
		case 17: //Rug merchant - Sophanem
			if (!player.getDiaryManager().getDesertDiary().hasCompleted("EASY")) {
				player.getDH().sendNpcChat1("You must have completed all easy diaries here in the desert \\n to use this location.", 17, "Rug Merchant");
				return;
			}
			player.startAnimation(2262);
			AgilityHandler.delayFade(player, "NONE", 3285, 2815, 0, "You step on the carpet and take off...", "at last you end up in sophanem.", 3);
			break;

		case 2580:
			player.getPA().startTeleport(3039, 4788, 0, "modern", false);
			player.teleAction = -1;
			break;

		case 1501:
			player.getShops().openShop(23);
			break;

		case 315:
			player.getDH().sendDialogues(545, npcType);
			break;
			default:
				try {
					player.getAttributes().getActionController().submit(FourthClickNPCActionFactory.getAction(player,player.absX,player.absY,npcType,player.rememberNpcIndex));
				} catch (NullPointerException e1) {
					player.sendMessage("Nothing interesting happens.");
				}
				break;
		}
	}

}
