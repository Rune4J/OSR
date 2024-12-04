package ethos.rune4j.skill.gathering.farming.growth.impl;

import ethos.rune4j.model.dto.skill.farming.PatchContext;

public class FruitTreeGrowthStrategy extends StandardGrowthStrategy {

    @Override
    public void advanceGrowthStage(int growthStage) {
        if (!isMature() && !isDead()) { // Don't grow if already mature or dead
            resetWateredState();
            if (patchIsDiseasedOnAdvancement() && !isPatchDiseased()) {
                applyDisease();
            } else if (isPatchDiseased()) {
                applyDeath();
            } else if(context.getCurrentGrowthStage() == context.getMaturityStage() - 1) { // we advance the tree to the check-health stage
                context.setCurrentGrowthStage(context.getCurrentGrowthStage() + 21); // Growth stage for fruit trees caps at 6 and with how we calculate varbit as stage + initial child index this is effectively (childIndex + 6 + 28)
            } else {
                context.setCurrentGrowthStage(context.getCurrentGrowthStage() + growthStage);

            }
        }
    }

    @Override
    protected boolean isPatchEligibleForDisease() {
        return context.getDiseasedState() == 0
                && context.getCurrentGrowthStage() < (context.getMaturityStage() - 1)
                && context.getCurrentGrowthStage() != 0;
    }

    @Override
    protected boolean isMature() {
        return context.getCurrentGrowthStage() == 10 || context.getCurrentGrowthStage() == 26 || context.getCurrentGrowthStage() == 25; //context.getCurrentGrowthStage() == context.getMaturityStage();
    }

    public FruitTreeGrowthStrategy(PatchContext context) {
        super(context);
    }
}
