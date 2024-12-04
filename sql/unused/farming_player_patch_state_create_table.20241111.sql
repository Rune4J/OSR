CREATE TABLE farming_player_patch_state
(
    state_id        BIGINT PRIMARY KEY,
    player_id       BIGINT,
    region_id       INTEGER,
    patch_id        INTEGER,
    compost_state   INTEGER,
    protected_state INTEGER,
    harvested_count INTEGER
);

-- Indexes
CREATE INDEX player_farming_variables_player_id_idx ON farming_player_patch_state (player_id);
CREATE INDEX player_farming_variables_region_id_idx ON farming_player_patch_state (region_id);
CREATE INDEX player_farming_variables_patch_id_idx ON farming_player_patch_state (patch_id);