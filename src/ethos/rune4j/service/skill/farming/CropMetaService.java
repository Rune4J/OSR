package ethos.rune4j.service.skill.farming;

import ethos.rune4j.entity.skill.farming.CropMeta;

public interface CropMetaService {

    CropMeta findBySeedId(int seedId);
}
