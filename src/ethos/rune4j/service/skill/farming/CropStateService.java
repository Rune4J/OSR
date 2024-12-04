package ethos.rune4j.service.skill.farming;

import ethos.rune4j.entity.skill.farming.CropState;

public interface CropStateService {

    CropState findBySeedId(int seedId);
}
