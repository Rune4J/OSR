package ethos.rune4j.skill.gathering.farming.growth.impl;

import ethos.rune4j.model.dto.skill.farming.PatchContext;
import ethos.rune4j.skill.gathering.farming.growth.GrowthStrategy;
import ethos.runehub.skill.gathering.farming.Farming;
import ethos.util.Misc;

public abstract class StandardGrowthStrategy implements GrowthStrategy {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StandardGrowthStrategy.class.getName());

    @Override
    public void advanceGrowthStage(int growthStage) {
        logger.info("Advancing growth stage by {} at patch location {}", growthStage, context.getPatchLocationId());
        if (!isMature() && !isDead()) { // Don't grow if already mature or dead
            resetWateredState();
            if (patchIsDiseasedOnAdvancement() && !isPatchDiseased()) {
                applyDisease();
            } else if (isPatchDiseased()) {
                applyDeath();
            } else {
                context.setCurrentGrowthStage(context.getCurrentGrowthStage() + growthStage);

            }
        }
    }

    protected boolean patchIsDiseasedOnAdvancement() {
        int diseaseRoll = Misc.random(100);
        return isPatchEligibleForDisease() && diseaseRoll >= getCropHealthyAdvancementChance();
    }

    protected boolean isPatchEligibleForDisease() {
        return context.getDiseasedState() == 0
                && context.getCurrentGrowthStage() < (context.getMaturityStage() - 1)
                && context.getCurrentGrowthStage() != 0;
    }

    protected double getCropHealthyAdvancementChance() {
        if (context.getOccupiedById() != 0) {
            double bonus = 1.0D;
            if (context.getWateredState() == 1) {
                bonus += 0.2;
            }
            if (context.getCompostId() == Farming.COMPOST) {
                bonus += 0.5;
            } else if (context.getCompostId() == Farming.SUPERCOMPOST) {
                bonus += 0.8;
            } else if (context.getCompostId() == Farming.ULTRACOMPOST) {
                bonus += 0.9;
            }

            double value = context.getDiseaseChance() * bonus;
            logger.info("Chance to advance without disease: {}", value);
            return value;
        }
        return 0;
    }

    protected boolean isPatchDiseased() {
        return context.getDiseasedState() == 1;
    }

    protected boolean isPatchProtected() {
        return context.getPatchProtectedState() == 1;
    }

    protected boolean isMature() {
        return context.getCurrentGrowthStage() == context.getMaturityStage();
    }

    protected boolean isDead() {
        return context.getDiseasedState() == 1 && context.getWateredState() == 1; // All patches except type 32 are dead if diseased and watered
    }

    protected void applyDisease() {
        context.setDiseasedState(1);
    }

    protected void applyDeath() {
        context.setDiseasedState(1);
        context.setWateredState(1);
    }

    protected void resetWateredState() {
        context.setWateredState(0);
    }

    public StandardGrowthStrategy(PatchContext context) {
        this.context = context;
    }

    protected final PatchContext context;
}
