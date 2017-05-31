TRUNCATE TABLE note RESTART IDENTITY CASCADE;
TRUNCATE account_roles RESTART IDENTITY CASCADE;
TRUNCATE role_permissions RESTART IDENTITY CASCADE;
TRUNCATE account RESTART IDENTITY CASCADE;
TRUNCATE role RESTART IDENTITY CASCADE;
TRUNCATE permission RESTART IDENTITY CASCADE;

INSERT INTO account(username, fullname, password_hash) VALUES
('a', 'Voicu', 'CA978112CA1BBDCAFAC231B39A23DC4DA786EFF8147C4E72B9807785AFEE48BB')
RETURNING id;

INSERT INTO role (name) VALUES ('user') RETURNING id;

INSERT INTO permission (name) VALUES ('note') RETURNING id;

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);

INSERT INTO account_roles (account_id, role_id) VALUES (1, 1);

INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 1', NULL);
INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 4', NULL);
INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 2', 1);
INSERT INTO note (owner_id, text, parent_id) VALUES (1, 'note 3', 1);
