CREATE TABLE farming_crop_metadata
(
    seed_id             INTEGER PRIMARY KEY,
    planting_xp         INTEGER,
    harvesting_xp       INTEGER,
    base_harvest_amount INTEGER,
    harvested_crop_id   INTEGER,
    level_requirement   INTEGER,
    seed_amount         INTEGER,
    check_health_xp     INTEGER,
    cts_low             INTEGER,
    cts_high            INTEGER,
    FOREIGN KEY (seed_id) REFERENCES farming_crop_state (seed_id)
);

CREATE INDEX crop_metadata_seed_id_idx ON farming_crop_metadata (seed_id);