package ethos.runehub.repository.skill.farming;

import ethos.runehub.RunehubConstants;
import ethos.runehub.entity.skill.farming.FarmingPatchState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FarmingPatchStateRepository {

    private static final Logger logger = LoggerFactory.getLogger(FarmingPatchStateRepository.class.getName());
    private static final String URL = "jdbc:sqlite:" + RunehubConstants.RUNE4J;

    /**
     * Find FarmingPatchState by stateId
     */
    public FarmingPatchState findByStateId(long stateId) {
        logger.info("Finding FarmingPatchState by stateId: {}", stateId);

        final String query = "SELECT * FROM farming_patch_state WHERE state_id = ?";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, stateId);

            return getFarmingPatchState(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data for state {}. Cause: {}", new Object[]{stateId, e.getMessage()});
        }
        return null;
    }

    /**
     * Save FarmingPatchState
     */
    public void save(FarmingPatchState farmingPatchState) {
        logger.info("Saving FarmingPatchState: {}", farmingPatchState);

        final String query = "INSERT INTO farming_patch_state (state_id, watered, diseased, growth_stage, seed_id) VALUES (?, ?, ?, ?, ?)";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, farmingPatchState.getStateId());
            pstmt.setInt(2, farmingPatchState.getWatered());
            pstmt.setInt(3, farmingPatchState.getDiseased());
            pstmt.setInt(4, farmingPatchState.getGrowthStage());
            pstmt.setInt(5, farmingPatchState.getSeedId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to save farming data for state {}. Cause: {}", new Object[]{farmingPatchState.getStateId(), e.getMessage()});
        }
    }

    private FarmingPatchState getFarmingPatchState(PreparedStatement pstmt) throws SQLException {
        try (var rs = pstmt.executeQuery()) {
            if (rs.next()) {
                final FarmingPatchState farmingPatchState = new FarmingPatchState();
                farmingPatchState.setStateId(rs.getLong("state_id"));
                farmingPatchState.setWatered(rs.getInt("watered"));
                farmingPatchState.setDiseased(rs.getInt("diseased"));
                farmingPatchState.setGrowthStage(rs.getInt("growth_stage"));
                farmingPatchState.setSeedId(rs.getInt("seed_id"));
                return farmingPatchState;
            }
        }
        return null;
    }
}
