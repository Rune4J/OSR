package ethos.rune4j.domain.mapper.loot;

import ethos.rune4j.domain.model.dto.loot.LootTableDTO;
import ethos.rune4j.domain.model.entity.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootTableDTOMapper {

    private static final Logger logger = LoggerFactory.getLogger(LootTableDTOMapper.class);

    /**
     * Converts a LootTableEntity to a LootTableEntityDTO.
     *
     * @param lootTableEntity the LootTableEntity to convert
     * @return the converted LootTableEntityDTO, or null if the input is null
     */
    public static LootTableDTO toDTO(LootTable lootTableEntity) {
        logger.info("Converting LootTableEntity {} to LootTableEntityDTO", lootTableEntity);
        if (lootTableEntity == null) {
            logger.warn("LootTableEntity is null, returning null");
            return null;
        }

        LootTableDTO lootTableEntityDTO = new LootTableDTO();
        lootTableEntityDTO.setId(lootTableEntity.getId());

        return lootTableEntityDTO;
    }

    /**
     * Converts a LootTableDTO to a LootTable.
     *
     * @param lootTableEntityDTO the LootTableDTO to convert
     * @return the converted LootTable, or null if the input is null
     */
    public static LootTable fromDTO(LootTableDTO lootTableEntityDTO) {
        logger.info("Converting LootTableEntityDTO {} to LootTableEntity", lootTableEntityDTO);
        if (lootTableEntityDTO == null) {
            logger.warn("LootTableEntityDTO is null, returning null");
            return null;
        }

        LootTable lootTableEntity = new LootTable();
        lootTableEntity.setId(lootTableEntityDTO.getId());

        return lootTableEntity;
    }
}
