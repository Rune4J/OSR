package ethos.rune4j.skill.gathering.farming.growth.impl;

import ethos.rune4j.model.dto.skill.farming.PatchContext;

/**
 * Special growth strategy is used for special patches, bush patches, hops patches, tree patches, fruit tree patches, etc.
 */
public class SpecialGrowthStrategy extends StandardGrowthStrategy {

    public SpecialGrowthStrategy(PatchContext patchState) {
        super(patchState);
    }
}
