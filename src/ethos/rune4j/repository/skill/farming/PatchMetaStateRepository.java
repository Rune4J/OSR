package ethos.rune4j.repository.skill.farming;

import ethos.rune4j.entity.skill.farming.PatchMetaState;
import ethos.rune4j.repository.Rune4JRepository;
import ethos.runehub.RunehubConstants;

import java.util.List;
import java.util.Optional;

/**
 * Repository for PatchMetaState
 */
public interface PatchMetaStateRepository extends Rune4JRepository<PatchMetaState> {


    /**
     * Find PatchMetaState by playerId, regionId, and patchId
     * @param playerId - the player id
     * @param regionId - the region id
     * @param patchId - the patch id 0, 8, 16, 24, 32
     * @return PatchMetaState
     */
    Optional<PatchMetaState> findPatchMetaState(long playerId, int regionId, int patchId);

    /**
     * Find PatchMetaState by stateId
     * @param stateId - the state id
     * @return PatchMetaState
     */
    Optional<PatchMetaState> findByStateId(long stateId);

    /**
     * Find all PatchMetaState for player in region
     * @param playerId - the player id
     * @param regionId - the region id
     * @return List<PatchMetaState>
     */
    List<PatchMetaState> findAllPatchMetaStateForPlayerInRegion(long playerId, int regionId);

    /**
     * Save PatchMetaState
     * @param patchMetaState - the PatchMetaState
     * @return PatchMetaState
     */
    PatchMetaState save(PatchMetaState patchMetaState);

    /**
     * Update PatchMetaState
     * @param patchMetaState - the PatchMetaState
     * @return PatchMetaState
     */
    PatchMetaState update(PatchMetaState patchMetaState);
}
