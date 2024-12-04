package ethos.runehub.repository.skill.farming;

import ethos.runehub.RunehubConstants;
import ethos.runehub.entity.skill.farming.PlayerPatchState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for player farming data
 */
public class PlayerPatchStateRepository {

    private static final Logger logger = LoggerFactory.getLogger(PlayerPatchStateRepository.class.getName());
    private static final String URL = "jdbc:sqlite:" + RunehubConstants.RUNE4J;

    /**
     * Find all PlayerFarm entities by playerId
     */
    public List<PlayerPatchState> findAllByPlayerId(long playerId) {
        logger.info("Finding all PlayerPatchState entities by playerId: {}", playerId);

        final String query = "SELECT * FROM farming_player_patch_state WHERE player_id = ?";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, playerId);

            return getPlayerPatchStates(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data for player {}. Cause: {}", new Object[]{playerId, e.getMessage()});
        }
        return null;
    }

    /**
     * Find all PlayerPatchState entities by patchId
     */
    public List<PlayerPatchState> findAllByPatchId(int patchId) {
        logger.info("Finding all PlayerPatchState entities by patchId: {}", patchId);

        final String query = "SELECT * FROM farming_player_patch_state WHERE patch_id = ?";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, patchId);

            return getPlayerPatchStates(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data for patch {}. Cause: {}", new Object[]{patchId, e.getMessage()});
        }
        return null;
    }

    /**
     * Find the PlayerPatchState entity by playerId and patchId and regionId
     */
    public PlayerPatchState findByPlayerIdAndPatchIdAndRegionId(long playerId, int patchId, int regionId) {
        logger.info("Finding PlayerPatchState entity by playerId: {}, patchId: {}, regionId: {}", playerId, patchId, regionId);

        final String query = "SELECT * FROM farming_player_patch_state WHERE player_id = ? AND patch_id = ? AND region_id = ?";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, playerId);
            pstmt.setInt(2, patchId);
            pstmt.setInt(3, regionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return this.readPlayerPatchState(
                            rs.getLong("player_id"),
                            rs.getInt("region_id"),
                            rs.getLong("state_id"),
                            rs.getInt("patch_id"),
                            rs.getInt("compost_state"),
                            rs.getInt("harvested_count")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to read farming data for player {}. Cause: {}", new Object[]{playerId, e.getMessage()});
        }
        return null;
    }

    /**
     * Update the PlayerPatchState entity
     */
    public void update(PlayerPatchState playerPatchState) {
        logger.info("Updating PlayerPatchState entity: {}", playerPatchState);

        final String query = "UPDATE farming_player_patch_state SET state_id = ?, compost_state = ?, harvested_count = ? WHERE player_id = ? AND patch_id = ? AND region_id = ?";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, playerPatchState.getStateId());
            pstmt.setInt(2, playerPatchState.getCompostState());
            pstmt.setInt(3, playerPatchState.getHarvestedCount());
            pstmt.setLong(4, playerPatchState.getPlayerId());
            pstmt.setInt(5, playerPatchState.getPatchId());
            pstmt.setInt(6, playerPatchState.getRegionId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to update farming data for player {}. Cause: {}", new Object[]{playerPatchState.getPlayerId(), e.getMessage()});
        }
    }

    /**
     * Insert the PlayerPatchState entity
     */
    public void insert(PlayerPatchState playerPatchState) {
        logger.info("Inserting PlayerPatchState entity: {}", playerPatchState);

        final String query = "INSERT INTO farming_player_patch_state (player_id, region_id, state_id, patch_id, compost_state, harvested_count) VALUES (?, ?, ?, ?, ?, ?)";
        // Validate the combination of player, region, and patch doesn't exist
        if (findByPlayerIdAndPatchIdAndRegionId(playerPatchState.getPlayerId(), playerPatchState.getPatchId(), playerPatchState.getRegionId()) != null) {
            logger.error("Failed to insert farming data for player {}. Cause: PlayerPatchState already exists", playerPatchState.getPlayerId());
            return;
        }
        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, playerPatchState.getPlayerId());
            pstmt.setInt(2, playerPatchState.getRegionId());
            pstmt.setLong(3, playerPatchState.getStateId());
            pstmt.setInt(4, playerPatchState.getPatchId());
            pstmt.setInt(5, playerPatchState.getCompostState());
            pstmt.setInt(6, playerPatchState.getHarvestedCount());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to insert farming data for player {}. Cause: {}", new Object[]{playerPatchState.getPlayerId(), e.getMessage()});
        }
    }

    /**
     * Find all PlayerPatchState entities
     */
    public List<PlayerPatchState> findAll() {
        logger.info("Finding all PlayerPatchState entities");

        final String query = "SELECT * FROM farming_player_patch_state";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            return getPlayerPatchStates(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data. Cause: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Find all PlayerPatchState entities by regionId
     */
    private List<PlayerPatchState> getPlayerPatchStates(PreparedStatement pstmt) throws SQLException {
        List<PlayerPatchState> farmingSaves = new ArrayList<>();
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                PlayerPatchState playerPatchState = this.readPlayerPatchState(
                        rs.getLong("player_id"),
                        rs.getInt("region_id"),
                        rs.getLong("state_id"),
                        rs.getInt("patch_id"),
                        rs.getInt("compost_state"),
                        rs.getInt("harvested_count")
                );

                farmingSaves.add(playerPatchState);
            }
            return farmingSaves;
        }
    }

    /**
     * Create PlayerPatchState entity
     */
    private PlayerPatchState readPlayerPatchState(long playerId,int regionId, long stateId, int patchId, int compostState, int harvestedCount) {
        PlayerPatchState playerPatchState = new PlayerPatchState();
        playerPatchState.setPlayerId(playerId);
        playerPatchState.setRegionId(regionId);
        playerPatchState.setStateId(stateId);
        playerPatchState.setPatchId(patchId);
        playerPatchState.setCompostState(compostState);
        playerPatchState.setHarvestedCount(harvestedCount);
        logger.info("Read PlayerPatchState entity: {}", playerPatchState);
        return playerPatchState;
    }
}
