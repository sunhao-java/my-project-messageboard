----------------------------------------------
-- Export file for user MESSAGE             --
-- Created by sunhao on 2012-2-10, 23:52:52 --
----------------------------------------------

spool message.log

prompt
prompt Creating table T_LOGIN_HISTORY
prompt ==============================
prompt
create table MESSAGE.T_LOGIN_HISTORY
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
comment on column MESSAGE.T_LOGIN_HISTORY.LOGIN_USER_ID
  is '登录者ID';
comment on column MESSAGE.T_LOGIN_HISTORY.LOGIN_IP
  is '登录地IP';
comment on column MESSAGE.T_LOGIN_HISTORY.LOGIN_TIME
  is '登录时间';
comment on column MESSAGE.T_LOGIN_HISTORY.BROWSER
  is '登录所使用浏览器';
alter table MESSAGE.T_LOGIN_HISTORY
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

prompt
prompt Creating table T_MESSAGE_EVENT
prompt ==============================
prompt
create table MESSAGE.T_MESSAGE_EVENT
(
  PK_ID          NUMBER(19) not null,
  OPERATION_TYPE NUMBER(19),
  OPERATOR_ID    NUMBER(19),
  OWNER_ID       NUMBER(19),
  RESOURCE_TYPE  NUMBER(10),
  RESOURCE_ID    NUMBER(19),
  OPERATION_TIME TIMESTAMP(6),
  OPERATION_IP   VARCHAR2(255 CHAR),
  DESCRIPTION    VARCHAR2(4000 CHAR)
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
comment on column MESSAGE.T_MESSAGE_EVENT.OPERATION_TYPE
  is '操作类型';
comment on column MESSAGE.T_MESSAGE_EVENT.OPERATOR_ID
  is '操作者ID';
comment on column MESSAGE.T_MESSAGE_EVENT.OWNER_ID
  is '拥有者ID';
comment on column MESSAGE.T_MESSAGE_EVENT.RESOURCE_TYPE
  is '事件类型标识';
comment on column MESSAGE.T_MESSAGE_EVENT.RESOURCE_ID
  is '被操作对象的ID';
comment on column MESSAGE.T_MESSAGE_EVENT.OPERATION_TIME
  is '操作发生时间';
comment on column MESSAGE.T_MESSAGE_EVENT.OPERATION_IP
  is '操作发生地IP';
comment on column MESSAGE.T_MESSAGE_EVENT.DESCRIPTION
  is '操作事件的描述';
alter table MESSAGE.T_MESSAGE_EVENT
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

prompt
prompt Creating table T_MESSAGE_INFO
prompt =============================
prompt
create table MESSAGE.T_MESSAGE_INFO
(
  PK_ID           NUMBER(19) not null,
  DESCRIPTION     CLOB,
  MODIFY_USERID   NUMBER(19),
  MODIFY_USERNAME VARCHAR2(255 CHAR),
  MODIFY_DATE     TIMESTAMP(6)
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
comment on column MESSAGE.T_MESSAGE_INFO.DESCRIPTION
  is '留言板描述';
comment on column MESSAGE.T_MESSAGE_INFO.MODIFY_USERID
  is '修改的用户的ID';
comment on column MESSAGE.T_MESSAGE_INFO.MODIFY_USERNAME
  is '修改的用户的truename';
comment on column MESSAGE.T_MESSAGE_INFO.MODIFY_DATE
  is '修改的时间';
alter table MESSAGE.T_MESSAGE_INFO
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

prompt
prompt Creating table T_MESSAGE_MSG
prompt ============================
prompt
create table MESSAGE.T_MESSAGE_MSG
(
  PK_ID           NUMBER(19) not null,
  CONTENT         CLOB,
  TITLE           VARCHAR2(255 CHAR),
  CREATE_DATE     TIMESTAMP(6),
  IP              VARCHAR2(20 CHAR),
  CREATE_USERID   NUMBER(19),
  DELETE_FLAG     NUMBER(19),
  CREATE_USERNAME VARCHAR2(255 CHAR),
  IS_AUDIT        NUMBER(19)
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
comment on column MESSAGE.T_MESSAGE_MSG.CONTENT
  is '留言内容';
comment on column MESSAGE.T_MESSAGE_MSG.TITLE
  is '标题';
comment on column MESSAGE.T_MESSAGE_MSG.CREATE_DATE
  is '留言时间';
comment on column MESSAGE.T_MESSAGE_MSG.IP
  is '留言者IP地址';
comment on column MESSAGE.T_MESSAGE_MSG.CREATE_USERID
  is '留言者ID';
comment on column MESSAGE.T_MESSAGE_MSG.DELETE_FLAG
  is '删除标识0未删除1已删除';
comment on column MESSAGE.T_MESSAGE_MSG.CREATE_USERNAME
  is '对应的留言者Name';
comment on column MESSAGE.T_MESSAGE_MSG.IS_AUDIT
  is '管理员审核通过标识：0未审核1已通过2未通过审核';
alter table MESSAGE.T_MESSAGE_MSG
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

prompt
prompt Creating table T_MESSAGE_REPLY
prompt ==============================
prompt
create table MESSAGE.T_MESSAGE_REPLY
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
comment on column MESSAGE.T_MESSAGE_REPLY.TITLE
  is '标题';
comment on column MESSAGE.T_MESSAGE_REPLY.REPLY_CONTENT
  is '回复内容';
comment on column MESSAGE.T_MESSAGE_REPLY.REPLY_DATE
  is '回复时间';
comment on column MESSAGE.T_MESSAGE_REPLY.REPLY_USERID
  is '回复者ID';
comment on column MESSAGE.T_MESSAGE_REPLY.DELETE_FLAG
  is '删除标识0未删除1已删除';
comment on column MESSAGE.T_MESSAGE_REPLY.MESSAGE_ID
  is '此条回复对应的留言ID';
alter table MESSAGE.T_MESSAGE_REPLY
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

prompt
prompt Creating table T_MESSAGE_USER
prompt =============================
prompt
create table MESSAGE.T_MESSAGE_USER
(
  PK_ID         NUMBER(19) not null,
  USER_NAME     VARCHAR2(255 CHAR),
  PASSWORD      VARCHAR2(255 CHAR),
  CREATE_DATE   TIMESTAMP(6),
  EMAIL         VARCHAR2(255 CHAR),
  PHONE_NUM     VARCHAR2(255 CHAR),
  QQ            VARCHAR2(20 CHAR),
  HEAD_IMAGE    VARCHAR2(255 CHAR),
  ADDRESS       VARCHAR2(255 CHAR),
  DELETE_FLAG   NUMBER(19),
  SEX           NUMBER(19),
  HOME_PAGE     VARCHAR2(500 CHAR),
  TRUE_NAME     VARCHAR2(255 CHAR),
  IS_ADMIN      NUMBER(19),
  IS_MAIL_CHECK NUMBER(19)
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
comment on column MESSAGE.T_MESSAGE_USER.USER_NAME
  is '用户名';
comment on column MESSAGE.T_MESSAGE_USER.PASSWORD
  is '密码(MD5加密)';
comment on column MESSAGE.T_MESSAGE_USER.CREATE_DATE
  is '创建时间';
comment on column MESSAGE.T_MESSAGE_USER.EMAIL
  is '邮箱地址';
comment on column MESSAGE.T_MESSAGE_USER.PHONE_NUM
  is '电话号码';
comment on column MESSAGE.T_MESSAGE_USER.QQ
  is 'QQ号码';
comment on column MESSAGE.T_MESSAGE_USER.HEAD_IMAGE
  is '注册用户的头像(记录头像图片的路径)';
comment on column MESSAGE.T_MESSAGE_USER.ADDRESS
  is '地址';
comment on column MESSAGE.T_MESSAGE_USER.DELETE_FLAG
  is '软删除，0未删除，1已删除';
comment on column MESSAGE.T_MESSAGE_USER.SEX
  is '性别0:不男不女;1: 男;2:女';
comment on column MESSAGE.T_MESSAGE_USER.HOME_PAGE
  is '注册用户的主页';
comment on column MESSAGE.T_MESSAGE_USER.TRUE_NAME
  is '真实姓名';
comment on column MESSAGE.T_MESSAGE_USER.IS_ADMIN
  is '是否是管理员的标识, 0不是管理员，1是管理员';
comment on column MESSAGE.T_MESSAGE_USER.IS_MAIL_CHECK
  is '是否已经邮箱验证过？ 1已验证，0未验证';
alter table MESSAGE.T_MESSAGE_USER
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

prompt
prompt Creating table T_MESSAGE_USER_PRIVACY
prompt =====================================
prompt
create table MESSAGE.T_MESSAGE_USER_PRIVACY
(
  USER_PKID NUMBER(19) not null,
  USRNAME   NUMBER(19),
  SEX       NUMBER(19),
  TRUENAME  NUMBER(19),
  EMAIL     NUMBER(19),
  PHONENUM  NUMBER(19),
  QQ        NUMBER(19),
  HOMEPAGE  NUMBER(19),
  ADDRESS   NUMBER(19)
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
comment on table MESSAGE.T_MESSAGE_USER_PRIVACY
  is '用户隐私设置';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.USER_PKID
  is '对应的用户ID';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.USRNAME
  is '用户名';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.SEX
  is '性别';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.TRUENAME
  is '真实姓名';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.EMAIL
  is '邮箱';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.PHONENUM
  is '电话号码';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.QQ
  is 'QQ';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.HOMEPAGE
  is '主页';
comment on column MESSAGE.T_MESSAGE_USER_PRIVACY.ADDRESS
  is '地址';
alter table MESSAGE.T_MESSAGE_USER_PRIVACY
  add primary key (USER_PKID)
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

prompt
prompt Creating sequence SEQ_MESSAGE_EVENT
prompt ===================================
prompt
create sequence MESSAGE.SEQ_MESSAGE_EVENT
minvalue 1
maxvalue 999999999999999999999999999
start with 221
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MESSAGE_INFO
prompt ==================================
prompt
create sequence MESSAGE.SEQ_MESSAGE_INFO
minvalue 1
maxvalue 999999999999999999999999999
start with 81
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MESSAGE_LOGINHISTORY
prompt ==========================================
prompt
create sequence MESSAGE.SEQ_MESSAGE_LOGINHISTORY
minvalue 1
maxvalue 999999999999999999999999999
start with 721
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MESSAGE_MSG
prompt =================================
prompt
create sequence MESSAGE.SEQ_MESSAGE_MSG
minvalue 1
maxvalue 999999999999999999999999999
start with 361
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MESSAGE_REPLY
prompt ===================================
prompt
create sequence MESSAGE.SEQ_MESSAGE_REPLY
minvalue 1
maxvalue 999999999999999999999999999
start with 141
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MESSAGE_USER
prompt ==================================
prompt
create sequence MESSAGE.SEQ_MESSAGE_USER
minvalue 1
maxvalue 999999999999999999999999999
start with 201
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_USER_PRIVACY
prompt ==================================
prompt
create sequence MESSAGE.SEQ_USER_PRIVACY
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;


spool off
