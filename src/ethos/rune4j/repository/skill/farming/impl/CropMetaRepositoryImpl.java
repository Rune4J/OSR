package ethos.rune4j.repository.skill.farming.impl;

import ethos.rune4j.entity.skill.farming.CropMeta;
import ethos.rune4j.repository.skill.farming.CropMetaRepository;
import ethos.runehub.RunehubConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class CropMetaRepositoryImpl implements CropMetaRepository {

    private static final Logger logger = LoggerFactory.getLogger(CropMetaRepositoryImpl.class.getName());
    private static final String URL = "jdbc:sqlite:" + RunehubConstants.RUNE4J;

    @Override
    public Optional<CropMeta> findBySeedId(int seedId) {
        logger.info("Finding CropMeta by seedId: {}", seedId);

        final String query = "SELECT * FROM farming_crop_metadata WHERE seed_id = ?";

        //Query database and return result
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, seedId);

            return Optional.ofNullable(getCropMeta(pstmt));
        } catch (SQLException e) {
            logger.error("Failed to read farming meta data for seed {}. Cause: {}", seedId, e.getMessage());
        }
        return Optional.empty();
    }

    private CropMeta getCropMeta(PreparedStatement pstmt) throws SQLException {
        try (var rs = pstmt.executeQuery()) {
            if (rs.next()) {
                final CropMeta cropMeta = new CropMeta();
                cropMeta.setSeedId(rs.getInt("seed_id"));
                cropMeta.setPlantXp(rs.getInt("planting_xp"));
                cropMeta.setHarvestXp(rs.getInt("harvesting_xp"));
                cropMeta.setHarvestMinimum(rs.getInt("base_harvest_amount"));
                cropMeta.setHarvestedId(rs.getInt("harvested_crop_id"));
                cropMeta.setLevelRequirement(rs.getInt("level_requirement"));
                cropMeta.setSeedsPlanted(rs.getInt("seed_amount"));
                cropMeta.setCheckHealthXp(rs.getInt("check_health_xp"));
                cropMeta.setCtsMin(rs.getInt("cts_low"));
                cropMeta.setCtsMax(rs.getInt("cts_high"));
                return cropMeta;
            }
        }
        return null;
    }
}
