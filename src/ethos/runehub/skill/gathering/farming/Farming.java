package ethos.runehub.skill.gathering.farming;

import ethos.model.players.Player;
import ethos.rune4j.entity.skill.farming.CropMeta;
import ethos.rune4j.entity.skill.farming.PatchMetaState;
import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.rune4j.repository.skill.farming.impl.PatchMetaStateRepositoryImpl;
import ethos.rune4j.repository.skill.farming.impl.PatchStateRepositoryImpl;
import ethos.rune4j.service.skill.farming.CropMetaService;
import ethos.rune4j.service.skill.farming.CropStateService;
import ethos.rune4j.service.skill.farming.PatchMetaStateService;
import ethos.rune4j.service.skill.farming.PatchStateService;
import ethos.rune4j.service.skill.farming.impl.CropMetaServiceImpl;
import ethos.rune4j.service.skill.farming.impl.CropStateServiceImpl;
import ethos.rune4j.service.skill.farming.impl.PatchMetaStateServiceImpl;
import ethos.rune4j.service.skill.farming.impl.PatchStateServiceImpl;
import ethos.rune4j.skill.gathering.farming.growth.GrowthStrategy;
import ethos.rune4j.skill.gathering.farming.growth.GrowthStrategyFactory;
import ethos.runehub.RunehubConstants;
import ethos.runehub.RunehubUtils;
import ethos.runehub.entity.player.PlayerFarmingSave;
import ethos.runehub.entity.player.PlayerFarmingSaveDAO;
import ethos.rune4j.entity.skill.farming.CropState;
import ethos.runehub.skill.SkillAction;
import ethos.runehub.skill.gathering.GatheringSkill;
import ethos.runehub.skill.gathering.tool.GatheringTool;
import ethos.runehub.skill.gathering.tool.GatheringToolLoader;
import ethos.runehub.skill.node.context.impl.GatheringNodeContext;
import org.runehub.api.util.IDManager;
import org.runehub.api.util.SkillDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Interaction process
 * 1. Use seeds on patch (provides seedId, x, y, z)
 * 2. return patch using x,y,z
 * 3. perform checks to validate seeds and patch and level
 * 4. create planted objectId in patch
 * 5. plant does growth check every farm tick to determine if it grows, gets sick, or dies
 * 6. gets objectId and chance data from crop database using seedId
 * 7. plant grows completely player interacts with like typical gathering node
 * <p>
 * IDs
 * 8150-8153 fully grown weeds in herb patch (8152 = ardougne test patch)
 * 8573-8576 weed cycles for falador allotment patch
 * 8550 - 8557 fully grown allotment weeds
 * 8558 - 8562 potato growth stages unaltered
 * 8563 - 8566 potato growth stages with water
 * 8567 -8569 potato diseased growth stages
 * 8570 - 8572 potato death growth stages
 * 8573 empty allotment
 * 8574 stage 1 weeds allotment
 * 8575 stage 2 weeds allotment
 * 8576 stage 3 weeds allotment
 * 8577-8579 weed growth stages allotment
 * 7557-7560 belladonna weed growth stages
 * 7576-7580 fully grown bush patch weeds
 * 7575 stage 2 bush patch weeds
 * 7574 stage 1 bush patch weeds
 * 7573 empty bush patch
 * 8139-8143 herb growth stages
 * 8136-8138 herb patch weeds stages //dark
 * 8133-8135 herb patch weed stages //default
 * 8132 empty herb patch
 */
public class Farming extends GatheringSkill {

    private static final Logger logger = LoggerFactory.getLogger(Farming.class.getName());

    public static final int COMPOST = 6032;
    public static final int SUPERCOMPOST = 6034;
    public static final int ULTRACOMPOST = 7622;
    public static final int BOTTOMLESS_COMPOST = 7624;

    public Optional<FarmingConfig> getConfig(int nodeId, int regionId) {
        switch (nodeId) {
            case 8554:
            case 8552:
            case 8556:
            case 8550://n falador allotment
                return Optional.of(this.getPlayer().getContext().getPlayerSaveData().farmingConfig().get(regionId).stream()
                        .filter(config -> config.getPatch() == 0).findAny().orElseThrow(() -> new IllegalArgumentException("No Such Farm")));
            case 8555:
            case 8553:
            case 8557:
            case 8551: //s falador allotment
                return Optional.of(this.getPlayer().getContext().getPlayerSaveData().farmingConfig().get(regionId).stream()
                        .filter(config -> config.getPatch() == 8).findAny().orElseThrow(() -> new IllegalArgumentException("No Such Farm")));
            case 7849:
            case 7848:
            case 7850:
            case 7847:
                return Optional.of(this.getPlayer().getContext().getPlayerSaveData().farmingConfig().get(regionId).stream()
                        .filter(config -> config.getPatch() == 16).findAny().orElseThrow(() -> new IllegalArgumentException("No Such Farm")));
            case 8152:
            case 8151:
            case 8153:
            case 8150:
                return Optional.of(this.getPlayer().getContext().getPlayerSaveData().farmingConfig().get(regionId).stream()
                        .filter(config -> config.getPatch() == 24).findAny().orElseThrow(() -> new IllegalArgumentException("No Such Farm")));
            case 8175: //hops
            case 8176:
            case 8173:
            case 8174:
            case 7577: //bush
            case 7578:
            case 7580:
            case 7579:
            case 8391: // tree
            case 8390:
            case 8389:
            case 8388:
            case 19147:
            case 7962: //fruit tree
            case 7965:
            case 7963:
            case 7964:
            case 26579:

                return Optional.of(this.getPlayer().getContext().getPlayerSaveData().farmingConfig().get(regionId).stream()
                        .filter(config -> config.getPatch() == 32).findAny().orElseThrow(() -> new IllegalArgumentException("No Such Farm")));
        }
        return Optional.empty();
    }

    private double getCompostYieldBonus(int compost) {
        switch (compost) {
            case COMPOST:
                return 1.2;
            case SUPERCOMPOST:
                return 1.4;
            case ULTRACOMPOST:
                return 1.6;
        }
        return 1.0;
    }

    public double getHarvestChanceBonus() {
        double bonus = 1;
        return bonus;
    }

    public double getHarvestMinMaxBonus(FarmingConfig config) {
        return this.getCompostYieldBonus(config.getCompost());
    }

    public int getHarvestedAmount(GatheringNodeContext<?> context, FarmingConfig config) {
        final int regionId = RunehubUtils.getRegionId(context.getX(), context.getY());
        if (PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerRegionAndPatch(this.getPlayer().getId(), regionId, config.getPatch()) == null) {
            throw new NullPointerException("No such farming data.");
        }
        return PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerRegionAndPatch(this.getPlayer().getId(), regionId, config.getPatch()).getHarvested();
//        return this.getPlayer().getContext().getPlayerSaveData().getHarvestMap().computeIfAbsent(regionId, key -> {
//                    final HashMap<Integer, AdjustableInteger> patchMap = new HashMap<>();
//                    patchMap.put(config.getType(), new AdjustableInteger(0));
//                    return patchMap;
//                })
//                .computeIfAbsent(config.getType(), key -> new AdjustableInteger(0)).value();
    }

    public void updateHarvestCounts(GatheringNodeContext<?> context, FarmingConfig config) {
        final int regionId = RunehubUtils.getRegionId(context.getX(), context.getY());
        final PlayerFarmingSave save = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerRegionAndPatch(this.getPlayer().getId(), regionId, config.getPatch());
        if (save == null) {
            throw new NullPointerException("No such farming data.");
        } else {
            save.setHarvested(save.getHarvested() + 1);
            PlayerFarmingSaveDAO.getInstance().updateFarmingData(save);
        }
//        this.getPlayer().getContext().getPlayerSaveData().getHarvestMap().computeIfAbsent(regionId, key -> {
//                    final HashMap<Integer, AdjustableInteger> patchMap = new HashMap<>();
//                    patchMap.put(config.getType(), new AdjustableInteger(0));
//                    return patchMap;
//                }).computeIfAbsent(config.getType(), key -> new AdjustableInteger(0)).increment();
    }

    public void resetHarvestCounts(GatheringNodeContext<?> context, FarmingConfig config) {
        final int regionId = RunehubUtils.getRegionId(context.getX(), context.getY());
        final PlayerFarmingSave save = PlayerFarmingSaveDAO.getInstance().selectFarmingDataByPlayerRegionAndPatch(this.getPlayer().getId(), regionId, config.getPatch());
        if (save == null) {
            throw new NullPointerException("No such farming data.");
        } else {
            save.setHarvested(0);
            PlayerFarmingSaveDAO.getInstance().updateFarmingData(save);
        }
//        this.getPlayer().getContext().getPlayerSaveData().getHarvestMap().computeIfAbsent(regionId, key -> {
//                    final HashMap<Integer, AdjustableInteger> patchMap = new HashMap<>();
//                    patchMap.put(config.getType(), new AdjustableInteger(0));
//                    return patchMap;
//                })
//                .computeIfAbsent(config.getType(), key -> new AdjustableInteger(0)).setValue(0);
    }

    /**
     * Advance growth stage by a certain amount
     *
     * @param stages         - the amount of stages to advance
     * @param patchLocation  - the patch location id to advance
     * @param overgrowthOnly - whether to only advance overgrown patches
     */

    public void advanceGrowthStage(int stages, int patchLocation, boolean overgrowthOnly) {
        logger.debug("Advancing growth stage by: {} at patch: {}", stages, patchLocation);
        List<PatchMetaState> patchesInFarm = patchMetaStateService.findAllPatchMetaStateForPlayerInRegion(
                this.getPlayer().getContext().getId(),
                RunehubUtils.getRegionId(this.getPlayer().getX(), this.getPlayer().getY())
        );

        List<PatchState> patchStates = patchesInFarm.stream()
                .map(patchMetaState -> patchStateService.findPatchStateById(patchMetaState.getPatchStateId()))
                .filter(patchState -> overgrowthOnly ? (patchState.getSeedId() == 0 && patchState.getPatchLocation() == patchLocation) : patchState.getPatchLocation() == patchLocation)
                .toList();

        patchStates.forEach(patchState -> {
            PatchMetaState patchMetaState = patchMetaStateService.findPatchMetaStateForPlayerInRegionAtPatch(
                    this.getPlayer().getContext().getId(),
                    RunehubUtils.getRegionId(this.getPlayer().getX(), this.getPlayer().getY()),
                    patchState.getPatchLocation()
            );

            PatchContext patchContext = buildPatchContext(patchState, patchMetaState);
            GrowthStrategy strategy = GrowthStrategyFactory.getStrategy(patchContext);
            strategy.advanceGrowthStage(1);

            updatePatchStateWithPatchContext(patchState, patchContext);
            updatePatchMetaStateWithPatchContext(patchMetaState, patchContext);

            savePatchState(patchState);
        });

        updateFarm(RunehubUtils.getRegionId(this.getPlayer().getX(), this.getPlayer().getY()));
    }

    public void advanceGrowthStage(int stages, int patchLocation) {
        advanceGrowthStage(stages, patchLocation, false);
    }

    public PatchContext buildPatchContext(PatchState patchState, PatchMetaState patchMetaState) {
        PatchContext patchContext = new PatchContext();
        patchContext.setOccupiedById(patchState.getSeedId());
        patchContext.setDiseasedState(patchState.getDiseased());
        patchContext.setWateredState(patchState.getWatered());
        patchContext.setCurrentGrowthStage(patchState.getGrowthStage());
        patchContext.setCompostId(patchMetaState.getCompostState());
        patchContext.setPatchLocationId(patchState.getPatchLocation());
        patchContext.setPatchProtectedState(patchMetaState.getProtectedState());
        patchContext.setHarvestTime(patchMetaState.getHarvestTime());
        patchContext.setPlantTime(patchMetaState.getPlantTime());
        patchContext.setHarvested(patchMetaState.getHarvestedCount());
        if (patchState.getSeedId() != 0) {
            patchContext.setMaturityStage(getCropState(patchState.getSeedId()).getStages());
            patchContext.setDiseaseChance(getCropState(patchState.getSeedId()).getDiseaseSuccessThreshold());
        }
        return patchContext;
    }

    private void updatePatchStateWithPatchContext(PatchState patchState, PatchContext patchContext) {
        patchState.setDiseased(patchContext.getDiseasedState());
        patchState.setWatered(patchContext.getWateredState());
        patchState.setGrowthStage(patchContext.getCurrentGrowthStage());
        patchState.setSeedId(patchContext.getOccupiedById());
    }

    private void updatePatchMetaStateWithPatchContext(PatchMetaState patchMetaState, PatchContext patchContext) {
        patchMetaState.setCompostState(patchContext.getCompostId());
        patchMetaState.setProtectedState(patchContext.getPatchProtectedState());
        patchMetaState.setHarvestTime(patchContext.getHarvestTime());
        patchMetaState.setPlantTime(patchContext.getPlantTime());
        patchMetaState.setHarvestedCount(patchContext.getHarvested());
    }


    private void savePatchState(PatchState patchState) {
        patchStateService.save(patchState);
    }

    private void savePatchMetaState(PatchMetaState patchMetaState) {
        patchMetaStateService.save(patchMetaState);
    }

    public void savePatchContext(PatchContext context) {
        PatchMetaState patchMetaState = patchMetaStateService.findPatchMetaStateForPlayerInRegionAtPatch(
                this.getPlayer().getContext().getId(),
                RunehubUtils.getRegionId(this.getPlayer().getX(), this.getPlayer().getY()),
                context.getPatchLocationId()
        );
        PatchState patchState = patchStateService.findPatchStateById(patchMetaState.getPatchStateId());
        updatePatchStateWithPatchContext(patchState, context);
        updatePatchMetaStateWithPatchContext(patchMetaState, context);
        savePatchState(patchState);
        savePatchMetaState(patchMetaState);
    }

    public CropState getCropState(int seedId) {
        return cropStateService.findBySeedId(seedId);
    }

    public void updateFarm(int regionId) {
        List<PatchMetaState> patchesInFarm = patchMetaStateService.findAllPatchMetaStateForPlayerInRegion(this.getPlayer().getContext().getId(), regionId);
        List<PatchState> patchStates = patchesInFarm.stream().map(patchMetaState -> patchStateService.findPatchStateById(patchMetaState.getPatchStateId())).toList();
        logger.debug("Patches in farm: {}", patchesInFarm);
        int varbit = 0;
        switch (regionId) { // We're going to fudge this as well and hardcode the region ids for special cases
            case 11317: // fruit tree patch in catherby
                List<Integer> specialPatchVarbits = patchStates.stream().map(this::getVarbitForSpecialCasePatch).toList();
                varbit = specialPatchVarbits.stream().mapToInt(Integer::intValue).sum();
                break;
            default:

                List<Integer> patchVarbits = patchStates.stream().map(this::getVarbitForPatch).toList();
                varbit = patchVarbits.stream().mapToInt(Integer::intValue).sum();

                break;
        }

        logger.debug("Varbit: {}", varbit);
        this.getPlayer().getPA().sendConfig(529, varbit);

    }

    /**
     * Special cases like fruit trees and cacti do not follow the standard growth calculations
     * The varbit for these patches is crop index + growth stage
     * Diseased/Dead states have their own crop index
     * To make this as painless as possible we're going to treat the watered/diseased states the same way, but instead of bitshifting we're going to add an offset
     *
     * @param patchState
     * @return
     */
    private int getVarbitForSpecialCasePatch(PatchState patchState) {
        int varbit = 0;
        if (patchState.getSeedId() != 0) {
            CropState cropState = cropStateService.findBySeedId(patchState.getSeedId());
            logger.debug("Varbit for special case patch: {}", varbit);
            int diseasedOffset = patchState.getDiseased() == 1 ?
                    varbit
                            + cropState.getStages()
                            + cropState.getMaturityStages()
                    : 0;
            logger.debug("Varbit for special case patch with diseased offset: {}", diseasedOffset);
            int wateredOffset = patchState.getWatered() == 1 ?
                    diseasedOffset + cropState.getStages()
                    : 0;
            logger.debug("Varbit for special case patch with watered offset: {}", wateredOffset);
            varbit = cropState.getCropIndex() + diseasedOffset + wateredOffset + patchState.getGrowthStage();
            logger.debug("Varbit for special case patch with offsets: {}", varbit);
            return varbit;
        }
        return getVarbitForPatch(patchState); //this is for overgrown patches
    }

    private int getVarbitForPatch(PatchState patchState) {
        logger.debug("Getting varbit for patch: {}", patchState);
        int varbit = (patchState.getGrowthStage() + (patchState.getWatered() << 6 | patchState.getDiseased() << 7) << patchState.getPatchLocation());
        if (patchState.getSeedId() != 0)
            varbit = (cropStateService.findBySeedId(patchState.getSeedId()).getCropIndex()
                    + patchState.getGrowthStage()
                    + (patchState.getWatered() << 6 | patchState.getDiseased() << 7) << patchState.getPatchLocation());
        logger.debug("Varbit for patch: {}", varbit);
        return varbit;
    }

    private boolean playerHasPatch(long playerId, int regionId, int patchId) {
        return patchMetaStateService.findPatchMetaStateForPlayerInRegionAtPatch(playerId, regionId, patchId) != null;
    }

    public PatchMetaState getPatchMetaState(long playerId, int regionId, int patchId) {
        if (!playerHasPatch(playerId, regionId, patchId)) {
            return createNewPatchMetaState(playerId, regionId, patchId);
        }
        return patchMetaStateService.findPatchMetaStateForPlayerInRegionAtPatch(playerId, regionId, patchId);
    }

    private PatchMetaState createNewPatchMetaState(long playerId, int regionId, int patchId) {
        // We must ensure for every patch meta state there is a patch state with the same state id
        PatchState patchState = new PatchState();
        patchState.setPatchStateId(IDManager.getUUID());
        patchState.setPatchLocation(patchId);

        PatchMetaState patchMetaState = new PatchMetaState();
        patchMetaState.setPlayerId(playerId);
        patchMetaState.setRegionId(regionId);
        patchMetaState.setPatchLocation(patchId);
        patchMetaState.setPatchStateId(patchState.getPatchStateId());

        patchStateService.save(patchState);
        return patchMetaStateService.save(patchMetaState);
    }

    public PatchState getPatchState(long playerId, int regionId, int patchId) {
        return patchStateService.findPatchStateById(getPatchMetaState(playerId, regionId, patchId).getPatchStateId());
    }

    public int getSeedLevelRequirement(int seedId) {
        return cropMetaService.findBySeedId(seedId).getLevelRequirement();
    }

    public int getSeedsRequiredToPlant(int seedId) {
        return cropMetaService.findBySeedId(seedId).getSeedsPlanted();
    }

    public int getXPFromPlanting(int seedId) {
        return cropMetaService.findBySeedId(seedId).getPlantXp();
    }

    public int getXPFromHarvesting(int seedId) {
        return cropMetaService.findBySeedId(seedId).getHarvestXp();
    }

    public int getXPFromCheckingHealth(int seedId) {
        return cropMetaService.findBySeedId(seedId).getCheckHealthXp();
    }

    public int getHarvestAmountMinimum(int seedId) {
        return cropMetaService.findBySeedId(seedId).getHarvestMinimum();
    }

    public double getBaseHarvestChanceToSave(int seedId) {
        final CropMeta cropMeta = cropMetaService.findBySeedId(seedId);
        return getActionSuccessChance(cropMeta.getCtsMin(), cropMeta.getCtsMax());
    }

    public double getHarvestChanceToSaveWithPower(int seedId) {
        final CropMeta cropMeta = cropMetaService.findBySeedId(seedId);
        return getActionSuccessChance((int) (cropMeta.getCtsMin() * this.getPowerBonus()), (int) (cropMeta.getCtsMax() * this.getPowerBonus()));
    }

    public int getMinimumHarvestAmount(int seedId) {
        return cropMetaService.findBySeedId(seedId).getHarvestMinimum();
    }

    public int getHarvestedItemId(int seedId) {
        return cropMetaService.findBySeedId(seedId).getHarvestedId();
    }

    public int getCropStateIndex(int seedId) {
        return cropStateService.findBySeedId(seedId).getCropIndex();
    }

    public int getHarvestLifeBonus(PatchContext context) {
        int extraHarvestLives = 0;
        extraHarvestLives += getHarvestLifeBonusFromCompost(context.getCompostId());
        return extraHarvestLives;
    }

    private int getHarvestLifeBonusFromCompost(int compost) {
        switch (compost) {
            case COMPOST:
                return 1;
            case SUPERCOMPOST:
                return 2;
            case ULTRACOMPOST:
                return 3;
        }
        return 0;
    }

    @Override
    public void train(SkillAction skillAction) {
        super.train(skillAction);
    }

    @Override
    public GatheringTool getBestAvailableTool() {
        final List<GatheringTool> availableTools = GatheringToolLoader.getInstance().readAll().stream()
                .filter(tool -> tool.getSkillId() == this.getId())
                .filter(tool -> this.getPlayer().getItems().playerHasItem(tool.getItemId()) || this.getPlayer().getItems().isWearingItem(tool.getItemId()))
                .filter(tool -> this.getPlayer().getSkillController().getLevel(this.getId()) >= tool.getLevelRequired())
                .collect(Collectors.toList());
        final int bestToolLevel = availableTools.stream().mapToInt(GatheringTool::getLevelRequired).max().orElse(1);

        return availableTools.stream().filter(tool -> tool.getLevelRequired() == bestToolLevel).findFirst().orElseThrow(() -> new NullPointerException("No available tools."));
    }

    @Override
    public int getId() {
        return SkillDictionary.Skill.FARMING.getId();
    }

    @Override
    protected int getPowerModifier() {
        return 0;
    }

    @Override
    protected int getEfficiencyModifier() {
        return 0;
    }

    @Override
    protected int getGainsModifier() {
        return 0;
    }

    public Farming(Player player) {
        super(player);
        this.patchMetaStateService = new PatchMetaStateServiceImpl(new PatchMetaStateRepositoryImpl());
        this.patchStateService = new PatchStateServiceImpl(new PatchStateRepositoryImpl());
        this.cropStateService = new CropStateServiceImpl();
        this.cropMetaService = new CropMetaServiceImpl();
    }

    private final PatchMetaStateService patchMetaStateService;
    private final PatchStateService patchStateService;
    private final CropStateService cropStateService;
    private final CropMetaService cropMetaService;
}
