package ethos.runehub.service.skill.farming;

import ethos.runehub.entity.skill.farming.PlayerPatchState;

import java.util.List;

public interface PlayerPatchStateService {

    PlayerPatchState findPlayerPatchStateForPlayer(long playerId, int regionId, int patchId);

    List<PlayerPatchState> findAllByPlayerId(long playerId);
    List<PlayerPatchState> findAllByPatchId(int patchId);
    List<PlayerPatchState> findAllByRegionId(int regionId);
    List<PlayerPatchState> findAll();

    void save(PlayerPatchState playerPatchState);

    void update(PlayerPatchState playerPatchState);
}
