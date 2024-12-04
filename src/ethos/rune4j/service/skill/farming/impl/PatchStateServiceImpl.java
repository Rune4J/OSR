package ethos.rune4j.service.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.repository.skill.farming.PatchStateRepository;
import ethos.rune4j.service.skill.farming.PatchStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PatchStateServiceImpl implements PatchStateService {

    private static final Logger logger = LoggerFactory.getLogger(PatchStateServiceImpl.class.getName());

    @Override
    public PatchState findPatchStateById(long patchStateId) {
        logger.debug("Finding PatchState by id: {}", patchStateId);
        Optional<PatchState> patchState = patchStateRepository.findPatchStateById(patchStateId);
        logger.debug("Found PatchState: {}", patchState);
        return patchState.orElse(null);
    }

    @Override
    public PatchState save(PatchState patchState) {
        logger.debug("Saving PatchState: {}", patchState);
        PatchState existingPatchState = findPatchStateById(patchState.getPatchStateId());
        if (existingPatchState != null) {
            logger.debug("PatchState already exists: {}", existingPatchState);
            return patchStateRepository.update(patchState);
        }
        return patchStateRepository.save(patchState);
    }

    public PatchStateServiceImpl(PatchStateRepository patchStateRepository) {
        this.patchStateRepository = patchStateRepository;
    }

    private final PatchStateRepository patchStateRepository;
}
