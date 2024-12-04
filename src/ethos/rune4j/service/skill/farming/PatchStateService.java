package ethos.rune4j.service.skill.farming;

import ethos.rune4j.entity.skill.farming.PatchState;

public interface PatchStateService {

    /**
     * Find PatchState by id - We'll use this to check if a patch exists
     * @param patchStateId - the patch state id
     * @return PatchState
     */
    PatchState findPatchStateById(long patchStateId);

    /**
     * Save PatchState - We'll use this to insert patches that don't exist and update patches that do
     * @param patchState - the PatchState
     * @return PatchState
     */
    PatchState save(PatchState patchState);
}
