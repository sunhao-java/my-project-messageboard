prompt PL/SQL Developer import file
prompt Created on 2012年1月7日 by sunhao
set feedback off
set define off
prompt Dropping T_LOGIN_HISTORY...
drop table T_LOGIN_HISTORY cascade constraints;
prompt Dropping T_MESSAGE_ADMIN...
drop table T_MESSAGE_ADMIN cascade constraints;
prompt Dropping T_MESSAGE_INFO...
drop table T_MESSAGE_INFO cascade constraints;
prompt Dropping T_MESSAGE_MSG...
drop table T_MESSAGE_MSG cascade constraints;
prompt Dropping T_MESSAGE_REPLY...
drop table T_MESSAGE_REPLY cascade constraints;
prompt Dropping T_MESSAGE_USER...
drop table T_MESSAGE_USER cascade constraints;
prompt Creating T_LOGIN_HISTORY...
create table T_LOGIN_HISTORY
(
  PK_ID         NUMBER(19) not null,
  LOGIN_USER_ID NUMBER(19),
  LOGIN_IP      VARCHAR2(255 CHAR),
  LOGIN_TIME    TIMESTAMP(6),
  BROWSER       VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_LOGIN_HISTORY.LOGIN_USER_ID
  is '登录者ID';
comment on column T_LOGIN_HISTORY.LOGIN_IP
  is '登录地IP';
comment on column T_LOGIN_HISTORY.LOGIN_TIME
  is '登录时间';
comment on column T_LOGIN_HISTORY.BROWSER
  is '登录所使用浏览器';
alter table T_LOGIN_HISTORY
  add primary key (PK_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating T_MESSAGE_ADMIN...
create table T_MESSAGE_ADMIN
(
  PK_ID       NUMBER(19) not null,
  USERNAME    VARCHAR2(255 CHAR),
  PASSWORD    VARCHAR2(255 CHAR),
  DELETE_FLAG NUMBER(19)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_MESSAGE_ADMIN.USERNAME
  is '管理员用户名';
comment on column T_MESSAGE_ADMIN.PASSWORD
  is '管理员密码';
comment on column T_MESSAGE_ADMIN.DELETE_FLAG
  is '软删除标记，0未删除，1已删除';
alter table T_MESSAGE_ADMIN
  add primary key (PK_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating T_MESSAGE_INFO...
create table T_MESSAGE_INFO
(
  PK_ID       NUMBER(19) not null,
  DESCRIPTION VARCHAR2(4000 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_MESSAGE_INFO.DESCRIPTION
  is '留言板描述';
alter table T_MESSAGE_INFO
  add primary key (PK_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating T_MESSAGE_MSG...
create table T_MESSAGE_MSG
(
  PK_ID         NUMBER(19) not null,
  CONTENT       VARCHAR2(4000 CHAR),
  TITLE         VARCHAR2(255 CHAR),
  CREATE_DATE   TIMESTAMP(6),
  IP            VARCHAR2(20 CHAR),
  CREATE_USERID NUMBER(19),
  DELETE_FLAG   NUMBER(19)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_MESSAGE_MSG.CONTENT
  is '留言内容';
comment on column T_MESSAGE_MSG.TITLE
  is '标题';
comment on column T_MESSAGE_MSG.CREATE_DATE
  is '留言时间';
comment on column T_MESSAGE_MSG.IP
  is '留言者IP地址';
comment on column T_MESSAGE_MSG.CREATE_USERID
  is '留言者ID';
comment on column T_MESSAGE_MSG.DELETE_FLAG
  is '删除标识0未删除1已删除';
alter table T_MESSAGE_MSG
  add primary key (PK_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating T_MESSAGE_REPLY...
create table T_MESSAGE_REPLY
(
  PK_ID         NUMBER(19) not null,
  TITLE         VARCHAR2(255 CHAR),
  REPLY_CONTENT VARCHAR2(4000 CHAR),
  REPLY_DATE    TIMESTAMP(6),
  REPLY_USERID  NUMBER(19),
  DELETE_FLAG   NUMBER(19),
  MESSAGE_ID    NUMBER(19)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_MESSAGE_REPLY.TITLE
  is '标题';
comment on column T_MESSAGE_REPLY.REPLY_CONTENT
  is '回复内容';
comment on column T_MESSAGE_REPLY.REPLY_DATE
  is '回复时间';
comment on column T_MESSAGE_REPLY.REPLY_USERID
  is '回复者ID';
comment on column T_MESSAGE_REPLY.DELETE_FLAG
  is '删除标识0未删除1已删除';
comment on column T_MESSAGE_REPLY.MESSAGE_ID
  is '所回复的留言ID';
alter table T_MESSAGE_REPLY
  add primary key (PK_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table T_MESSAGE_REPLY
  add constraint FKF52EEFC7AF8B6318 foreign key (MESSAGE_ID)
  references T_MESSAGE_MSG (PK_ID);

prompt Creating T_MESSAGE_USER...
create table T_MESSAGE_USER
(
  PK_ID       NUMBER(19) not null,
  USER_NAME   VARCHAR2(255 CHAR),
  PASSWORD    VARCHAR2(255 CHAR),
  CREATE_DATE TIMESTAMP(6),
  EMAIL       VARCHAR2(255 CHAR),
  PHONE_NUM   VARCHAR2(255 CHAR),
  QQ          VARCHAR2(20 CHAR),
  HEAD_IMAGE  VARCHAR2(255 CHAR),
  ADDRESS     VARCHAR2(255 CHAR),
  DELETE_FLAG NUMBER(19),
  SEX         NUMBER(19),
  HOME_PAGE   VARCHAR2(500 CHAR),
  TRUE_NAME   VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column T_MESSAGE_USER.USER_NAME
  is '用户名';
comment on column T_MESSAGE_USER.PASSWORD
  is '密码(MD5加密)';
comment on column T_MESSAGE_USER.CREATE_DATE
  is '创建时间';
comment on column T_MESSAGE_USER.EMAIL
  is '邮箱地址';
comment on column T_MESSAGE_USER.PHONE_NUM
  is '电话号码';
comment on column T_MESSAGE_USER.QQ
  is 'QQ号码';
comment on column T_MESSAGE_USER.HEAD_IMAGE
  is '注册用户的头像(记录头像图片的路径)';
comment on column T_MESSAGE_USER.ADDRESS
  is '地址';
comment on column T_MESSAGE_USER.DELETE_FLAG
  is '软删除，0未删除，1已删除';
comment on column T_MESSAGE_USER.SEX
  is '性别0:不男不女;1: 男;2:女';
comment on column T_MESSAGE_USER.HOME_PAGE
  is '注册用户的主页';
comment on column T_MESSAGE_USER.TRUE_NAME
  is '真实姓名';
alter table T_MESSAGE_USER
  add primary key (PK_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for T_LOGIN_HISTORY...
alter table T_LOGIN_HISTORY disable all triggers;
prompt Disabling triggers for T_MESSAGE_ADMIN...
alter table T_MESSAGE_ADMIN disable all triggers;
prompt Disabling triggers for T_MESSAGE_INFO...
alter table T_MESSAGE_INFO disable all triggers;
prompt Disabling triggers for T_MESSAGE_MSG...
alter table T_MESSAGE_MSG disable all triggers;
prompt Disabling triggers for T_MESSAGE_REPLY...
alter table T_MESSAGE_REPLY disable all triggers;
prompt Disabling triggers for T_MESSAGE_USER...
alter table T_MESSAGE_USER disable all triggers;
prompt Disabling foreign key constraints for T_MESSAGE_REPLY...
alter table T_MESSAGE_REPLY disable constraint FKF52EEFC7AF8B6318;
prompt Loading T_LOGIN_HISTORY...
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (1, 41, '127.0.0.1', to_timestamp('04-01-2012 20:48:02.562000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (2, 41, '127.0.0.1', to_timestamp('04-01-2012 21:22:49.843000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (3, 41, '127.0.0.1', to_timestamp('04-01-2012 21:31:12.062000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (4, 41, '127.0.0.1', to_timestamp('04-01-2012 21:34:52.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET4.0C; 360SE)');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (5, 41, '127.0.0.1', to_timestamp('04-01-2012 21:37:27.328000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET4.0C)');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (6, 41, '127.0.0.1', to_timestamp('04-01-2012 21:45:02.421000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (22, 41, '127.0.0.1', to_timestamp('05-01-2012 21:21:48.281000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (41, 41, '127.0.0.1', to_timestamp('07-01-2012 18:07:07.390000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (42, 41, '127.0.0.1', to_timestamp('07-01-2012 18:39:15.593000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (43, 41, '127.0.0.1', to_timestamp('07-01-2012 18:43:53.578000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (44, 41, '127.0.0.1', to_timestamp('07-01-2012 18:50:15.671000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (45, 41, '127.0.0.1', to_timestamp('07-01-2012 20:37:19.265000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (46, 41, '127.0.0.1', to_timestamp('07-01-2012 20:38:34.515000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (47, 41, '127.0.0.1', to_timestamp('07-01-2012 20:44:18.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (48, 41, '127.0.0.1', to_timestamp('07-01-2012 21:05:45.250000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (49, 41, '127.0.0.1', to_timestamp('07-01-2012 21:44:29.328000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (50, 41, '127.0.0.1', to_timestamp('07-01-2012 21:44:34.765000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (51, 41, '127.0.0.1', to_timestamp('07-01-2012 21:55:14.796000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (52, 41, '127.0.0.1', to_timestamp('07-01-2012 21:57:23.218000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (53, 41, '127.0.0.1', to_timestamp('07-01-2012 21:58:03.062000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (54, 41, '127.0.0.1', to_timestamp('07-01-2012 22:05:45.218000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (55, 41, '127.0.0.1', to_timestamp('07-01-2012 22:30:06.171000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (56, 41, '127.0.0.1', to_timestamp('07-01-2012 22:35:42.140000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (57, 41, '127.0.0.1', to_timestamp('07-01-2012 22:36:42.703000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (58, 41, '127.0.0.1', to_timestamp('07-01-2012 22:38:50.484000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (59, 41, '127.0.0.1', to_timestamp('07-01-2012 22:54:57.515000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (60, 41, '127.0.0.1', to_timestamp('07-01-2012 22:57:44.640000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (61, 61, '127.0.0.1', to_timestamp('07-01-2012 22:58:10.203000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (62, 41, '127.0.0.1', to_timestamp('07-01-2012 22:59:08.265000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (63, 41, '127.0.0.1', to_timestamp('07-01-2012 22:59:33.078000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (64, 61, '127.0.0.1', to_timestamp('07-01-2012 22:59:54.140000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (65, 41, '127.0.0.1', to_timestamp('07-01-2012 23:01:41.250000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (66, 81, '127.0.0.1', to_timestamp('07-01-2012 23:15:07.609000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (67, 81, '127.0.0.1', to_timestamp('07-01-2012 23:18:56.359000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (21, 41, '127.0.0.1', to_timestamp('05-01-2012 21:03:23.234000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (23, 41, '127.0.0.1', to_timestamp('05-01-2012 21:46:20.781000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (24, 41, '127.0.0.1', to_timestamp('05-01-2012 21:57:35.296000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (25, 41, '127.0.0.1', to_timestamp('05-01-2012 22:06:52.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (26, 41, '127.0.0.1', to_timestamp('05-01-2012 22:49:54.375000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (27, 41, '127.0.0.1', to_timestamp('05-01-2012 23:07:46.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (28, 41, '127.0.0.1', to_timestamp('05-01-2012 23:14:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (29, 61, '127.0.0.1', to_timestamp('05-01-2012 23:28:48.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
insert into T_LOGIN_HISTORY (PK_ID, LOGIN_USER_ID, LOGIN_IP, LOGIN_TIME, BROWSER)
values (30, 61, '127.0.0.1', to_timestamp('05-01-2012 23:35:09.390000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1');
commit;
prompt 43 records loaded
prompt Loading T_MESSAGE_ADMIN...
prompt Table is empty
prompt Loading T_MESSAGE_INFO...
prompt Table is empty
prompt Loading T_MESSAGE_MSG...
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (21, 'hahahahahahhahahahaha', '我是孙昊', to_timestamp('07-01-2012 22:39:07.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 41, 0);
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (22, 'dscaca', 'ceshi', to_timestamp('07-01-2012 22:56:14.265000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 41, 0);
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (23, 'dsdscds', 'cds', to_timestamp('07-01-2012 23:00:19.468000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 61, 0);
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (2, '第一次' || chr(10) || '<h2>发表留言<img src="http://sunhao.wiscom.com.cn:8089/message/fckeditor/editor/images/smiley/jp/em04.gif" alt="" /', '发表留言', to_timestamp('29-12-2011 22:49:55.703000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 41, 0);
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (3, '<h3 style="color: Red;">发表留言</h3>' || chr(10) || '<h3 style="color: Red;">发表留言<img src="http://sunhao.wiscom.com.cn:8089/message/fckeditor/editor/images/smiley/qq/11.gif" alt="" /', '发表留言', to_timestamp('29-12-2011 22:51:11.968000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 41, 0);
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (4, 'alert(''发表留言失败，请稍候再试！'');', 'alert(''发表留言失败，请稍候再试！'');', to_timestamp('29-12-2011 22:53:00.500000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 41, 0);
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (5, 'alert(''发表留言失败，请稍候再试！'');', 'alert(''发表留言失败，请稍候再试！'');', to_timestamp('29-12-2011 22:53:38.453000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 41, 0);
insert into T_MESSAGE_MSG (PK_ID, CONTENT, TITLE, CREATE_DATE, IP, CREATE_USERID, DELETE_FLAG)
values (6, 'alert(''发表留言失败，请稍候再试！'');', 'alert(''发表留言失败，请稍候再试！'');', to_timestamp('29-12-2011 22:53:46.718000', 'dd-mm-yyyy hh24:mi:ss.ff'), '127.0.0.1', 41, 0);
commit;
prompt 8 records loaded
prompt Loading T_MESSAGE_REPLY...
prompt Table is empty
prompt Loading T_MESSAGE_USER...
insert into T_MESSAGE_USER (PK_ID, USER_NAME, PASSWORD, CREATE_DATE, EMAIL, PHONE_NUM, QQ, HEAD_IMAGE, ADDRESS, DELETE_FLAG, SEX, HOME_PAGE, TRUE_NAME)
values (81, 'jingtian', '8f73a641a3d0e0d616caea21ff7bebce', to_timestamp('07-01-2012 23:14:58.187000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'jingtian@sunhao.com', '13956465120', null, 'image/pic12.gif', null, 0, 2, null, '景甜');
insert into T_MESSAGE_USER (PK_ID, USER_NAME, PASSWORD, CREATE_DATE, EMAIL, PHONE_NUM, QQ, HEAD_IMAGE, ADDRESS, DELETE_FLAG, SEX, HOME_PAGE, TRUE_NAME)
values (61, '孙昊', '5847bcb4b355bac2a2b048f9d1722e86', to_timestamp('05-01-2012 23:28:06.015000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'sunhao.java@gmail.com', '13956465120', '139511111', 'image/pic5.gif', null, 0, 1, null, '孙昊');
insert into T_MESSAGE_USER (PK_ID, USER_NAME, PASSWORD, CREATE_DATE, EMAIL, PHONE_NUM, QQ, HEAD_IMAGE, ADDRESS, DELETE_FLAG, SEX, HOME_PAGE, TRUE_NAME)
values (41, 'sunhao', '4529e6dfac9d308b2924840935b016c7', to_timestamp('27-12-2011 21:13:21.906000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'sunhao0550@163.com', '15161472714', '867885140', 'image/pic5.gif', '江苏省南京市江宁区将军大道', 0, 1, 'http://sunhao.wiscom.com.cn:8089/message/', '孙昊');
commit;
prompt 3 records loaded
prompt Enabling foreign key constraints for T_MESSAGE_REPLY...
alter table T_MESSAGE_REPLY enable constraint FKF52EEFC7AF8B6318;
prompt Enabling triggers for T_LOGIN_HISTORY...
alter table T_LOGIN_HISTORY enable all triggers;
prompt Enabling triggers for T_MESSAGE_ADMIN...
alter table T_MESSAGE_ADMIN enable all triggers;
prompt Enabling triggers for T_MESSAGE_INFO...
alter table T_MESSAGE_INFO enable all triggers;
prompt Enabling triggers for T_MESSAGE_MSG...
alter table T_MESSAGE_MSG enable all triggers;
prompt Enabling triggers for T_MESSAGE_REPLY...
alter table T_MESSAGE_REPLY enable all triggers;
prompt Enabling triggers for T_MESSAGE_USER...
alter table T_MESSAGE_USER enable all triggers;
set feedback on
set define on
prompt Done.
