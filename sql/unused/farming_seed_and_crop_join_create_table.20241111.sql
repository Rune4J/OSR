CREATE TABLE farming_crop_index_seed_join (
                                      seed_id INTEGER,
                                      crop_index INTEGER,
                                      PRIMARY KEY (seed_id, crop_index)
);

CREATE INDEX idx_crop_index_seed_join_seed_id ON farming_crop_index_seed_join (seed_id);
CREATE INDEX idx_crop_index_seed_join_crop_index ON farming_crop_index_seed_join (crop_index);