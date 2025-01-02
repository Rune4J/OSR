package ethos.rune4j.domain.mapper.loot;


import ethos.rune4j.domain.model.dto.loot.LootDTO;
import ethos.rune4j.domain.model.entity.loot.Loot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mapper class for converting between Loot and LootDTO.
 */
public class LootDTOMapper {

    private static final Logger logger = LoggerFactory.getLogger(LootDTOMapper.class);

    /**
     * Converts a LootEntity to a LootEntityDTO.
     *
     * @param lootEntity the LootEntity to convert
     * @return the converted LootEntityDTO, or null if the input is null
     */
    public static LootDTO toDTO(final Loot lootEntity) {
        logger.info("Converting LootEntity {} to LootEntityDTO", lootEntity);

        if (lootEntity == null) {
            logger.warn("LootEntity is null, returning null");
            return null;
        }

        LootDTO lootEntityDTO = new LootDTO();
        lootEntityDTO.setId(lootEntity.getId());
        lootEntityDTO.setBaseChance(lootEntity.getBaseChance());
        lootEntityDTO.setTableId(lootEntity.getTableId());
        lootEntityDTO.setItemId(lootEntity.getItemId());
        lootEntityDTO.setMinAmount(lootEntity.getMinAmount());
        lootEntityDTO.setMaxAmount(lootEntity.getMaxAmount());
        lootEntityDTO.setSecondary(lootEntity.isSecondary());
        lootEntityDTO.setTertiary(lootEntity.isTertiary());
        lootEntityDTO.setSubTableId(lootEntity.getSubTableId());


        return lootEntityDTO;
    }

    /**
     * Converts a LootEntityDTO to a LootEntity.
     *
     * @param lootEntityDTO the LootEntityDTO to convert
     * @return the converted LootEntity, or null if the input is null
     */
    public static Loot fromDTO(final LootDTO lootEntityDTO) {
        logger.info("Converting LootEntityDTO {} to LootEntity", lootEntityDTO);

        if (lootEntityDTO == null) {
            logger.warn("LootEntityDTO is null, returning null");
            return null;
        }

        Loot lootEntity = new Loot();
        lootEntity.setId(lootEntityDTO.getId());
        lootEntity.setBaseChance(lootEntityDTO.getBaseChance());
        lootEntity.setTableId(lootEntityDTO.getTableId());
        lootEntity.setItemId(lootEntityDTO.getItemId());
        lootEntity.setMinAmount(lootEntityDTO.getMinAmount());
        lootEntity.setMaxAmount(lootEntityDTO.getMaxAmount());
        lootEntity.setSecondary(lootEntityDTO.isSecondary());
        lootEntity.setTertiary(lootEntityDTO.isTertiary());
        lootEntity.setSubTableId(lootEntityDTO.getSubTableId());

        return lootEntity;
    }
}
