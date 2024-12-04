package ethos.runehub.skill.gathering.farming;

import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.runehub.db.PlayerCharacterContextDataAccessObject;
import ethos.runehub.entity.player.PlayerCharacterContext;
import ethos.runehub.entity.player.PlayerFarmingSave;
import ethos.runehub.entity.player.PlayerFarmingSaveDAO;
import ethos.rune4j.entity.skill.farming.CropState;
import ethos.runehub.entity.skill.farming.FarmingPatchState;
import ethos.runehub.entity.skill.farming.PlayerPatchState;
import ethos.runehub.service.skill.farming.CropStateService;
import ethos.runehub.service.skill.farming.FarmingPatchStateService;
import ethos.runehub.service.skill.farming.PlayerPatchStateService;
import ethos.runehub.service.skill.farming.impl.CropStateServiceImpl;
import ethos.runehub.service.skill.farming.impl.FarmingPatchStateServiceImpl;
import ethos.runehub.service.skill.farming.impl.PlayerPatchStateServiceImpl;
import ethos.runehub.skill.gathering.farming.crop.*;
import ethos.runehub.skill.gathering.farming.patch.PatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class FarmController {

    private static final Logger logger = LoggerFactory.getLogger(FarmController.class.getName());

    private static FarmController instance = null;

    public static FarmController getInstance() {
        if (instance == null)
            instance = new FarmController();
        return instance;
    }

    private boolean isOvergrown(PlayerFarmingSave save) {
        return save.getCropId() <= 0 && save.getStage() == 0;
    }

    private boolean isOvergrown(FarmingConfig config) {
        return config.getCrop() <= 0 && config.getStage() == 0;
    }

    private boolean isDead(PlayerFarmingSave save) {
        return save.isWatered() && save.isDiseased();
    }

    private boolean isDead(FarmingConfig config) {
        return config.isWatered() == 1 && config.isDiseased() == 1;
    }

    private boolean isSuccessful(FarmingConfig config) {
        final float roll = random.nextFloat();
        return roll >= this.getBaseSuccessChance(config);
    }

    private boolean isSuccessful(PlayerFarmingSave save) {
        final float roll = random.nextFloat();
        return roll >= this.getBaseSuccessChance(save);
    }

    private double getActionSuccessChance(FarmingConfig config, double bonus) {
        double value = (CropCache.getInstance().read(config.getCrop()).getMinDiseaseRoll() * bonus) / 128;
        return value;
    }

    private double getActionSuccessChance(int cropId, double bonus) {
        double value = (CropCache.getInstance().read(cropId).getMinDiseaseRoll() * bonus) / 128;
        return value;
    }

    private double getBaseSuccessChance(FarmingConfig config) {
        if (config.getCrop() != 0) {
            double bonus = 1;
            if (config.watered()) {
                bonus += 0.2;
            }
            if (config.getCompost() == Farming.COMPOST) {
                bonus += 0.5;
            } else if (config.getCompost() == Farming.SUPERCOMPOST) {
                bonus += 0.8;
            } else if (config.getCompost() == Farming.ULTRACOMPOST) {
                bonus += 0.9;
            }
            return getActionSuccessChance(config, bonus);
        }
        return 0;
    }

    private double getBaseSuccessChance(PlayerFarmingSave config) {
        if (config.getCropId() != 0) {
            double bonus = 1;
            if (config.isWatered()) {
                bonus += 0.2;
            }
            if (config.getCompost() == Farming.COMPOST) {
                bonus += 0.5;
            } else if (config.getCompost() == Farming.SUPERCOMPOST) {
                bonus += 0.8;
            } else if (config.getCompost() == Farming.ULTRACOMPOST) {
                bonus += 0.9;
            }
            return getActionSuccessChance(config.getCropId(), bonus);
        }
        return 0;
    }

    /**
     * disabled herb patches getting diseased to prevent graphical bug
     *
     * @param config
     */
    private void rollForDisease(FarmingConfig config) {
        if (config.getStage() > 0 && config.getStage() < (CropCache.getInstance().read(config.getCrop()).getGrowthCycles() - 1)) {
            if (isSuccessful(config) && config.getType() != PatchType.HERB.ordinal()) {
                config.setDiseased(true);
            }
        }
    }

    private void rollForDisease(PlayerFarmingSave save) {
        if (save.getStage() > 0 && save.getStage() < (CropCache.getInstance().read(save.getCropId()).getGrowthCycles() - 1)) {
            if (isSuccessful(save) && save.getPatchGroupId() != PatchType.HERB.ordinal()) {
                save.setDiseased(true);
            }
        }
    }

    private void updateCropHealthState(FarmingConfig config) {
        if (config.diseased()) {
            config.setWatered(true);
        } else {
            if (config.watered()) {
                config.setWatered(false);
            }
            this.rollForDisease(config);
            config.incrementCycle();
        }
    }

    private void updateCropHealthState(PlayerFarmingSave config) {
        if (config.isDiseased()) {
            config.setWatered(true);
        } else {
            if (config.isWatered()) {
                config.setWatered(false);
            }
            this.rollForDisease(config);
            config.setStage(config.getStage() + 1);
        }
    }

    private void doGrowthCycle(PlayerCharacterContext player, PatchType patchType) {
//        final List<PlayerFarmingSave> farmingSave = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayer(player.getId());
//        if (farmingSave != null) {
//            farmingSave.stream()
//                    .filter(save -> save.getPatchGroupId() == patchType.ordinal())
//                    .filter(save -> !isOvergrown(save)) //filters patches with nothing
//                    .filter(save -> !isDead(save)) //filters patches that are dead
//                    .filter(save -> save.getCropId() != 0)
//                    .filter(save -> save.getStage() < CropCache.getInstance().read(save.getCropId()).getGrowthCycles())
//                    .forEach(farmingConfig -> {
//                        logger.info("Executing Growth Cycle for patch: {}", farmingConfig.getPatchGroupId());
//                        this.updateCropHealthState(farmingConfig);
//                    });
//            farmingSave.forEach(PlayerFarmingSaveDAO.getInstance()::updateFarmingData);
//        }
//        player.getPlayerSaveData().farmingConfig().keySet().forEach(regionId -> {
//            List<FarmingConfig> patches = player.getPlayerSaveData().farmingConfig().get(regionId);
//            patches.stream()
//                    .filter(config -> config.getType() == patchType.ordinal())
//                    .filter(farmingConfig -> !isOvergrown(farmingConfig)) //filters patches with nothing
//                    .filter(farmingConfig -> !isDead(farmingConfig)) //filters patches that are dead
//                    .filter(config -> config.getCrop() != 0)
//                    .filter(farmingConfig -> farmingConfig.getStage() < CropCache.getInstance().read(farmingConfig.getCrop()).getGrowthCycles())
//                    .forEach(farmingConfig -> {
//                        logger.info("Executing Growth Cycle for patch: " + farmingConfig.getPatch());
//                        this.updateCropHealthState(farmingConfig);
//                    });
//        });
    }


    public void doGrowthCycleForOfflinePlayer(PlayerCharacterContext player, PatchType patchType) {
//        this.doGrowthCycle(player, patchType);
//        PlayerCharacterContextDataAccessObject.getInstance().update(player);
    }

    public void doGrowthCycleForOnlinePlayer(Player player, PatchType patchType) {
//        this.doGrowthCycle(player.getContext(), patchType);
//        player.save();
//        int regionX = player.absX >> 3;
//        int regionY = player.absY >> 3;
//        int regionId = ((regionX / 8) << 8) + (regionY / 8);
////        if (player.getContext().getPlayerSaveData().farmingConfig().containsKey(regionId)) {
////            final int varbit = player.getContext().getPlayerSaveData().farmingConfig().get(regionId).stream().mapToInt(FarmingConfig::varbit).sum();
////            player.getPA().sendConfig(529, varbit);
////        }
//        final List<PlayerFarmingSave> farmingSave = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerAndRegion(player.getId(), regionId);
//        if (farmingSave != null) {
//            final List<Integer> bits = farmingSave.stream().map(PlayerFarmingSave::getBits).toList();
//            final int varbit = bits.stream().mapToInt(Integer::intValue).sum();
//            logger.info("Sending farming varbit: {} for region: {}", varbit, regionId);
//            player.getPA().sendConfig(529, varbit);
//        }

    }

    public void executeGrowthCycle() {
        logger.info("Growth Cycle");
        playerPatchStateService.findAll()
                .forEach(this::advanceGrowthStage);
    }

    private boolean rollForDisease(CropState cropState) {
        final int roll = random.nextInt(100);
        logger.info("Rolling for disease on crop: {} with roll: {} and threshold of: {}", new Object[]{cropState.getSeedId(), roll, cropState.getDiseaseSuccessThreshold()});
        return roll >= cropState.getDiseaseSuccessThreshold();
    }

    private void resetWaterState(FarmingPatchState farmingPatchState) {
        logger.info("Resetting water state for patch: {}", farmingPatchState.getStateId());
        if (farmingPatchState.getWatered() == 1) {
            farmingPatchState.setWatered(0);
        }
    }

    private void applyDiseaseToPatch(PlayerPatchState playerPatchState, FarmingPatchState farmingPatchState, CropState cropState) {
        logger.info("Applying disease to patch: {}", playerPatchState.getStateId());

        if (playerPatchState.getPatchId() == 0
                || playerPatchState.getPatchId() == 8
                || playerPatchState.getPatchId() == 16
                || playerPatchState.getPatchId() == 24
                && farmingPatchState.getGrowthStage() < cropState.getStages() - 1
        ) {
            farmingPatchState.setDiseased(1);
        }
        // TODO - Handle patchID 32
    }

    private void killPatch(PlayerPatchState playerPatchState, FarmingPatchState farmingPatchState) {
        logger.info("Killing patch: {}", playerPatchState.getStateId());

        if (playerPatchState.getPatchId() == 0
                || playerPatchState.getPatchId() == 8
                || playerPatchState.getPatchId() == 16
                || playerPatchState.getPatchId() == 24
        ) {
            farmingPatchState.setDiseased(1);
            farmingPatchState.setWatered(1);
        }
        // TODO - Handle patchID 32
    }

    private boolean isPatchDead(PlayerPatchState playerPatchState, FarmingPatchState farmingPatchState) {
        if (playerPatchState.getPatchId() == 0
                || playerPatchState.getPatchId() == 8
                || playerPatchState.getPatchId() == 16
                || playerPatchState.getPatchId() == 24
        ) {
            return farmingPatchState.getDiseased() == 1 && farmingPatchState.getWatered() == 1;
        }
        //TODO - Handle patchID 32
        return false;
    }

    private void advanceGrowthStage(PlayerPatchState playerPatchState) {
        logger.info("Advancing growth stage for patch: {}", playerPatchState.getStateId());
        FarmingPatchState farmingPatchState = farmingPatchStateService.findByStateId(playerPatchState.getStateId());
        if (farmingPatchState != null) {
            if (farmingPatchState.getSeedId() != 0) {
                CropState cropState = cropStateService.findCropStateForSeedId(farmingPatchState.getSeedId());
                if (farmingPatchState.getGrowthStage() < cropState.getStages()) {
                    if (!isPatchDead(playerPatchState, farmingPatchState)) {
                        //Patch is already diseased
                        if (farmingPatchState.getDiseased() == 1) {
                            killPatch(playerPatchState, farmingPatchState);
                        } else if (rollForDisease(cropState)) { // Attempt to disease the patch
                            applyDiseaseToPatch(playerPatchState, farmingPatchState, cropState);
                        }

                        //Always reset watered state
                        resetWaterState(farmingPatchState);
                        //Increment growth stage
                        farmingPatchState.setGrowthStage(farmingPatchState.getGrowthStage() + 1);
                        //Save the updated patch state
                        farmingPatchStateService.save(farmingPatchState);
                    }
                }
            }
        }
    }

    public void sendFarmUpdate(long playerId, int regionId ) {
        logger.info("Sending Farm Update for player: {}, region: {}", playerId, regionId);
        Player player = PlayerHandler.getPlayer(playerId).orElseThrow(() -> new IllegalStateException("Player not found"));
        List<Integer> bits = playerPatchStateService.findAllByPlayerId(playerId).stream()
                .map(patchState -> {
                    FarmingPatchState patch = farmingPatchStateService.findByStateId(patchState.getStateId());
                    int varbit = getVarbit(patch.getWatered(), patch.getDiseased(), patch.getSeedId(), patch.getGrowthStage(), patchState.getPatchId());
                    logger.info("Sending varbit: {} for patch: {}", varbit, patchState.getStateId());
                    return varbit;
                })
                .toList();
        player.getPA().sendConfig(529, bits.stream().mapToInt(Integer::intValue).sum());
    }

    private int getVarbit(int watered, int diseased, int cropId, int stage, int patchGroupId) {
        return (cropId + stage + (watered << 6 | diseased << 7) << patchGroupId);
    }

    public PlayerPatchState getPlayerPatchState(long playerId, int regionId, int patchId) {
        logger.info("Finding PlayerPatchState for player: {}, region: {}, patch: {}", playerId, regionId, patchId);
        return playerPatchStateService.findPlayerPatchStateForPlayer(playerId, regionId, patchId);
    }

    public FarmingPatchState getFarmingPatchState(long stateId) {
        logger.info("Finding FarmingPatchState for stateId: {}", stateId);
        return farmingPatchStateService.findByStateId(stateId);
    }

    public void growWeeds() {
//        PlayerCharacterContextDataAccessObject.getInstance().getAllEntries().forEach(player -> {
//            player.getPlayerSaveData().farmingConfig().keySet().forEach(key -> {
//                player.getPlayerSaveData().farmingConfig().get(key).stream()
//                        .filter(config -> config.getCrop() == 0)
//                        .filter(config -> config.getStage() != 0)
//                        .forEach(config -> {
//                            Optional<Player> playerOptional = PlayerHandler.getPlayer(player.getId());
//                            if (playerOptional.isPresent()) {
//                                Player p = playerOptional.get();
//                                p.getContext().getPlayerSaveData().farmingConfig().get(key).stream()
//                                        .filter(patch -> patch.getPatch() == config.getPatch())
//                                        .findAny().ifPresent(patch -> patch.setStage(patch.getStage() - 1));
//                                p.save();
//                            } else {
//                                config.setStage(config.getStage() - 1);
//                                PlayerCharacterContextDataAccessObject.getInstance().update(player);
//                            }
//                        });
//            });
//        });


        PlayerCharacterContextDataAccessObject.getInstance().getAllEntries().forEach(player -> {
            final List<PlayerFarmingSave> farmingSave = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayer(player.getId());
            if (farmingSave != null) {
                farmingSave.stream()
                        .filter(save -> save.getCropId() == 0)
                        .filter(save -> save.getStage() != 0)
                        .forEach(save -> {
                            save.setStage(save.getStage() - 1);
                        });
            }
        });
    }

    public void grow(PatchType patchType) {
        logger.info("{} Growth Cycle", patchType);
        PlayerCharacterContextDataAccessObject.getInstance().getAllEntries().forEach(player -> {
            if (PlayerHandler.getPlayer(player.getId()).isPresent()) {
                this.doGrowthCycleForOnlinePlayer(PlayerHandler.getPlayer(player.getId()).get(), patchType);
            } else {
                this.doGrowthCycleForOfflinePlayer(player, patchType);
            }
        });
    }

    private void createAllotments() {
        CropDAO.getInstance().create(new Crop(
                5318,
                5,
                1942,
                8,
                9,
                1,
                3,
                3,
                60,
                false,
                new CropPayment(995, 10000),
                PatchType.ALLOTMENT.ordinal(),
                6
        ));

        CropDAO.getInstance().create(new Crop(
                5319,
                5,
                1957,
                10,
                11,
                5,
                3,
                3,
                60,
                false,
                new CropPayment(995, 10000),
                PatchType.ALLOTMENT.ordinal(),
                13
        ));

        CropDAO.getInstance().create(new Crop(
                5324,
                5,
                1965,
                11,
                12,
                7,
                3,
                3,
                60,
                false,
                new CropPayment(995, 10000),
                PatchType.ALLOTMENT.ordinal(),
                20
        ));

        CropDAO.getInstance().create(new Crop( //tomato
                5322,
                5,
                1982,
                13,
                14,
                12,
                3,
                3,
                60,
                false,
                new CropPayment(995, 10000),
                PatchType.ALLOTMENT.ordinal(),
                27
        ));

        CropDAO.getInstance().create(new Crop( //corn
                5320,
                7,
                5986,
                17,
                19,
                20,
                3,
                3,
                60,
                false,
                new CropPayment(995, 10000),
                PatchType.ALLOTMENT.ordinal(),
                34
        ));

        CropDAO.getInstance().create(new Crop( //strawberry
                5323,
                7,
                5504,
                26,
                29,
                31,
                3,
                3,
                60,
                false,
                new CropPayment(995, 10000),
                PatchType.ALLOTMENT.ordinal(),
                43
        ));

        CropDAO.getInstance().create(new Crop( //watermelon
                5321,
                9,
                5982,
                49,
                55,
                47,
                3,
                3,
                60,
                false,
                new CropPayment(995, 10000),
                PatchType.ALLOTMENT.ordinal(),
                52
        ));
    }


    private FarmController() {
        this.random = new Random();
        this.farmingPatchStateService = new FarmingPatchStateServiceImpl();
        this.playerPatchStateService = new PlayerPatchStateServiceImpl(farmingPatchStateService);
        this.cropStateService = new CropStateServiceImpl();
        this.createAllotments();

    }

    private final FarmingPatchStateService farmingPatchStateService;
    private final PlayerPatchStateService playerPatchStateService;
    private final CropStateService cropStateService;

    private final Random random;
    private final ScheduledExecutorService growthCycleExecutorService = Executors.newScheduledThreadPool(1);
}
