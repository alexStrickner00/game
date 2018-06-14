DROP DATABASE IF EXISTS bloodtime;
CREATE DATABASE IF NOT EXISTS bloodtime;
USE bloodtime;

CREATE TABLE spielfigur(
	entityId INT NOT NULL auto_increment,
    entity_name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    health INT NOT NULL,
    delay INT NOT NULL,
    damage INT NOT NULL,
    speed INT NOT NULL,
    image MEDIUMBLOB,
    costs INT NOT NULL,
    PRIMARY KEY(entityId)
);

CREATE TABLE stats(
	timestamp TIMESTAMP,
	xp INT NOT NULL,
	gameTime VARCHAR(255) NOT NULL
);