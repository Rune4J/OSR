package ethos.rune4j.skill.gathering.farming.growth.impl;

import ethos.rune4j.model.dto.skill.farming.PatchContext;
// We may be able to delete this if it's not any different from the standard strategy
public class TreeGrowthStrategy extends StandardGrowthStrategy {


    @Override
    protected boolean isMature() {
        return context.getCurrentGrowthStage() >= context.getMaturityStage(); // we do not want to advance if tree has been checked as that is maturity + 1
    }

    public TreeGrowthStrategy(PatchContext context) {
        super(context);
    }
}
