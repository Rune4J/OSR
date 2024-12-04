CREATE TABLE farming_patch_state
(
    patch_state_id BIGINT,
    watered        INTEGER,
    diseased       INTEGER,
    growth_stage   INTEGER,
    seed_id     INTEGER,
    patch_location INTEGER,
    PRIMARY KEY (patch_state_id)
--     FOREIGN KEY (seed_id) REFERENCES farming_crop_state (seed_id)
);