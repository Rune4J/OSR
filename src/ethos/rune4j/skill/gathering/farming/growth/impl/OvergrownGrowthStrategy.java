package ethos.rune4j.skill.gathering.farming.growth.impl;

import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.model.dto.skill.farming.PatchContext;

/**
 * Overgrown is for patches with weeds in them or nothing in them and can grow weeds
 */
public class OvergrownGrowthStrategy extends StandardGrowthStrategy {

    @Override
    public void advanceGrowthStage(int growthStage) {
        if (!isMature()) {
            context.setCurrentGrowthStage(context.getCurrentGrowthStage() - growthStage);
        }
    }

    @Override
    protected boolean isMature() {
        return context.getCurrentGrowthStage() == 0; // Weeds grow in reverse 0 is mature full patch 3 is empty patch
    }

    public OvergrownGrowthStrategy(PatchContext context) {
        super(context);
    }

}
