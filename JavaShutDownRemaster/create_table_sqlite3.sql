--
-- File generated with SQLiteStudio v3.2.1 on út úno 25 21:43:48 2020
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: action
CREATE TABLE [action] IF NOT EXISTS (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    name                TEXT    NOT NULL
                                UNIQUE,
    class_name          TEXT    UNIQUE
                                NOT NULL,
    description         TEXT,
    parameters_count    INTEGER NOT NULL
                                DEFAULT (0) 
                                CHECK ( (parameters_count >= 0) ),
    is_producing_result BOOLEAN NOT NULL
                                CHECK ( (is_producing_result IN (0, 1) ) ) 
                                DEFAULT (0) 
);


-- Table: scheduled_action
CREATE TABLE scheduled_action IF NOT EXISTS (
    id                 INTEGER  PRIMARY KEY AUTOINCREMENT,
    action_id          INTEGER  NOT NULL
                                REFERENCES [action] (id) ON DELETE CASCADE,
    goal_time          DATETIME NOT NULL,
    result             TEXT,
    status             TEXT     REFERENCES scheduled_action_status_enum (status) ON DELETE RESTRICT
                                NOT NULL
                                DEFAULT SCHEDULED,
    created_time       DATETIME NOT NULL
                                DEFAULT (CURRENT_TIMESTAMP),
    last_modified_time DATETIME NOT NULL
                                DEFAULT (CURRENT_TIMESTAMP) 
);


-- Table: scheduled_action_parameter
CREATE TABLE scheduled_action_parameter IF NOT EXISTS (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    scheduled_action_id INTEGER REFERENCES scheduled_action (id) ON DELETE CASCADE
                                NOT NULL,
    value               TEXT
);


-- Table: scheduled_action_status_enum
CREATE TABLE scheduled_action_status_enum IF NOT EXISTS (
    status TEXT PRIMARY KEY
);


-- Trigger: tr_scheduled_action_lastmodified_time
CREATE TRIGGER tr_scheduled_action_lastmodified_time IF NOT EXISTS
         AFTER UPDATE OF goal_time,
                         result,
                         status
            ON scheduled_action
BEGIN
    UPDATE scheduled_action
       SET last_modified_time = CURRENT_TIMESTAMP
     WHERE old.id = id;
END;


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
