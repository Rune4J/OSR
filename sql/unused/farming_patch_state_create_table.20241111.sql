CREATE TABLE farming_patch_state
(
    state_id       BIGINT PRIMARY KEY,
    watered        INTEGER,
    diseased       INTEGER,
    growth_stage   INTEGER,
    seed_id     INTEGER,
    FOREIGN KEY (state_id) REFERENCES farming_player_patch_state (state_id)
);

CREATE INDEX patch_state_state_id_idx ON farming_patch_state (state_id);
CREATE INDEX patch_state_seed_id_idx ON farming_patch_state (seed_id);