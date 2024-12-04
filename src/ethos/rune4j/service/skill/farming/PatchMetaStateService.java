package ethos.rune4j.service.skill.farming;

import ethos.rune4j.entity.skill.farming.PatchMetaState;

import java.util.List;

public interface PatchMetaStateService {

    /**
     * We'll use this to grab the meta state of any given patch in a region for a player
     * @param playerId - the player id
     * @param regionId - the region id
     * @param patchId - the patch id
     * @return PatchMetaState
     */
    PatchMetaState findPatchMetaStateForPlayerInRegionAtPatch(long playerId, int regionId, int patchId);

    /**
     * We'll use this to grab all the meta states of any given patch in a region for a player. This represents an entire "farm"
     * @param playerId - the player id
     * @param regionId - the region id
     * @return List<PatchMetaState>
     */
    List<PatchMetaState> findAllPatchMetaStateForPlayerInRegion(long playerId, int regionId);

    /**
     * This will only work if a patch already exists. If the player has never interacted with the patch before, it will return null
     * @param stateId - the state id
     * @return PatchMetaState
     */
    PatchMetaState findByStateId(long stateId);

    /**
     * We'll use this to save the meta state of a patch for a player if it doesn't exist, or update it if it does
     * @param patchMetaState - the PatchMetaState
     * @return PatchMetaState
     */
    PatchMetaState save(PatchMetaState patchMetaState);
}
