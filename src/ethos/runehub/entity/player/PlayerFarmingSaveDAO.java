package ethos.runehub.entity.player;

import ethos.runehub.RunehubConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Player Farming Save data
 */
public class PlayerFarmingSaveDAO {

    private static final Logger logger = LoggerFactory.getLogger(PlayerFarmingSaveDAO.class.getName());
    private static final String URL = "jdbc:sqlite:" + RunehubConstants.PLAYER_DB;

    private static PlayerFarmingSaveDAO instance = null;

    public static PlayerFarmingSaveDAO getInstance() {
        if (instance == null)
            instance = new PlayerFarmingSaveDAO();
        return instance;
    }


    /**
     * Selects all farming data for a specific player id
     * @param playerId the player's unique identifier
     */
    public List<PlayerFarmingSave> selectFarmingDataByPlayer(long playerId) {
        String query = "SELECT playerId, regionId, stage, patch, crop, compost, watered, diseased, harvested, patchTypeId FROM farming WHERE playerId = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, playerId);

            List<PlayerFarmingSave> farmingSaves = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int regionId = rs.getInt("regionId");
                    int stage = rs.getInt("stage");
                    int patch = rs.getInt("patch");
                    int crop = rs.getInt("crop");
                    int compost = rs.getInt("compost");
                    boolean watered = rs.getBoolean("watered");
                    boolean diseased = rs.getBoolean("diseased");
                    int harvested = rs.getInt("harvested");
                    int patchTypeId = rs.getInt("patchTypeId");
                    PlayerFarmingSave farmingSave = new PlayerFarmingSave(playerId);
                    farmingSave.setRegionId(regionId);
                    farmingSave.setPatchGroupId(patch);
                    farmingSave.setStage(stage);
                    farmingSave.setCropId(crop);
                    farmingSave.setCompost(compost);
                    farmingSave.setWatered(watered);
                    farmingSave.setDiseased(diseased);
                    farmingSave.setHarvested(harvested);
                    farmingSave.setPatchTypeId(patchTypeId);
                    farmingSaves.add(farmingSave);
                    logger.info("Player ID: {}, Region ID: {}, Stage: {}, Patch: {}, Crop: {}, Compost: {}, Watered: {}, Diseased: {}, Harvested: {}, Patch Type: {}", new Object[]{playerId, regionId, stage, patch, crop, compost, watered, diseased, harvested,patchTypeId});
                }
                return farmingSaves;
            }
        } catch (SQLException e) {
            logger.error("Failed to read farming data for player {}. Cause: {}", new Object[]{playerId, e.getMessage()});
        }
        return null;
    }

    /**
     * Selects all farming data for a player in a specific region at a specific patch
     *
     * @param playerId the player's unique identifier
     * @param regionId the region's unique identifier
     * @param patch the patch's unique identifier
     */
    public PlayerFarmingSave selectFarmingDataByPlayerRegionAndPatch(long playerId, int regionId, int patch) {
        String query = "SELECT playerId, regionId, stage, patch, crop, compost, watered, diseased, harvested, patchTypeId FROM farming WHERE playerId = ? AND regionId = ? AND patch = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, playerId);
            pstmt.setInt(2, regionId);
            pstmt.setInt(3, patch);

            try (ResultSet rs = pstmt.executeQuery()) {
                PlayerFarmingSave farmingSave = new PlayerFarmingSave(playerId);
                while (rs.next()) {
                    int stage = rs.getInt("stage");
                    int crop = rs.getInt("crop");
                    int compost = rs.getInt("compost");
                    boolean watered = rs.getBoolean("watered");
                    boolean diseased = rs.getBoolean("diseased");
                    int harvested = rs.getInt("harvested");
                    int patchTypeId = rs.getInt("patchTypeId");

                    farmingSave.setPatchTypeId(patchTypeId);
                    farmingSave.setRegionId(regionId);
                    farmingSave.setPatchGroupId(patch);
                    farmingSave.setStage(stage);
                    farmingSave.setCropId(crop);
                    farmingSave.setCompost(compost);
                    farmingSave.setWatered(watered);
                    farmingSave.setDiseased(diseased);
                    farmingSave.setHarvested(harvested);

                    logger.info("Player ID: {}, Region ID: {}, Stage: {}, Patch: {}, Crop: {}, Compost: {}, Watered: {}, Diseased: {}, Harvested: {}, PatchType: {}", new Object[]{playerId, regionId, stage, patch, crop, compost, watered, diseased, harvested,patchTypeId});
                }
                return farmingSave;
            }
        } catch (SQLException e) {
            logger.error("Failed to read farming data for player {} in region {} at patch {}. Cause: {}", new Object[] {playerId, regionId,patch,e.getMessage()});
        }
        return null;
    }

    /**
     * Selects all farming data for a player in a specific region
     */
    public List<PlayerFarmingSave> selectFarmingDataByPlayerAndRegion(long playerId, int regionId) {
        String query = "SELECT playerId, regionId, stage, patch, crop, compost, watered, diseased, harvested, patchTypeId FROM farming WHERE playerId = ? AND regionId = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, playerId);
            pstmt.setInt(2, regionId);

            List<PlayerFarmingSave> farmingSaves = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int stage = rs.getInt("stage");
                    int patch = rs.getInt("patch");
                    int crop = rs.getInt("crop");
                    int compost = rs.getInt("compost");
                    boolean watered = rs.getBoolean("watered");
                    boolean diseased = rs.getBoolean("diseased");
                    int harvested = rs.getInt("harvested");
                    int patchTypeId = rs.getInt("patchTypeId");
                    PlayerFarmingSave farmingSave = new PlayerFarmingSave(playerId);
                    farmingSave.setPatchGroupId(patch);
                    farmingSave.setStage(stage);
                    farmingSave.setCropId(crop);
                    farmingSave.setCompost(compost);
                    farmingSave.setWatered(watered);
                    farmingSave.setDiseased(diseased);
                    farmingSave.setHarvested(harvested);
                    farmingSaves.add(farmingSave);
                    farmingSave.setRegionId(regionId);
                    farmingSave.setPatchTypeId(patchTypeId);
                    logger.info("Player ID: {}, Region ID: {}, Stage: {}, Patch: {}, Crop: {}, Compost: {}, Watered: {}, Diseased: {}, Harvested: {}, PatchType: {}", new Object[]{playerId, regionId, stage, patch, crop, compost, watered, diseased, harvested,patchTypeId});
                }
                return farmingSaves;
            }
        } catch (SQLException e) {
            logger.error("Failed to read farming data for player {} in region {}. Cause: {}", new Object[]{playerId, regionId, e.getMessage()});
        }
        return null;
    }

    /**
     * Inserts farming data for a player
     *
     * @param save the player's farming data
     */
    public void insertFarmingData(PlayerFarmingSave save) {
        String query = "INSERT INTO farming (playerId, regionId, stage, patch, crop, compost, watered, diseased, harvested, patchTypeId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, save.getPlayerId());
            pstmt.setInt(2, save.getRegionId());
            pstmt.setInt(3, save.getStage());
            pstmt.setInt(4, save.getPatchGroupId());
            pstmt.setInt(5, save.getCropId());
            pstmt.setInt(6, save.getCompost());
            pstmt.setBoolean(7, save.isWatered());
            pstmt.setBoolean(8, save.isDiseased());
            pstmt.setInt(9, save.getHarvested());
            pstmt.setInt(10, save.getPatchTypeId());

            pstmt.executeUpdate();
            logger.info("Inserted farming data for player {} in region {} at patch {}", new Object[]{save.getPlayerId(), save.getRegionId(), save.getPatchGroupId()});
        } catch (SQLException e) {
            logger.error("Failed to insert farming data for player {} in region {} at patch {}. Cause: {}", new Object[]{save.getPlayerId(), save.getRegionId(), save.getPatchGroupId(), e.getMessage()});
        }
    }


    /**
     * Updates farming data for a player
     *
     * @param save the player's farming data
     */
    public void updateFarmingData(PlayerFarmingSave save) {
        String query = "UPDATE farming SET stage = ?, crop = ?, compost = ?, watered = ?, diseased = ?, harvested = ? WHERE playerId = ? AND regionId = ? AND patch = ? AND patchTypeId = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, save.getStage());
            pstmt.setInt(2, save.getCropId());
            pstmt.setInt(3, save.getCompost());
            pstmt.setBoolean(4, save.isWatered());
            pstmt.setBoolean(5, save.isDiseased());
            pstmt.setInt(6, save.getHarvested());
            pstmt.setLong(7, save.getPlayerId());
            pstmt.setInt(8, save.getRegionId());
            pstmt.setInt(9, save.getPatchGroupId());
            pstmt.setInt(10, save.getPatchTypeId());

            pstmt.executeUpdate();
            logger.info("Updated farming data for player {} in region {} at patch {}", new Object[]{save.getPlayerId(), save.getRegionId(), save.getPatchGroupId()});
        } catch (SQLException e) {
            logger.error("Failed to update farming data for player {} in region {} at patch {}. Cause: {}", new Object[]{save.getPlayerId(), save.getRegionId(), save.getPatchGroupId(), e.getMessage()});
        }
    }

    private PlayerFarmingSaveDAO() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String query = "CREATE TABLE IF NOT EXISTS farming (playerId INTEGER, regionId INTEGER, stage INTEGER, patch INTEGER, crop INTEGER, compost INTEGER, watered BOOLEAN, diseased BOOLEAN, harvested INTEGER, patchTypeId INTEGER, PRIMARY KEY (playerId, regionId, patch, patchTypeId))";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(query);
            }
        } catch (SQLException e) {
            logger.error("Failed to create farming table. Cause: {}", e.getMessage());
        }
    }
}
