package ethos.rune4j.domain.mapper.loot;

import ethos.rune4j.domain.model.dto.loot.LootTableContainerAssociationDTO;
import ethos.rune4j.domain.model.entity.loot.LootTableContainerAssociation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mapper class for converting between LootTableContainerAssociationEntity and LootTableContainerAssociationEntityDTO.
 */
public class LootTableContainerAssociationEntityDTOMapper {

    private static final Logger logger = LoggerFactory.getLogger(LootTableContainerAssociationEntityDTOMapper.class);

    /**
     * Converts a LootTableContainerAssociation to a LootTableContainerAssociationDTO.
     *
     * @param entity the LootTableContainerAssociation to convert
     * @return the converted LootTableContainerAssociationDTO, or null if the input entity is null
     */
    public static LootTableContainerAssociationDTO toDTO(LootTableContainerAssociation entity) {
        logger.info("Mapping LootTableContainerAssociation {} to LootTableContainerAssociationDTO", entity);

        if (entity == null) {
            logger.warn("LootTableContainerAssociation is null, returning null");
            return null;
        }

        LootTableContainerAssociationDTO dto = new LootTableContainerAssociationDTO();
        dto.setId(entity.getId());
        dto.setLootTableContainerId(entity.getLootTableContainerId());
        dto.setTableId(entity.getTableId());
        dto.setWeight(entity.getWeight());

        return dto;
    }

    /**
     * Converts a LootTableContainerAssociationDTO to a LootTableContainerAssociation.
     *
     * @param dto the LootTableContainerAssociationDTO to convert
     * @return the converted LootTableContainerAssociation, or null if the input dto is null
     */
    public static LootTableContainerAssociation fromDTO(LootTableContainerAssociationDTO dto) {
        logger.info("Mapping LootTableContainerAssociationDTO {} to LootTableContainerAssociation", dto);

        if (dto == null) {
            logger.warn("LootTableContainerAssociationDTO is null, returning null");
            return null;
        }

        LootTableContainerAssociation entity = new LootTableContainerAssociation();
        entity.setId(dto.getId());
        entity.setLootTableContainerId(dto.getLootTableContainerId());
        entity.setTableId(dto.getTableId());
        entity.setWeight(dto.getWeight());

        return entity;
    }

}