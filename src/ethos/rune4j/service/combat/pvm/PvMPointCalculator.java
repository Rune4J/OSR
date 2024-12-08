package ethos.rune4j.service.combat.pvm;

import ethos.model.npcs.NPC;
import ethos.model.players.Player;
import ethos.runehub.RunehubUtils;
import ethos.runehub.content.PointController;
import ethos.runehub.entity.mob.hostile.HostileMobIdContextLoader;
import ethos.runehub.world.WorldSettingsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvMPointCalculator {

    private static final Logger logger = LoggerFactory.getLogger(PvMPointCalculator.class);
    /**
     * Awards points to the player based on the NPC they killed.
     * @param killer The player who killed the NPC.
     * @param npc The NPC that was killed.
     */
    public void awardPoints(Player killer, NPC npc) {
        int basePointValue = calculateBasePoints(npc.getHealth().getMaximum());
        double levelBonus = calculateLevelBonus(npc.npcType, killer.combatLevel);
        double multiKillBonus = calculateMultiKillBonus(npc.npcType, killer.getAttributes().getLastKilledMobId(), killer.getAttributes().getSameTypeMobKills());
        int finalPointValue = calculateFinalPoints(basePointValue, levelBonus, multiKillBonus, isWeekendEventActive());

        logger.info("Player {} killed NPC {} and was awarded {} points.", killer.getName(), npc.npcType, finalPointValue);
        logger.info("Base points: {}, Level bonus: {}, Multi-kill bonus: {}, Weekend event active: {}", basePointValue, levelBonus, multiKillBonus, isWeekendEventActive());

        addPointsToPlayer(killer, finalPointValue);
    }

    /**
     * Calculates the base points awarded to the player. This is based on the NPC's max health.
     * @param npcMaxHealth The NPC's max health.
     * @return The base points awarded to the player.
     */
    private int calculateBasePoints(int npcMaxHealth) {
        return Math.max(1, npcMaxHealth / 25);
    }

    /**
     * Calculates the level bonus for the player. This is the percent difference between the player's combat level and the NPC's combat level.
     * @param npcId The NPC's ID.
     * @param playerCombatLevel The player's combat level.
     * @return The level bonus for the player.
     */
    private double calculateLevelBonus(int npcId, int playerCombatLevel) {
        int npcCombatLevel = HostileMobIdContextLoader.getInstance().read(npcId).getCombatLevel();
        return RunehubUtils.calculatePercentageOver(npcCombatLevel, playerCombatLevel);
    }

    /**
     * Calculates the final points awarded to the player.
     * @param basePointValue
     * @param levelBonus
     * @param isWeekendEvent
     * @return
     */
    private int calculateFinalPoints(int basePointValue, double levelBonus, double multiKillBonus, boolean isWeekendEvent) {
        int finalPointValue = (int) Math.min(50, Math.max(1, (basePointValue * levelBonus) * multiKillBonus));
        if (isWeekendEvent) {
            finalPointValue *= 2;
        }

        return finalPointValue;
    }

    /**
     * Calculates the multi-kill bonus for the player. This bonus increases the more of the same type of mob the player kills in a row.
     * TODO use npc category instead of npcId
     * @param npcId The NPC's ID.
     * @param lastKilledMobId The ID of the last mob the player killed.
     * @param sameTypeMobKillStreak The number of the same type of mob the player has killed in a row.
     * @return The multi-kill bonus for the player.
     */
    private double calculateMultiKillBonus(int npcId, int lastKilledMobId, int sameTypeMobKillStreak) {
        double bonus = 1.0; // Base bonus
        if (npcId != lastKilledMobId) {
            return bonus; // Reset bonus if the mob type changes
        }

        double baseIncrement = 0.01; // Initial increment
        double bonusDecayRate = 0.001; // How much the increment diminishes per range
        int rangeSize = 8; // Number of kills per range

        // Calculate the number of completed ranges
        int rangesCompleted = sameTypeMobKillStreak / rangeSize;

        logger.debug("Ranges completed: {}", rangesCompleted);
        logger.debug("Kill Streak: {}", sameTypeMobKillStreak);

        // Accumulate the bonus based on ranges completed
        for (int i = 0; i <= rangesCompleted; i++) {
            double currentIncrement = baseIncrement / (1 + i * bonusDecayRate);

            logger.debug("Current increment: {}", currentIncrement);

            bonus += currentIncrement;
        }

        return bonus;
    }

    /**
     * Checks if the weekend event is active.
     * @return True if the weekend event is active, false otherwise.
     */
    private boolean isWeekendEventActive() {
        return WorldSettingsController.getInstance().getWorldSettings().getWeekendEventId() == 6;
    }

    /**
     * Adds points to the player.
     * @param player The player to add points to.
     * @param points The number of points to add.
     */
    private void addPointsToPlayer(Player player, int points) {
        player.getAttributes().getPointController().addPoints(PointController.PointType.PVM, points);
    }
}
