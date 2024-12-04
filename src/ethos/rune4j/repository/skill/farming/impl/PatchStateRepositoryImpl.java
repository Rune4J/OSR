package ethos.rune4j.repository.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.PatchState;
import ethos.rune4j.repository.skill.farming.PatchStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class PatchStateRepositoryImpl implements PatchStateRepository {

    private static final Logger logger = LoggerFactory.getLogger(PatchStateRepositoryImpl.class.getName());

    @Override
    public Optional<PatchState> findPatchStateById(long patchStateId) {
        logger.info("Finding PatchState by id: {}", patchStateId);
        // Query to find PatchState by id
        final String query = "SELECT * FROM farming_patch_state WHERE patch_state_id = ?";
        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, patchStateId);
            return getPatchState(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data for patch state {}. Cause: {}", patchStateId, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public PatchState save(PatchState patchState) {
        logger.info("Saving to repository PatchState: {}", patchState);
        // Query to save PatchState
        final String query = "INSERT INTO farming_patch_state (patch_state_id, watered, diseased, growth_stage, seed_id, patch_location) VALUES (?,?, ?, ?, ?,?)";
        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, patchState.getPatchStateId());
            pstmt.setInt(2, patchState.getWatered());
            pstmt.setInt(3, patchState.getDiseased());
            pstmt.setInt(4, patchState.getGrowthStage());
            pstmt.setInt(5, patchState.getSeedId());
            pstmt.setInt(6, patchState.getPatchLocation());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to save farming data for patch state {}. Cause: {}", patchState, e.getMessage());
        }
        return null;
    }

    @Override
    public PatchState update(PatchState patchState) {
        logger.info("Updating PatchState: {}", patchState);
        // Query to update PatchState
        final String query = "UPDATE farming_patch_state SET seed_id = ?, growth_stage = ?, diseased = ?, watered = ?, patch_location = ? WHERE patch_state_id = ?";
        // Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patchState.getSeedId());
            pstmt.setInt(2, patchState.getGrowthStage());
            pstmt.setInt(3, patchState.getDiseased());
            pstmt.setInt(4, patchState.getWatered());
            pstmt.setInt(5, patchState.getPatchLocation());
            pstmt.setLong(6, patchState.getPatchStateId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to update farming data for patch state {}. Cause: {}", patchState, e.getMessage());
        }
        return null;
    }

    private Optional<PatchState> getPatchState(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                final PatchState patchState = new PatchState();
                patchState.setPatchStateId(rs.getLong("patch_state_id"));
                patchState.setWatered(rs.getInt("watered"));
                patchState.setDiseased(rs.getInt("diseased"));
                patchState.setGrowthStage(rs.getInt("growth_stage"));
                patchState.setSeedId(rs.getInt("seed_id"));
                patchState.setPatchLocation(rs.getInt("patch_location"));
                logger.info("Found PatchState: {}", patchState);
                return Optional.of(patchState);
            }
        }
        return Optional.empty();
    }
}
