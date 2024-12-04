package ethos.runehub.entity.player.action.impl.node;

import ethos.model.players.Player;
import ethos.rune4j.entity.skill.farming.PatchMetaState;
import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.runehub.RunehubConstants;
import ethos.runehub.RunehubUtils;
import ethos.runehub.entity.item.ItemInteractionContext;
import ethos.runehub.skill.gathering.farming.Farming;
import ethos.runehub.skill.gathering.farming.action.ApplyCompostAction;
import ethos.runehub.skill.gathering.farming.action.ApplyPlantCureAction;
import ethos.runehub.skill.gathering.farming.action.PlantSeedAction;
import ethos.runehub.skill.gathering.farming.action.WaterPatchAction;
import org.runehub.api.util.math.geometry.Point;
import org.runehub.api.util.math.geometry.impl.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemOnPatchAction extends ItemOnNodeAction {

    private static final Logger logger = LoggerFactory.getLogger(ItemOnPatchAction.class.getName());

    @Override
    public void perform() {
        PatchMetaState patchMetaState = player.getSkillController().getFarming().getPatchMetaState(player.getContext().getId(), RunehubUtils.getRegionId(itemInteractionContext.getX(), itemInteractionContext.getY()), identifyPatch(itemInteractionContext.getX(), itemInteractionContext.getY()));
        PatchState patchState = player.getSkillController().getFarming().getPatchState(player.getContext().getId(), RunehubUtils.getRegionId(itemInteractionContext.getX(), itemInteractionContext.getY()), identifyPatch(itemInteractionContext.getX(), itemInteractionContext.getY()));
        PatchContext patchContext = player.getSkillController().getFarming().buildPatchContext(patchState, patchMetaState);
        if (player.getSkillController().getFarming().getCropState(itemInteractionContext.getUsedId()) != null) {
            logger.debug("Planting seed on patch: {}, {}, {}, {}", itemInteractionContext.getUsedId(), itemInteractionContext.getUsedWithId(), itemInteractionContext.getX(), itemInteractionContext.getY());
            player.getSkillController().getFarming().train(new PlantSeedAction(player, itemInteractionContext, patchContext));
        } else if (itemInteractionContext.getUsedId() == 6036) { // 6036 is the plant cure item id
            player.getSkillController().getFarming().train(new ApplyPlantCureAction(player, itemInteractionContext, patchContext));
        } else if (
                itemInteractionContext.getUsedId() == 5340
                        || itemInteractionContext.getUsedId() == 5339
                        || itemInteractionContext.getUsedId() == 5338
                        || itemInteractionContext.getUsedId() == 5337
                        || itemInteractionContext.getUsedId() == 5336
                        || itemInteractionContext.getUsedId() == 5335
                        || itemInteractionContext.getUsedId() == 5334
                        || itemInteractionContext.getUsedId() == 5333
        ) { // Check if the player has any valid watering can
            player.getSkillController().getFarming().train(new WaterPatchAction(player, itemInteractionContext, patchContext));
        } else if (
                itemInteractionContext.getUsedId() == Farming.COMPOST
                        || itemInteractionContext.getUsedId() == Farming.SUPERCOMPOST
                        || itemInteractionContext.getUsedId() == Farming.ULTRACOMPOST
                        || itemInteractionContext.getUsedId() == Farming.BOTTOMLESS_COMPOST
        ) {
            player.getSkillController().getFarming().train(new ApplyCompostAction(player, itemInteractionContext, patchContext));
        }
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

    public ItemOnPatchAction(Player player, ItemInteractionContext itemInteractionContext) {
        super(player, itemInteractionContext);
    }
}
