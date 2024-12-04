package ethos.runehub.service.skill.farming.impl;

import ethos.runehub.entity.skill.farming.FarmingPatchState;
import ethos.runehub.entity.skill.farming.PlayerPatchState;
import ethos.runehub.repository.skill.farming.PlayerPatchStateRepository;
import ethos.runehub.service.skill.farming.FarmingPatchStateService;
import ethos.runehub.service.skill.farming.PlayerPatchStateService;
import org.runehub.api.util.IDManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlayerPatchStateServiceImpl implements PlayerPatchStateService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerPatchStateServiceImpl.class.getName());

    @Override
    public PlayerPatchState findPlayerPatchStateForPlayer(long playerId, int regionId, int patchId) {
        logger.info("Finding PlayerPatchState for player: {}, region: {}, patch: {}", new Object[]{playerId, regionId, patchId});
        PlayerPatchState playerPatchState = playerPatchStateRepository.findByPlayerIdAndPatchIdAndRegionId(playerId, regionId, patchId);
        // If the playerPatchState is null, create a new one it should only be null if the player has never interacted with the patch before
        if (playerPatchState == null) {
            playerPatchState = new PlayerPatchState();
            playerPatchState.setStateId(IDManager.getUUID());
            playerPatchState.setPlayerId(playerId);
            playerPatchState.setRegionId(regionId);
            playerPatchState.setPatchId(patchId);
            this.save(playerPatchState);
        }
        return playerPatchState;
    }

    @Override
    public List<PlayerPatchState> findAllByPlayerId(long playerId) {
        logger.info("Finding all PlayerPatchState entities by playerId: {}", playerId);
        return playerPatchStateRepository.findAllByPlayerId(playerId);
    }

    @Override
    public List<PlayerPatchState> findAllByPatchId(int patchId) {
        logger.info("Finding all PlayerPatchState entities by patchId: {}", patchId);
        return List.of();
    }

    @Override
    public List<PlayerPatchState> findAllByRegionId(int regionId) {
        logger.info("Finding all PlayerPatchState entities by regionId: {}", regionId);
        return List.of();
    }

    @Override
    public List<PlayerPatchState> findAll() {
        logger.info("Finding all PlayerPatchState entities");
        return playerPatchStateRepository.findAll();
    }

    @Override
    public void save(PlayerPatchState playerPatchState) {
        logger.info("Saving PlayerPatchState: {}", playerPatchState);
        FarmingPatchState farmingPatchState = new FarmingPatchState();
        farmingPatchState.setStateId(playerPatchState.getStateId());

        // We should only be saving a new playerPatchState if the player has never interacted with the patch before and should create a corresponding farmingPatchState
        playerPatchStateRepository.insert(playerPatchState);
        farmingPatchStateService.save(farmingPatchState);
    }

    @Override
    public void update(PlayerPatchState playerPatchState) {
        logger.info("Updating PlayerPatchState: {}", playerPatchState);
    }

    public PlayerPatchStateServiceImpl(FarmingPatchStateService farmingPatchStateService) {
        this.farmingPatchStateService = farmingPatchStateService;
    }

    private final PlayerPatchStateRepository playerPatchStateRepository = new PlayerPatchStateRepository();
    private final FarmingPatchStateService farmingPatchStateService;
}
