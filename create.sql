# create database house;
#
# create user 'house'@'%' identified by 'P4$F5/D*Fd+4%8j)A1@T';
#
# grant select, insert, update on house.* to 'house'@'%' with grant option;
#
# create user 'house-cms'@'localhost' identified by 'Gk05)%AfBdfD%(067F!B';
#
# grant select, insert, update, delete on house.* to 'house-cms'@'localhost' with grant option;
#
# use house;

DROP TABLE IF EXISTS menu;
CREATE TABLE menu
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    parent_id   BIGINT       NOT NULL COMMENT '父级ID',
    type        INT          not null comment '类型: 1.目录, 2.菜单, 3.接口',
    name        VARCHAR(32)  NOT NULL COMMENT '名称',
    icon        varchar(128) not null DEFAULT '' comment '图标',
    path        VARCHAR(255) NOT NULL COMMENT '路径',
    sort_by     INT          NOT NULL COMMENT '排序',
    state       INT          NOT NULL COMMENT '状态, 0: 禁用, 1: 启用',
    remark      TEXT         NOT NULL COMMENT '备注',
    create_by   VARCHAR(32)  NOT NULL COMMENT '操作人员',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '操作人员',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB,
  CHARSET = UTF8MB4, COMMENT '角色菜单表';

INSERT INTO menu(id, parent_id, type, name, path, sort_by, state, remark, create_by)
VALUES (1, 0, 1, '菜单管理', '', 2, 1, '菜单管理', 'admin'),
       (2, 17, 2, '菜单列表', '/menu/index.html', 1, 1, '菜单列表', 'admin'),
       (3, 18, 3, '菜单查询', '/menu/query', 1, 1, '菜单查询', 'admin'),
       (4, 18, 3, '添加菜单', '/menu/add', 2, 1, '添加菜单', 'admin'),
       (5, 18, 3, '更新菜单信息', '/menu/update', 3, 1, '更新菜单信息', 'admin'),
       (6, 18, 3, '更改菜单状态', '/menu/change/state', 4, 1, '更改菜单状态', 'admin'),
       (7, 18, 3, '删除菜单', '/menu/delete', 5, 1, '删除应用', 'admin'),
       (8, 0, 1, '管理员', '', 3, 1, '管理员', 'admin'),
       (9, 24, 2, '角色列表', '/admin/role/index.html', 1, 1, '角色列表', 'admin'),
       (10, 25, 3, '角色查询', '/admin/role/query', 1, 1, '角色查询', 'admin'),
       (11, 25, 3, '添加角色', '/admin/role/add', 2, 1, '添加角色', 'admin'),
       (12, 25, 3, '更新角色信息', '/admin/role/update', 3, 1, '更新角色信息', 'admin'),
       (13, 25, 3, '更改角色状态', '/admin/role/change/state', 4, 1, '更改角色状态', 'admin'),
       (14, 25, 3, '删除角色', '/admin/role/delete', 5, 1, '删除角色', 'admin'),
       (15, 25, 3, '角色菜单查询', '/admin/role/menu/relation', 6, 1, '角色菜单查询', 'admin'),
       (16, 25, 3, '角色添加菜单', '/admin/role/menu/add', 7, 1, '角色添加菜单', 'admin'),
       (17, 25, 3, '角色删除菜单', '/admin/role/menu/delete', 8, 1, '角色删除菜单', 'admin'),
       (18, 24, 2, '管理员列表', '/admin/index.html', 2, 1, '管理员列表', 'admin'),
       (19, 34, 3, '管理员查询', '/admin/query', 1, 1, '管理员查询', 'admin'),
       (20, 34, 3, '添加管理员', '/admin/add', 2, 1, '添加管理员', 'admin'),
       (21, 34, 3, '更新管理员信息', '/admin/update', 3, 1, '更新管理员信息', 'admin'),
       (22, 34, 3, '更改管理员密码', '/admin/change/password', 4, 1, '更改管理员密码', 'admin'),
       (23, 34, 3, '更改管理员角色', '/admin/change/role', 5, 1, '更改管理员角色', 'admin'),
       (24, 34, 3, '更改管理员状态', '/admin/change/state', 6, 1, '更改管理员状态', 'admin'),
       (25, 34, 3, '删除管理员', '/admin/delete', 7, 1, '删除管理员', 'admin'),
       (1000, 0, 1, '设置', '', 1000, 1, '设置', 'admin'),
       (1001, 1000, 2, '详细信息', '/setting/detail.html', 1001, 1, '详细信息', 'admin'),
       (1002, 1001, 3, '修改信息', '/setting/change/detail', 1, 1, '更新信息', 'admin'),
       (1003, 1000, 2, '修改密码', '/setting/change/password.html', 1002, 1, '修改密码', 'admin'),
       (1004, 1003, 3, '修改密码', '/setting/change/Password', 1, 1, '更新信息', 'admin');


DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    id          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(32) NOT NULL COMMENT '名称',
    state       INT         NOT NULL COMMENT '状态, 0: 禁用, 1: 启用',
    remark      TEXT        NOT NULL COMMENT '备注',
    create_by   VARCHAR(32) NOT NULL COMMENT '操作人员',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人员',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB,
  CHARSET = UTF8MB4, COMMENT '管理员角色表';

INSERT INTO role (id, name, state, remark, create_by)
VALUES (1, '管理员', 1, '管理员', 'system');

DROP TABLE IF EXISTS role_menu_relation;
CREATE TABLE role_menu_relation
(
    id      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    UNIQUE KEY (role_id, menu_id)
) ENGINE = INNODB,
  CHARSET = UTF8MB4, COMMENT '角色菜单表';

DROP TABLE IF EXISTS admin;
CREATE TABLE admin
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role_id     BIGINT       NOT NULL COMMENT '角色ID',
    username    VARCHAR(32)  NOT NULL COMMENT '用户名',
    password    CHAR(64)     NOT NULL COMMENT '密码',
    nickname    VARCHAR(32)  NOT NULL COMMENT '昵称',
    avatar      varchar(255) NOT NULL COMMENT '头像',
    email       VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '邮箱',
    mobile      VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '手机号',
    state       INT          NOT NULL COMMENT '状态, 0: 禁用, 1: 启用',
    remark      TEXT         NOT NULL COMMENT '备注',
    create_by   VARCHAR(32)  NOT NULL COMMENT '操作人员',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '操作人员',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB,
  CHARSET = UTF8MB4, COMMENT '管理员表';

INSERT INTO admin (id, role_id, username, password, nickname, avatar, email, mobile, state, remark, create_by)
VALUES (1, 1, 'admin', '$2a$10$LQpCPRBlEDa1fMcFz.eezugW.Q7/PbNE1.1dZgxxTfo/2klzH6g0i', 'admin', '', '', '', 1, '管理员', 'system');

CREATE TABLE application
(
    id          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(32) NOT NULL COMMENT '名称',
    profile     VARCHAR(32) NOT NULL COMMENT '环境',
    label       VARCHAR(32) NOT NULL COMMENT '标签',
    state       INT         NOT NULL COMMENT '状态, 0: 禁用, 1: 启用',
    remark      TEXT        NOT NULL COMMENT '备注',
    create_by   VARCHAR(32) NOT NULL COMMENT '操作人员',
    create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人员',
    update_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB,
  CHARSET = UTF8MB4 COMMENT '应用表';

create table application_config
(
    id          bigint       not null primary key auto_increment,
    `key`       varchar(255) not null comment '参数名',
    value       text         not null comment '参数值',
    state       INT          NOT NULL COMMENT '状态, 0: 禁用, 1: 启用',
    remark      TEXT         NOT NULL COMMENT '备注',
    create_by   VARCHAR(32)  NOT NULL COMMENT '操作人员',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '操作人员',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
) engine = INNODB,
  charset = UTF8MB4 comment '应用配置表';

create table application_config_relation
(
    id             bigint not null primary key auto_increment,
    application_id bigint not null comment '应用ID',
    config_id      bigint not null comment '配置ID',
    unique key (application_id, config_id)
) engine = INNODB,
  charset = UTF8MB4 comment '应用配置关联表';

