CREATE DATABASE tradezilla;
USE tradezilla;

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(100) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

-- password it the word password
INSERT INTO users(username,password,enabled) VALUES ('jaf','$2a$10$D3NFo60d9hHEcMksQUV4ruYLdWGtBcFUF0G34M1fP62JgcAIgNhRe', true);
INSERT INTO users(username,password,enabled) VALUES ('alex','$2a$10$D3NFo60d9hHEcMksQUV4ruYLdWGtBcFUF0G34M1fP62JgcAIgNhRe', true);

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO user_roles (username, role) VALUES ('jaf', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('jaf', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('alex', 'ROLE_USER');

CREATE TABLE trade_items (
  id int(11) NOT NULL AUTO_INCREMENT,
  itemName varchar(45) NOT NULL,
  username varchar(45) NOT NULL,
  description varchar(1000) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uni_item_id (id, username),
  CONSTRAINT fk_user_item FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO trade_items (itemname, username, description) VALUES ('test item 001', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 002', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 003', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 004', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 005', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 006', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 007', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 008', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 009', 'jaf', 'description');
INSERT INTO trade_items (itemname, username, description) VALUES ('test item 010', 'jaf', 'description');
