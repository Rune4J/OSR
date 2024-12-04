package ethos.rune4j.repository.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.PatchMetaState;
import ethos.rune4j.repository.skill.farming.PatchMetaStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatchMetaStateRepositoryImpl implements PatchMetaStateRepository {

    private static final Logger logger = LoggerFactory.getLogger(PatchMetaStateRepositoryImpl.class.getName());


    @Override
    public Optional<PatchMetaState> findPatchMetaState(long playerId, int regionId, int patchId) {
        logger.info("Finding PatchMetaState by playerId: {}, regionId: {}, patchId: {}", playerId, regionId, patchId);
        // Query to find PatchMetaState by playerId, regionId, and patchId
        final String query = "SELECT * FROM farming_patch_meta_state WHERE player_id = ? AND region_id = ? AND patch_location = ?"; // Patch location is the patchId
        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, playerId);
            pstmt.setInt(2, regionId);
            pstmt.setInt(3, patchId);
            return getPatchMetaState(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data for player {}. Cause: {}", playerId, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<PatchMetaState> findByStateId(long stateId) {
        logger.info("Finding PatchMetaState by stateId: {}", stateId);
        // Query to find PatchMetaState by stateId
        final String query = "SELECT * FROM farming_patch_meta_state WHERE patch_state_id = ?";
        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, stateId);
            return getPatchMetaState(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data for stateId {}. Cause: {}", stateId, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<PatchMetaState> findAllPatchMetaStateForPlayerInRegion(long playerId, int regionId) {
        logger.info("Finding all PatchMetaState for player: {}, region: {}", playerId, regionId);
        // Query to find all PatchMetaState for player in region
        final String query = "SELECT * FROM farming_patch_meta_state WHERE player_id = ? AND region_id = ?";
        final List<PatchMetaState> patchMetaStates = new ArrayList<>();

        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, playerId);
            pstmt.setInt(2, regionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                //this should return all results and add them to the list
                while (rs.next()) {
                    final PatchMetaState patchMetaState = new PatchMetaState();
                    patchMetaState.setPlayerId(rs.getLong("player_id"));
                    patchMetaState.setRegionId(rs.getInt("region_id"));
                    patchMetaState.setPatchLocation(rs.getInt("patch_location"));
                    patchMetaState.setCompostState(rs.getInt("compost_state"));
                    patchMetaState.setProtectedState(rs.getInt("protected_state"));
                    patchMetaState.setHarvestedCount(rs.getInt("harvested_count"));
                    patchMetaState.setPatchStateId(rs.getLong("patch_state_id"));
                    patchMetaState.setPlantTime(rs.getLong("plant_time"));
                    patchMetaState.setHarvestTime(rs.getLong("harvest_time"));
                    patchMetaStates.add(patchMetaState);
                }
            }
            return patchMetaStates;
        } catch (SQLException e) {
            logger.error("Failed to read farming data for player {}. Cause: {}", playerId, e.getMessage());
        }
        return List.of();
    }

    @Override
    public PatchMetaState save(PatchMetaState patchMetaState) {
        logger.info("Saving PatchMetaState: {}", patchMetaState);
        // Query to save PatchMetaState
        final String query = "INSERT INTO farming_patch_meta_state (player_id, region_id, patch_location, compost_state, protected_state, harvested_count, patch_state_id, plant_time, harvest_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, patchMetaState.getPlayerId());
            pstmt.setInt(2, patchMetaState.getRegionId());
            pstmt.setInt(3, patchMetaState.getPatchLocation());
            pstmt.setInt(4, patchMetaState.getCompostState());
            pstmt.setInt(5, patchMetaState.getProtectedState());
            pstmt.setInt(6, patchMetaState.getHarvestedCount());
            pstmt.setLong(7, patchMetaState.getPatchStateId());
            pstmt.setLong(8, patchMetaState.getPlantTime());
            pstmt.setLong(9, patchMetaState.getHarvestTime());
            pstmt.executeUpdate();
            return patchMetaState;
        } catch (SQLException e) {
            logger.error("Failed to save farming data for player {}. Cause: {}", patchMetaState.getPlayerId(), e.getMessage());
        }
        return null;
    }

    @Override
    public PatchMetaState update(PatchMetaState patchMetaState) {
        logger.info("Updating PatchMetaState: {}", patchMetaState);
        // Query to update PatchMetaState
        final String query = "UPDATE farming_patch_meta_state SET compost_state = ?, protected_state = ?, harvested_count = ?, patch_state_id = ?, plant_time = ?, harvest_time = ? WHERE player_id = ? AND region_id = ? AND patch_location = ?";
        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patchMetaState.getCompostState());
            pstmt.setInt(2, patchMetaState.getProtectedState());
            pstmt.setInt(3, patchMetaState.getHarvestedCount());
            pstmt.setLong(4, patchMetaState.getPatchStateId());
            pstmt.setLong(7, patchMetaState.getPlayerId());
            pstmt.setInt(8, patchMetaState.getRegionId());
            pstmt.setInt(9, patchMetaState.getPatchLocation());
            pstmt.setLong(5, patchMetaState.getPlantTime());
            pstmt.setLong(6, patchMetaState.getHarvestTime());
            pstmt.executeUpdate();
            return patchMetaState;
        } catch (SQLException e) {
            logger.error("Failed to update farming data for player {}. Cause: {}", patchMetaState.getPlayerId(), e.getMessage());
        }
        return null;
    }

    private Optional<PatchMetaState> getPatchMetaState(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                final PatchMetaState patchMetaState = new PatchMetaState();
                patchMetaState.setPlayerId(rs.getLong("player_id"));
                patchMetaState.setRegionId(rs.getInt("region_id"));
                patchMetaState.setPatchLocation(rs.getInt("patch_location"));
                patchMetaState.setCompostState(rs.getInt("compost_state"));
                patchMetaState.setProtectedState(rs.getInt("protected_state"));
                patchMetaState.setHarvestedCount(rs.getInt("harvested_count"));
                patchMetaState.setPatchStateId(rs.getLong("patch_state_id"));
                patchMetaState.setPlantTime(rs.getLong("plant_time"));
                patchMetaState.setHarvestTime(rs.getLong("harvest_time"));
                logger.info("Found PatchMetaState: {}", patchMetaState);
                return Optional.of(patchMetaState);
            }
        }
        return Optional.empty();
    }
}
