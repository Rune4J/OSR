-- Create the player_farm table with the player farming variables
CREATE TABLE farming_player_farming_variables (
                                          player_id BIGINT,
                                          region_id INTEGER,
                                          patch_id INTEGER,
                                          compost_state INTEGER,
                                          protected_state INTEGER,
                                          harvested_count INTEGER,
                                          PRIMARY KEY (player_id, region_id, patch_id)
);

-- Indexes
CREATE INDEX player_farming_variables_player_id_idx ON farming_player_farming_variables (player_id);
CREATE INDEX player_farming_variables_region_id_idx ON farming_player_farming_variables (region_id);
CREATE INDEX player_farming_variables_patch_id_idx ON farming_player_farming_variables (patch_id);