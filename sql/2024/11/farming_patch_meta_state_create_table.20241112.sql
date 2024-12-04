CREATE TABLE farming_patch_meta_state
(
                                                    player_id BIGINT,
                                                    region_id INTEGER,
                                                    patch_location INTEGER,
                                                    compost_state INTEGER,
                                                    protected_state INTEGER,
                                                    harvested_count INTEGER,
                                                    patch_state_id BIGINT,
                                                    plant_time BIGINT,
                                                    harvest_time BIGINT,
                                                    PRIMARY KEY (player_id, region_id, patch_location),
                                                    FOREIGN KEY (patch_state_id) REFERENCES farming_patch_state (patch_state_id)
);

CREATE INDEX farming_patch_meta_state_player_id_idx ON farming_patch_meta_state (player_id);
CREATE INDEX farming_patch_meta_state_region_id_idx ON farming_patch_meta_state (region_id);
CREATE INDEX farming_patch_meta_state_patch_id_idx ON farming_patch_meta_state (patch_location);