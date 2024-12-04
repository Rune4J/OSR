package ethos.runehub.service.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.CropState;
import ethos.rune4j.repository.skill.farming.impl.CropStateRepositoryImpl;
import ethos.runehub.service.skill.farming.CropStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CropStateServiceImpl implements CropStateService {

    private static final Logger logger = LoggerFactory.getLogger(CropStateServiceImpl.class.getName());

    @Override
    public CropState findCropStateForSeedId(int seedId) {
        logger.info("Finding CropState for seedId: {}", seedId);
        return cropStateRepository.findBySeedId(seedId);
    }

    private final CropStateRepositoryImpl cropStateRepository = new CropStateRepositoryImpl();
}
