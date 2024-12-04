package ethos.rune4j.service.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.CropMeta;
import ethos.rune4j.repository.skill.farming.CropMetaRepository;
import ethos.rune4j.repository.skill.farming.impl.CropMetaRepositoryImpl;
import ethos.rune4j.service.skill.farming.CropMetaService;

public class CropMetaServiceImpl implements CropMetaService {
    @Override
    public CropMeta findBySeedId(int seedId) {
        return cropMetaRepository.findBySeedId(seedId).orElseThrow(() -> new IllegalArgumentException("CropMeta not found for seedId: " + seedId));
    }

    public CropMetaServiceImpl() {
        this.cropMetaRepository = new CropMetaRepositoryImpl();
    }

    private final CropMetaRepository cropMetaRepository;
}
