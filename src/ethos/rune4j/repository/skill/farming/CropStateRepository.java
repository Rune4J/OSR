package ethos.rune4j.repository.skill.farming;

import ethos.rune4j.entity.skill.farming.CropState;

public interface CropStateRepository {

    CropState findBySeedId(int seedId);
}
