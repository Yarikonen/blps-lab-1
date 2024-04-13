DELETE FROM role_privilege;
DELETE FROM privilege;
DELETE FROM role;

INSERT INTO role (id, name) VALUES (1, 'CUSTOMER');
INSERT INTO role (id, name) VALUES (2, 'ADMIN');

INSERT INTO privilege (id, name) VALUES (1, 'EDIT_PRODUCTS');
INSERT INTO privilege (id, name) VALUES (2, 'BUY_PRODUCTS');

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 2);
