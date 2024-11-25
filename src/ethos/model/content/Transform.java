package ethos.model.content;

import ethos.model.players.Player;

public class Transform {

    /**
     * Transform the player into the specified NPC.
     *
     * @param player The player to transform.
     * @param npcId  The ID of the NPC to transform into.
     */
    public static void npcTransform(Player player, int npcId) {
        player.justTransformed = true;
        player.npcId2 = npcId;
        player.setUpdateRequired(true);
        player.setAppearanceUpdateRequired(true);
        // Optional: Set any additional transformation flags if needed
    }

    /**
     * Transform the player back to normal.
     *
     * @param player The player to transform back.
     */
    public static void unTransform(Player player) {
        player.npcId2 = -1;
        player.setUpdateRequired(true);
        player.setAppearanceUpdateRequired(true);
        // Optional: Reset any additional transformation flags if needed
    }
}