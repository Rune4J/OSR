package ethos.rune4j.repository.skill.farming;

import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.repository.Rune4JRepository;

import java.util.Optional;

/**
 * Repository for PatchState
 */
public interface PatchStateRepository extends Rune4JRepository<PatchState> {



    /**
     * Find PatchState by id
     * @param patchStateId - the patch state id
     * @return PatchState
     */
    Optional<PatchState> findPatchStateById(long patchStateId);

    /**
     * Save PatchState
     * @param patchState - the PatchState
     * @return PatchState
     */
    PatchState save(PatchState patchState);

    /**
     * Update PatchState
     * @param patchState - the PatchState
     * @return PatchState
     */
    PatchState update(PatchState patchState);
}
