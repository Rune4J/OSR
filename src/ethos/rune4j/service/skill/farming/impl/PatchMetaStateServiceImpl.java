package ethos.rune4j.service.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.PatchMetaState;
import ethos.rune4j.repository.skill.farming.PatchMetaStateRepository;
import ethos.rune4j.service.skill.farming.PatchMetaStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class PatchMetaStateServiceImpl implements PatchMetaStateService {

    private static final Logger logger = LoggerFactory.getLogger(PatchMetaStateServiceImpl.class.getName());

    @Override
    public PatchMetaState findPatchMetaStateForPlayerInRegionAtPatch(long playerId, int regionId, int patchId) {
        logger.debug("Finding PatchMetaState by playerId: {}, regionId: {}, patchId: {}", playerId, regionId, patchId);
        Optional<PatchMetaState> patchMetaState = patchMetaStateRepository.findPatchMetaState(playerId, regionId, patchId);
        logger.debug("Found PatchMetaState: {}", patchMetaState);
        return patchMetaState.orElse(null);
    }

    @Override
    public List<PatchMetaState> findAllPatchMetaStateForPlayerInRegion(long playerId, int regionId) {
        logger.debug("Finding all PatchMetaState for player: {}, region: {}", playerId, regionId);
        List<PatchMetaState> patchMetaStates = patchMetaStateRepository.findAllPatchMetaStateForPlayerInRegion(playerId, regionId);
        logger.debug("Found PatchMetaStates: {}", patchMetaStates.size());
        return patchMetaStates;
    }

    @Override
    public PatchMetaState findByStateId(long stateId) {
        logger.debug("Finding PatchMetaState by stateId: {}", stateId);
        Optional<PatchMetaState> patchMetaState = patchMetaStateRepository.findByStateId(stateId);
        logger.debug("Found PatchMetaState: {}", patchMetaState);
        return patchMetaState.orElseThrow(() -> new RuntimeException("PatchMetaState not found"));
    }

    @Override
    public PatchMetaState save(PatchMetaState patchMetaState) {
        logger.debug("Saving PatchMetaState: {}", patchMetaState);
        PatchMetaState existingPatchMetaState = findPatchMetaStateForPlayerInRegionAtPatch(patchMetaState.getPlayerId(), patchMetaState.getRegionId(), patchMetaState.getPatchLocation());
        if (existingPatchMetaState != null) {
            logger.debug("PatchMetaState already exists: {}", existingPatchMetaState);
            return patchMetaStateRepository.update(patchMetaState);
        }
        return patchMetaStateRepository.save(patchMetaState);
    }

    public PatchMetaStateServiceImpl(PatchMetaStateRepository patchMetaStateRepository) {
        this.patchMetaStateRepository = patchMetaStateRepository;
    }

    private final PatchMetaStateRepository patchMetaStateRepository;
}
