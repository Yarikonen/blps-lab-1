DELETE FROM role_privilege;
DELETE FROM privilege;
DELETE FROM role;

INSERT INTO role (id, name) VALUES (1, 'CUSTOMER');
INSERT INTO role (id, name) VALUES (2, 'ADMIN');

INSERT INTO privilege (id, name) VALUES (1, 'EDIT_ITEMS');
INSERT INTO privilege (id, name) VALUES (2, 'CREATE_ORDER');
INSERT INTO privilege (id, name) VALUES (3, 'EDIT_WAREHOUSE');
INSERT INTO privilege (id, name) VALUES (4, 'CREATE_PICKUP_POINT');
INSERT INTO privilege (id, name) VALUES (5, 'EDIT_PICKUP_POINT');
INSERT INTO privilege (id, name) VALUES (6, 'VIEW_ORDERS');
INSERT INTO privilege (id, name) VALUES (7, 'DELETE_ORDER');

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 5);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 6);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 7);