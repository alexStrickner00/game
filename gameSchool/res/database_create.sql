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
    shooting TINYINT NOT NULL,
    projectileId INT,
    image MEDIUMBLOB,
    costs INT NOT NULL,
    PRIMARY KEY(entityId)
);

CREATE TABLE projectile(
	projectileId INT NOT NULL auto_increment,
	projectile_name VARCHAR(255) NOT NULL,
    flyspeed INT NOT NULL,
    image MEDIUMBLOB,
    PRIMARY KEY(projectileId)
);

ALTER TABLE spielfigur
	ADD FOREIGN KEY(projectileId) REFERENCES projectile(projectileId);