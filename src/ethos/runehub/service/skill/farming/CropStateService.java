package ethos.runehub.service.skill.farming;

import ethos.rune4j.entity.skill.farming.CropState;

public interface CropStateService {

    CropState findCropStateForSeedId(int seedId);
}
