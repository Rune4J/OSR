package ethos.runehub.service.skill.farming;

import ethos.runehub.entity.skill.farming.FarmingPatchState;

/**
 * Service for interacting with FarmingPatchState entities
 */
public interface FarmingPatchStateService {

    /**
     * Find a FarmingPatchState by stateId
     * @param stateId
     * @return FarmingPatchState
     */
    FarmingPatchState findByStateId(long stateId);

    /**
     * Save a FarmingPatchState
     * @param farmingPatchState
     */
    void save(FarmingPatchState farmingPatchState);

    /**
     * Update a FarmingPatchState
     * @param farmingPatchState
     */
    void update(FarmingPatchState farmingPatchState);

}
