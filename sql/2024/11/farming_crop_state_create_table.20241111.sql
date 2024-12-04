CREATE TABLE farming_crop_state (
                            seed_id INTEGER PRIMARY KEY,
                            crop_index INTEGER,
                            min_disease_chance INTEGER,
                            stage_count INTEGER,
                            regrows INTEGER, -- this is how many stages this crops maturity state has. For example an apple tree grows up to 6 apples to this would be 6. This is probably better named as 'maturity_stages' but it's too late now :      ^)
                            patch_location INTEGER
);

CREATE INDEX crop_state_seed_id_idx ON farming_crop_state (seed_id);
CREATE INDEX crop_state_crop_index_idx ON farming_crop_state (crop_index);