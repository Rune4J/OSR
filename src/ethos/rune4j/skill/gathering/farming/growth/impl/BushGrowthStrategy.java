package ethos.rune4j.skill.gathering.farming.growth.impl;

import ethos.rune4j.model.dto.skill.farming.PatchContext;

public class BushGrowthStrategy extends StandardGrowthStrategy {

    @Override
    public void advanceGrowthStage(int growthStage) {
        if (!isMature() && !isDead()) { // Don't grow if already mature or dead
            resetWateredState();
            if (patchIsDiseasedOnAdvancement() && !isPatchDiseased()) {
                applyDisease();
            } else if (isPatchDiseased()) {
                applyDeath();
            } else if(context.getCurrentGrowthStage() == context.getMaturityStage() -  5) { // we advance the bush to the mature stage
                context.setCurrentGrowthStage(context.getCurrentGrowthStage() + 5); // bushes fully matured state is +5 from mature
            } else {
                context.setCurrentGrowthStage(context.getCurrentGrowthStage() + growthStage);

            }
        }
    }

    @Override
    protected boolean isPatchEligibleForDisease() {
        return false; // Bushes can't get diseased (for now)
    }

    public BushGrowthStrategy(PatchContext context) {
        super(context);
    }
}
