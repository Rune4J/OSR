package ethos.rune4j.skill.gathering.farming.growth;

import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.rune4j.skill.gathering.farming.growth.impl.*;

public class GrowthStrategyFactory {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GrowthStrategyFactory.class.getName());

    public static GrowthStrategy getStrategy(PatchContext context) {
        logger.debug("Creating growth strategy for patch {}", context);
        if (isPatchOvergrown(context)) {
            logger.debug("Creating overgrown growth strategy for patch {}", context);
            return new OvergrownGrowthStrategy(context);
        } else if (context.getPatchLocationId() == 0 || context.getPatchLocationId() == 8) {
            logger.debug("Creating allotment growth strategy for patch {}", context);
            return new AllotmentGrowthStrategy(context);
        } else if (context.getPatchLocationId() == 16) {
            logger.debug("Creating flower growth strategy for patch {}", context);
            return new FlowerGrowthStrategy(context);
        } else if (context.getPatchLocationId() == 24) {
            logger.debug("Creating herb growth strategy for patch {}", context);
            return new HerbGrowthStrategy(context);
        } else if ( // We're just going to fudge this and hardcode fruit tree seed ids and any other special cases
                context.getOccupiedById() >= 5283
                        && context.getOccupiedById() <= 5289

        ) {
            return new FruitTreeGrowthStrategy(context);
        } else if (
                context.getOccupiedById() >= 5312
                        && context.getOccupiedById() <= 5316

        ) { // this is for the tree patch

        return new TreeGrowthStrategy(context);
        } else if (
                context.getOccupiedById() >= 5101
                        && context.getOccupiedById() <= 5105

        ) { // this is for the bush patch

            return new BushGrowthStrategy(context);
        } else {
            logger.debug("Creating special growth strategy for patch {}", context);
            return new SpecialGrowthStrategy(context);
        }
    }

    private static boolean isPatchOvergrown(PatchContext context) {
        return (context.getCurrentGrowthStage() <= 3 && context.getCurrentGrowthStage() >= 0) && context.getOccupiedById() == 0;
    }

}
