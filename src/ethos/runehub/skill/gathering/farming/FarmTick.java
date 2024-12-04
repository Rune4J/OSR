package ethos.runehub.skill.gathering.farming;

import ethos.model.players.Player;
import ethos.runehub.entity.player.PlayerFarmingSave;
import ethos.runehub.entity.player.PlayerFarmingSaveDAO;

import java.util.List;

public class FarmTick implements Runnable{

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FarmTick.class.getName());

    @Override
    public void run() {
        logger.info("Running FarmTick for player: {}", player.playerName);
        if (!player.disconnected) {
            int regionX = player.absX >> 3;
            int regionY = player.absY >> 3;
            int regionId = ((regionX / 8) << 8) + (regionY / 8);
            final List<PlayerFarmingSave> farmingSave = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerAndRegion(player.getContext().getId(), regionId);
            if (farmingSave != null) {
                final List<Integer> bits = farmingSave.stream().map(PlayerFarmingSave::getBits).toList();
                final int varbit = bits.stream().mapToInt(Integer::intValue).sum();
                logger.info("Sending farming varbit: {} for region: {}", varbit, regionId);
                player.getPA().sendConfig(529, varbit);
            }
//            if (player.getContext().getPlayerSaveData().farmingConfig().containsKey(regionId)) {
//                final int varbit = player.getContext().getPlayerSaveData().farmingConfig().get(regionId).stream().mapToInt(FarmingConfig::varbit).sum();
//                player.getPA().sendConfig(529, varbit);
//            }
        } else {
            player.getAttributes().getFarmTickExecutorService().shutdownNow();
        }
    }


    public FarmTick(Player player) {
        this.player = player;
    }

    private final Player player;
}
