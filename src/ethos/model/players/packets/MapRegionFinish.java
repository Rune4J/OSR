package ethos.model.players.packets;

import ethos.Server;
import ethos.model.players.PacketType;
import ethos.model.players.Player;
import ethos.runehub.world.WorldController;

public class MapRegionFinish implements PacketType {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MapRegionFinish.class.getName());

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int regionX = c.absX >> 3;
		int regionY = c.absY >> 3;
		int regionId = ((regionX / 8) << 8) + (regionY / 8);
//		WorldController.getInstance().loadRegion(regionId);
		WorldController.getInstance().loadChunk(regionId);
		Server.itemHandler.reloadItems(c);
		Server.getGlobalObjects().updateRegionObjects(c);
		System.out.println("Region ID: " + regionId);
//		if (c.getContext().getPlayerSaveData().farmingConfig().containsKey(regionId)) {
//			final int varbit = c.getContext().getPlayerSaveData().farmingConfig().get(regionId).stream().mapToInt(FarmingConfig::varbit).sum();
//			c.getPA().sendConfig(529,varbit);
//		}
		// Farming map updates
		c.getSkillController().getFarming().updateFarm(regionId);
//		final List<PlayerFarmingSave> farmingSave = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerAndRegion(c.getId(), regionId);
//		if (farmingSave != null) {
//			final List<Integer> bits = farmingSave.stream().map(PlayerFarmingSave::getBits).toList();
//			final int varbit = bits.stream().mapToInt(Integer::intValue).sum();
//			logger.info("Sending farming varbit: {} for region: {}", varbit, regionId);
//			c.getPA().sendConfig(529, varbit);
//		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}
		c.saveFile = true;

		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}
	}

}
