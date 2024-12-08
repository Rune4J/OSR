CREATE TABLE item_upgrade_rules
(
    source_item_id             INTEGER PRIMARY KEY,
    source_item_amount         INTEGER NOT NULL,
    upgrade_item_id            INTEGER NOT NULL,
    upgrade_item_amount        INTEGER NOT NULL,
    upgrade_point_cost         INTEGER NOT NULL,
    members_only               BOOLEAN NOT NULL,
    base_success_chance        FLOAT NOT NULL,
    base_consume_chance        FLOAT NOT NULL,
    base_upgrade_chance_increase FLOAT NOT NULL,
    base_upgrade_cost_increase FLOAT NOT NULL
);

CREATE INDEX item_upgrade_rules_source_item_id_idx ON item_upgrade_rules (source_item_id);