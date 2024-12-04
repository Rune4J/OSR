package ethos.rune4j.service.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.CropState;
import ethos.rune4j.repository.skill.farming.CropStateRepository;
import ethos.rune4j.repository.skill.farming.impl.CropStateRepositoryImpl;
import ethos.rune4j.service.skill.farming.CropStateService;

public class CropStateServiceImpl implements CropStateService {
    @Override
    public CropState findBySeedId(int seedId) {
        return cropStateRepository.findBySeedId(seedId);
    }

    public CropStateServiceImpl() {
        this.cropStateRepository = new CropStateRepositoryImpl();
    }

    private final CropStateRepository cropStateRepository;
}
