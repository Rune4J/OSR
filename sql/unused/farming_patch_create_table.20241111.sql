-- Create the player_farm table with the player farming variables
CREATE TABLE farming_patch
(
    patch_id       INTEGER PRIMARY KEY,
    region_id      INTEGER,
    patch_group_id INTEGER,
    patch_type_id  INTEGER
);

-- Indexes
CREATE INDEX patch_patch_id_idx ON farming_patch (patch_id);
CREATE INDEX patch_region_id_idx ON farming_patch (region_id);
CREATE INDEX patch_patch_group_id_idx ON farming_patch (patch_group_id);
CREATE INDEX patch_patch_type_id_idx ON farming_patch (patch_type_id);
