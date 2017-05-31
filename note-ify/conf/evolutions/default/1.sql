# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                            serial not null,
  username                      TEXT,
  fullname                      TEXT,
  password_hash                 TEXT,
  token                         TEXT,
  constraint pk_account primary key (id)
);

create table account_roles (
  account_id                    integer not null,
  role_id                       integer not null,
  constraint pk_account_roles primary key (account_id,role_id)
);

create table note (
  id                            serial not null,
  owner_id                      integer,
  text                          TEXT,
  parent_id                     integer,
  constraint pk_note primary key (id)
);

create table permission (
  id                            serial not null,
  name                          varchar(255),
  constraint pk_permission primary key (id)
);

create table role (
  id                            serial not null,
  name                          varchar(255),
  constraint pk_role primary key (id)
);

create table role_permissions (
  role_id                       integer not null,
  permission_id                 integer not null,
  constraint pk_role_permissions primary key (role_id,permission_id)
);

alter table account_roles add constraint fk_account_roles_account foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_account_roles_account on account_roles (account_id);

alter table account_roles add constraint fk_account_roles_role foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_account_roles_role on account_roles (role_id);

alter table note add constraint fk_note_owner_id foreign key (owner_id) references account (id) on delete restrict on update restrict;
create index ix_note_owner_id on note (owner_id);

alter table note add constraint fk_note_parent_id foreign key (parent_id) references note (id) on delete restrict on update restrict;
create index ix_note_parent_id on note (parent_id);

alter table role_permissions add constraint fk_role_permissions_role foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_role_permissions_role on role_permissions (role_id);

alter table role_permissions add constraint fk_role_permissions_permission foreign key (permission_id) references permission (id) on delete restrict on update restrict;
create index ix_role_permissions_permission on role_permissions (permission_id);


# --- !Downs

alter table if exists account_roles drop constraint if exists fk_account_roles_account;
drop index if exists ix_account_roles_account;

alter table if exists account_roles drop constraint if exists fk_account_roles_role;
drop index if exists ix_account_roles_role;

alter table if exists note drop constraint if exists fk_note_owner_id;
drop index if exists ix_note_owner_id;

alter table if exists note drop constraint if exists fk_note_parent_id;
drop index if exists ix_note_parent_id;

alter table if exists role_permissions drop constraint if exists fk_role_permissions_role;
drop index if exists ix_role_permissions_role;

alter table if exists role_permissions drop constraint if exists fk_role_permissions_permission;
drop index if exists ix_role_permissions_permission;

drop table if exists account cascade;

drop table if exists account_roles cascade;

drop table if exists note cascade;

drop table if exists permission cascade;

drop table if exists role cascade;

drop table if exists role_permissions cascade;

