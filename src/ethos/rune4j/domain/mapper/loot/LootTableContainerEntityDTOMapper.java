package ethos.rune4j.domain.mapper.loot;

import ethos.rune4j.domain.model.dto.loot.LootTableContainerDTO;
import ethos.rune4j.domain.model.entity.loot.LootTableContainer;
import ethos.rune4j.domain.model.entity.loot.LootTableContainerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mapper class for converting between LootTableContainer and LootTableContainerDTO.
 */
public class LootTableContainerEntityDTOMapper {

    private static final Logger logger = LoggerFactory.getLogger(LootTableContainerEntityDTOMapper.class);

    /**
     * Converts a LootTableContainer to a LootTableContainerDTO.
     *
     * @param entity the LootTableContainer to convert
     * @return the converted LootTableContainerEntityDTO, or null if the input entity is null
     */
    public static LootTableContainerDTO toDTO(LootTableContainer entity) {
        logger.info("Mapping LootTableContainer {} to LootTableContainerDTO", entity);
        if (entity == null) {
            logger.warn("LootTableContainer is null, returning null");
            return null;
        }

        LootTableContainerDTO dto = new LootTableContainerDTO();
        dto.setId(entity.getId());
        dto.setContainerId(entity.getContainerId());
        dto.setType(entity.getType());

        return dto;
    }

    /**
     * Converts a LootTableContainerDTO to a LootTableContainer.
     *
     * @param dto the LootTableContainerDTO to convert
     * @return the converted LootTableContainer, or null if the input dto is null
     */
    public static LootTableContainer fromDTO(LootTableContainerDTO dto) {
        logger.info("Mapping LootTableContainerDTO {} to LootTableContainer", dto);
        if (dto == null) {
            logger.warn("LootTableContainerDTO is null, returning null");
            return null;
        }

        LootTableContainer entity = new LootTableContainer();
        entity.setId(dto.getId());
        entity.setContainerId(dto.getContainerId());
        entity.setType(dto.getType());

        return entity;
    }
}