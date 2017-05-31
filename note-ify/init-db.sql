TRUNCATE TABLE note RESTART IDENTITY CASCADE;
TRUNCATE account_roles RESTART IDENTITY CASCADE;
TRUNCATE role_permissions RESTART IDENTITY CASCADE;
TRUNCATE account RESTART IDENTITY CASCADE;
TRUNCATE role RESTART IDENTITY CASCADE;
TRUNCATE permission RESTART IDENTITY CASCADE;

INSERT INTO account(username, fullname, password_hash) VALUES
('a', 'Voicu', 'CA978112CA1BBDCAFAC231B39A23DC4DA786EFF8147C4E72B9807785AFEE48BB');

INSERT INTO account(username, fullname, password_hash) VALUES
('admin', 'Admin', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918');

INSERT INTO role (name) VALUES ('user');
INSERT INTO role (name) VALUES ('admin');

INSERT INTO permission (name) VALUES ('note');
INSERT INTO permission (name) VALUES ('stats');

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2);

INSERT INTO account_roles (account_id, role_id) VALUES (1, 1);
INSERT INTO account_roles (account_id, role_id) VALUES (2, 2);

INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 1', NULL);
INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 4', NULL);
INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 2', 1);
INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 3', 1);
