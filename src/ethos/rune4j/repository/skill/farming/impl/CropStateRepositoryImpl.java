package ethos.rune4j.repository.skill.farming.impl;

import ethos.rune4j.repository.skill.farming.CropStateRepository;
import ethos.runehub.RunehubConstants;
import ethos.rune4j.entity.skill.farming.CropState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class CropStateRepositoryImpl implements CropStateRepository {

    private static final Logger logger = LoggerFactory.getLogger(CropStateRepositoryImpl.class.getName());
    private static final String URL = "jdbc:sqlite:" + RunehubConstants.RUNE4J;

    /**
     * Find CropState by seedId
     */
    @Override
    public CropState findBySeedId(int seedId) {
        logger.info("Finding CropState by seedId: {}", seedId);

        final String query = "SELECT * FROM farming_crop_state WHERE seed_id = ?";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, seedId);

            return getCropState(pstmt);
        } catch (SQLException e) {
            logger.error("Failed to read farming data for seed {}. Cause: {}", seedId, e.getMessage());
        }
        return null;
    }

    private CropState getCropState(PreparedStatement pstmt) throws SQLException {
        try (var rs = pstmt.executeQuery()) {
            if (rs.next()) {
                final CropState cropState = new CropState();
                cropState.setSeedId(rs.getInt("seed_id"));
                cropState.setCropIndex(rs.getInt("crop_index"));
                cropState.setRegrow(rs.getInt("regrows"));
                cropState.setStages(rs.getInt("stage_count"));
                cropState.setDiseaseSuccessThreshold(rs.getInt("min_disease_chance"));
                cropState.setPatchLocation(rs.getInt("patch_location"));
                return cropState;
            }
        }
        return null;
    }
}
