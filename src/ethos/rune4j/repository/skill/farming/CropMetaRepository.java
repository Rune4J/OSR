package ethos.rune4j.repository.skill.farming;

import ethos.rune4j.entity.skill.farming.CropMeta;

import java.util.Optional;

public interface CropMetaRepository {

    Optional<CropMeta> findBySeedId(int seedId);
}
