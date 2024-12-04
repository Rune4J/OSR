package ethos.runehub.service.skill.farming.impl;

import ethos.runehub.entity.skill.farming.FarmingPatchState;
import ethos.runehub.repository.skill.farming.FarmingPatchStateRepository;
import ethos.runehub.service.skill.farming.FarmingPatchStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FarmingPatchStateServiceImpl implements FarmingPatchStateService {

    private static final Logger logger = LoggerFactory.getLogger(FarmingPatchStateServiceImpl.class.getName());

    @Override
    public FarmingPatchState findByStateId(long stateId) {
        logger.info("Finding FarmingPatchState by stateId: {}", stateId);
        return farmingPatchStateRepository.findByStateId(stateId);
    }

    @Override
    public void save(FarmingPatchState farmingPatchState) {
        logger.info("Saving FarmingPatchState: {}", farmingPatchState);
        farmingPatchStateRepository.save(farmingPatchState);
    }

    @Override
    public void update(FarmingPatchState farmingPatchState) {

    }

    private final FarmingPatchStateRepository farmingPatchStateRepository = new FarmingPatchStateRepository();
}
