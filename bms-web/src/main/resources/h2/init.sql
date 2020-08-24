select 1 from dual;

-- insert into user (id, opversion, deleted, createdtime, updatedtime, name, realname, password, status) values (1, 0, 0, now(), now(), 'admin', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', 2);
--
-- insert into role (id, opversion, deleted, createdtime, updatedtime, name, enable) values (1, 0, 0, now(), now(), '管理员', 1);
-- insert into role (id, opversion, deleted, createdtime, updatedtime, name, enable) values (2, 0, 0, now(), now(), '普通用户', 1);
--
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (1, 0, 0, now(), now(), '新增图书', '/book/add');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (2, 0, 0, now(), now(), '修改图书', '/book/update');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (3, 0, 0, now(), now(), '删除图书', '/book/deleteById');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (4, 0, 0, now(), now(), '新增分类', '/category/add');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (5, 0, 0, now(), now(), '修改分类', '/category/update');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (6, 0, 0, now(), now(), '删除分类', '/category/deleteById');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (7, 0, 0, now(), now(), '新增权限', '/permission/add');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (8, 0, 0, now(), now(), '修改权限', '/permission/update');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (9, 0, 0, now(), now(), '删除权限', '/permission/deleteById');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (10, 0, 0, now(), now(), '新增角色', '/role/add');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (11, 0, 0, now(), now(), '修改角色', '/role/update');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (12, 0, 0, now(), now(), '删除角色', '/role/deleteById');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (13, 0, 0, now(), now(), '设置角色权限', '/role/setRolePermissions');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (14, 0, 0, now(), now(), '修改用户', '/user/update');
-- insert into permission (id, opversion, deleted, createdtime, updatedtime, name, url) values (15, 0, 0, now(), now(), '设置用户角色', '/user/setUserRoles');

-- insert into user_role (userId, roleId) values (1, 1);

-- insert into role_permission (roleId, permissionId) values (1, 1);
-- insert into role_permission (roleId, permissionId) values (1, 2);
-- insert into role_permission (roleId, permissionId) values (1, 3);
-- insert into role_permission (roleId, permissionId) values (1, 4);
-- insert into role_permission (roleId, permissionId) values (1, 5);
-- insert into role_permission (roleId, permissionId) values (1, 6);
-- insert into role_permission (roleId, permissionId) values (1, 7);
-- insert into role_permission (roleId, permissionId) values (1, 8);
-- insert into role_permission (roleId, permissionId) values (1, 9);
-- insert into role_permission (roleId, permissionId) values (1, 10);
-- insert into role_permission (roleId, permissionId) values (1, 11);
-- insert into role_permission (roleId, permissionId) values (1, 12);
-- insert into role_permission (roleId, permissionId) values (1, 13);
-- insert into role_permission (roleId, permissionId) values (1, 14);
-- insert into role_permission (roleId, permissionId) values (1, 15);

