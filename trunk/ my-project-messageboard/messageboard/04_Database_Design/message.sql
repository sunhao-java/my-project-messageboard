prompt PL/SQL Developer import file
prompt Created on 2011年12月22日 by sunhao
set feedback off
set define off
prompt Dropping T_MESSAGE_ADMIN...
drop table T_MESSAGE_ADMIN cascade constraints;
prompt Dropping T_MESSAGE_USER...
drop table T_MESSAGE_USER cascade constraints;
prompt Creating T_MESSAGE_ADMIN...
create table T_MESSAGE_ADMIN
(
  PK_ID       NUMBER(19) not null,
  USERNAME    VARCHAR2(100 CHAR),
  PASSWORD    VARCHAR2(100 CHAR),
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

prompt Disabling triggers for T_MESSAGE_ADMIN...
alter table T_MESSAGE_ADMIN disable all triggers;
prompt Disabling triggers for T_MESSAGE_USER...
alter table T_MESSAGE_USER disable all triggers;
prompt Loading T_MESSAGE_ADMIN...
insert into T_MESSAGE_ADMIN (PK_ID, USERNAME, PASSWORD, DELETE_FLAG)
values (1, 'sunhao', '123', 0);
commit;
prompt 1 records loaded
prompt Loading T_MESSAGE_USER...
prompt Table is empty
prompt Enabling triggers for T_MESSAGE_ADMIN...
alter table T_MESSAGE_ADMIN enable all triggers;
prompt Enabling triggers for T_MESSAGE_USER...
alter table T_MESSAGE_USER enable all triggers;
set feedback on
set define on
prompt Done.
