package ethos.runehub.entity.player.action.impl.node;

import ethos.model.players.Player;
import ethos.model.players.skills.Skill;
import ethos.rune4j.entity.skill.farming.PatchMetaState;
import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubConstants;
import ethos.runehub.RunehubUtils;
import ethos.runehub.entity.item.ItemInteractionContext;
import ethos.runehub.skill.gathering.farming.action.*;
import ethos.runehub.skill.gathering.woodcutting.action.ActiveWoodcuttingSkillAction;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstClickFarmingPatchAction extends ClickNodeAction {

    private static final Logger logger = LoggerFactory.getLogger(FirstClickFarmingPatchAction.class.getName());

    @Override
    public void perform() {
        logger.info("First Click Farming Patch Action Node: {}, {}, {}", nodeId, nodeX, nodeY);
        logger.info("Clicked patch {}", identifyPatch(nodeX, nodeY));
        PatchMetaState patchMetaState = player.getSkillController().getFarming().getPatchMetaState(player.getContext().getId(), RunehubUtils.getRegionId(nodeX, nodeY), identifyPatch(nodeX, nodeY));
        PatchState patchState = player.getSkillController().getFarming().getPatchState(player.getContext().getId(), RunehubUtils.getRegionId(nodeX, nodeY), identifyPatch(nodeX, nodeY));
        PatchContext patchContext = player.getSkillController().getFarming().buildPatchContext(patchState, patchMetaState);
        if (patchState.getSeedId() == 0 && patchState.getGrowthStage() < 3) { // This would mean the patch has weeds in it
            player.getSkillController().getFarming().train(new RakePatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if (patchContext.getOccupiedById() != 0 && patchContext.getDiseasedState() == 1 && patchContext.getWateredState() == 1) { // This would mean the patch is dead
            player.getSkillController().getFarming().train(new ClearPatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if (patchContext.getDiseasedState() == 1 && patchContext.getWateredState() == 0 && patchContext.getOccupiedById() != 0) { // This is a diseased patch
            player.getSkillController().getFarming().train(new ApplyPlantCureAction(player, new ItemInteractionContext(nodeX, nodeY, player.heightLevel, 6036, nodeId, 1, 1), patchContext)); // 6036 is the plant cure item id
        } else if ( //This is an allotment patch ready to be harvested
                isPatchReadyToHarvest(patchContext) && (patchContext.getPatchLocationId() == 0 || patchContext.getPatchLocationId() == 8)
        ) {
            player.getSkillController().getFarming().train(new HarvestAllotmentPatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if ( //This is a flower patch ready to be harvested
                isPatchReadyToHarvest(patchContext) && (patchContext.getPatchLocationId() == 16)
        ) {
            player.getSkillController().getFarming().train(new HarvestFlowerPatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if ( //This is an herb patch ready to be harvested
                isPatchReadyToHarvest(patchContext) && (patchContext.getPatchLocationId() == 24)
        ) {
            player.getSkillController().getFarming().train(new HarvestHerbPatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if (
                isPatchReadyToHarvest(patchContext)
                        && (patchContext.getPatchLocationId() == 32
                            && patchContext.getCurrentGrowthStage() == 26)
        ) {
            player.getSkillController().getFarming().train(new CheckFruitTreeHealth(player, patchContext));
        } else if (
                isPatchReadyToHarvest(patchContext)
                        && patchContext.getPatchLocationId() == 32
                        && patchContext.getCurrentGrowthStage() > 6 && patchContext.getCurrentGrowthStage() <= 10
                        && patchContext.getOccupiedById() >= 5283 && patchContext.getOccupiedById() <= 5289
        ) { //This is a fruit tree patch ready to be harvested
            player.getSkillController().getFarming().train(new HarvestFruitTreePatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if (
                isPatchReadyToHarvest(patchContext)
                        && patchContext.getPatchLocationId() == 32
                        && patchContext.getCurrentGrowthStage() == 6
        ) { //This is a fruit tree patch ready to be chopped down
            player.getSkillController().getFarming().train(new ChopDownFruitTreeAction(player, nodeId, nodeX, nodeY, player.heightLevel, patchContext));
        } else if (
                isPatchReadyToHarvest(patchContext)
                        && patchContext.getPatchLocationId() == 32
                        && patchContext.getCurrentGrowthStage() == 25
        ) { //This is a fruit tree patch is a stump and ready to be cleared
            player.getSkillController().getFarming().train(new ClearPatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if (
                isPatchReadyToHarvest(patchContext)
                        && patchContext.getPatchLocationId() == 32
                        && patchContext.getCurrentGrowthStage() == patchContext.getMaturityStage()
                && patchContext.getOccupiedById() >=  5312
                && patchContext.getOccupiedById() <=  5316
        ) {
            player.getSkillController().getFarming().train(new CheckTreeHealth(player, patchContext));
        } else if (
                isPatchReadyToHarvest(patchContext)
                        && patchContext.getPatchLocationId() == 32
                        && patchContext.getCurrentGrowthStage() == patchContext.getMaturityStage() + 1
                        && patchContext.getOccupiedById() >=  5312
                        && patchContext.getOccupiedById() <=  5316
        ) {
            player.getSkillController().getFarming().train(new ChopDownTreePatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if (
                        patchContext.getCurrentGrowthStage() >= patchContext.getMaturityStage() - 3 //bushes have 4 harvestable stages 1 for each berry
                        && patchContext.getPatchLocationId() == 32
                        && patchContext.getOccupiedById() >=  5101
                        && patchContext.getOccupiedById() <=  5105
        ) {
            player.getSkillController().getFarming().train(new HarvestBushPatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else if (
                patchContext.getCurrentGrowthStage() == patchContext.getMaturityStage() - 4 //bushes have 4 harvestable stages 1 for each berry
                        && patchContext.getPatchLocationId() == 32
                        && patchContext.getOccupiedById() >=  5101
                        && patchContext.getOccupiedById() <=  5105
        ) {
            player.getSkillController().getFarming().train(new ClearPatchAction(player, nodeId, nodeX, nodeY, patchContext));
        } else {
            logger.info("Current patch state: {}", patchState);
            logger.info("Current patch meta state: {}", patchMetaState);
            logger.info("Current patch context: {}", patchContext);
            player.sendMessage("This patch is not ready to be harvested yet.");
        }
//        PatchMetaState patchMetaState = player.getSkillController().getFarming().getPatchMetaState(player.getContext().getId(), RunehubUtils.getRegionId(nodeX, nodeY),0);
    }

    private boolean isPatchReadyToHarvest(PatchContext patchContext) {
        return patchContext.getOccupiedById() != 0
                && patchContext.getCurrentGrowthStage() >= patchContext.getMaturityStage()
                && patchContext.getWateredState() == 0
                && patchContext.getDiseasedState() == 0;
    }

    private int identifyPatch(int x, int y) {
        if (RunehubConstants.CANIFIS_FARM.contains(new Point(x, y))) {
            return findPatch10x10(getNormalizedX(x, RunehubConstants.CANIFIS_FARM), getNormalizedY(y, RunehubConstants.CANIFIS_FARM));
        } else if (RunehubConstants.CATHERBY_FARM.contains(new Point(x, y))) {
            return findPatch11x11(getNormalizedX(x, RunehubConstants.CATHERBY_FARM), getNormalizedY(y, RunehubConstants.CATHERBY_FARM));
        } else if (RunehubConstants.FALADOR_FARM.contains(new Point(x, y))) {
            return findPatch10x10(getNormalizedX(x, RunehubConstants.FALADOR_FARM), getNormalizedY(y, RunehubConstants.FALADOR_FARM));
        } else if (RunehubConstants.ARDOUGNE_FARM.contains(new Point(x, y))) {
            return findPatch11x11(getNormalizedX(x, RunehubConstants.ARDOUGNE_FARM), getNormalizedY(y, RunehubConstants.ARDOUGNE_FARM));
        }
        return 32;
    }

    private int getNormalizedX(int x, Rectangle farm) {
        // Calculate normalized position in the grid (relative to the SW corner of the patch)
        int normalizedX = (x - farm.getSouthWestPoint().getX());

        // If the result of the modulo operation is negative (which can happen with negative offsets),
        // add the grid size to ensure the result is always positive
        if (normalizedX < 0) normalizedX += farm.getLength();
        return normalizedX;
    }

    private int getNormalizedY(int y, Rectangle farm) {
        // Calculate normalized position in the grid (relative to the SW corner of the patch)
        int normalizedY = (y - farm.getSouthWestPoint().getY());

        // If the result of the modulo operation is negative (which can happen with negative offsets),
        // add the grid size to ensure the result is always positive
        if (normalizedY < 0) normalizedY += farm.getWidth();
        return normalizedY;
    }


    private int findPatch10x10(int x, int y) {
        logger.info("Finding patch 10x10 at {}, {}", x, y);
        // Flower Patch in 10x10: centered at (5,5)
        if (x >= 4 && x <= 5 && y >= 4 && y <= 5) {
            return 16;
        }

        // North Allotment in 10x10: Top-left L-shape
        if ((x <= 1 && y >= 4) || (x <= 4 && y >= 8)) {
            return 0;
        }

        // South Allotment in 10x10: Bottom-right L-shape
        if ((x >= 5 && y <= 1) || (x >= 8 && y <= 5)) {
            return 8;
        }

        // Herb Patch in 10x10: Top-right position
        if (x >= 8 && y >= 8) {
            return 24;
        }

        return 32;
    }

    private int findPatch11x11(int x, int y) {
        // Flower Patch in 11x11: centered at (5,5)
        if (x >= 4 && x <= 5 && y >= 4 && y <= 5) {
            return 16;
        }

        // North Allotment in 11x11: Top-left L-shape, adjusted for larger grid
        if ((x <= 9 && y >= 8) || (x <= 1 && y == 7)) {
            return 0;
        }

        // South Allotment in 11x11: Bottom-right L-shape
        if ((x >= 0 && y <= 1) || (x <= 1 && y == 2)) {
            return 8;
        }

        // Herb Patch in 11x11: Top-right position
        if (x >= 8 && y <= 5) {
            return 24;
        }

        return 32;
    }

    public FirstClickFarmingPatchAction(Player player, int nodeX, int nodeY, int nodeId) {
        super(player, nodeX, nodeY, nodeId);
    }
}
