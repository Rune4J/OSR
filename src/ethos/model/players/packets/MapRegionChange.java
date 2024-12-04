package ethos.model.players.packets;

import ethos.model.players.PacketType;
import ethos.model.players.Player;
import ethos.runehub.RunehubUtils;
import ethos.runehub.world.WorldController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapRegionChange implements PacketType {

    private static final Logger logger = LoggerFactory.getLogger(MapRegionChange.class.getName());

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        System.out.println("Changing region");
        int regionId = RunehubUtils.getRegionId(c.absX, c.absY);
        if (c.getAttributes().getInstanceNodes().containsKey(regionId)) {
            c.getAttributes().getInstanceNodes().get(regionId).forEach(node -> c.getPA().checkObjectSpawn(node));
        }

        // Farming map updates
        c.getSkillController().getFarming().updateFarm(regionId);
//        final List<PlayerFarmingSave> farmingSave = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerAndRegion(c.getId(), regionId);
//        if (farmingSave != null) {
//            final List<Integer> bits = farmingSave.stream().map(PlayerFarmingSave::getBits).toList();
//            final int varbit = bits.stream().mapToInt(Integer::intValue).sum();
//            logger.info("Sending farming varbit: {} for region: {}", varbit, regionId);
//            c.getPA().sendConfig(529, varbit);
//        }

//        if (c.getContext().getPlayerSaveData().farmingConfig().containsKey(regionId)) {
//            final List<PlayerFarmingSave> farmingSave = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerAndRegion(c.getId(), regionId);
//            // Map player farming data to the farming config
//            final List<Integer> bits = farmingSave.stream().map(PlayerFarmingSave::getBits).toList();
//            final int varbit = bits.stream().mapToInt(Integer::intValue).sum();
////            final int varbit = c.getContext().getPlayerSaveData().farmingConfig().get(regionId).stream().mapToInt(FarmingConfig::varbit).sum();
//            logger.info("Sending farming varbit: {} for region: {}", varbit, regionId);
//            c.getPA().sendConfig(529, varbit);
//
//        }

        if (regionId == 12338) {
            if (c.getContext().getPlayerSaveData().getLecternHotspot() > 0)
                c.getPA().checkObjectSpawn(c.getContext().getPlayerSaveData().getLecternHotspot(), 3092, 3223, 1, 10);
        }
        WorldController.getInstance().loadChunk(regionId);
//		WorldController.getInstance().loadRegion(RunehubUtils.getRegionId(c.absX,c.absY));
    }

}
