# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table conversation (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_conversation primary key (id))
;

create table message (
  id                        integer not null,
  conversation_id           integer not null,
  message                   varchar(255),
  date                      timestamp,
  author_id                 integer,
  constraint pk_message primary key (id))
;

create table person (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_person primary key (id))
;


create table message_person (
  message_id                     integer not null,
  person_id                      integer not null,
  constraint pk_message_person primary key (message_id, person_id))
;

create table person_conversation (
  person_id                      integer not null,
  conversation_id                integer not null,
  constraint pk_person_conversation primary key (person_id, conversation_id))
;
create sequence conversation_seq;

create sequence message_seq;

create sequence person_seq;

alter table message add constraint fk_message_conversation_1 foreign key (conversation_id) references conversation (id) on delete restrict on update restrict;
create index ix_message_conversation_1 on message (conversation_id);
alter table message add constraint fk_message_author_2 foreign key (author_id) references person (id) on delete restrict on update restrict;
create index ix_message_author_2 on message (author_id);



alter table message_person add constraint fk_message_person_message_01 foreign key (message_id) references message (id) on delete restrict on update restrict;

alter table message_person add constraint fk_message_person_person_02 foreign key (person_id) references person (id) on delete restrict on update restrict;

alter table person_conversation add constraint fk_person_conversation_person_01 foreign key (person_id) references person (id) on delete restrict on update restrict;

alter table person_conversation add constraint fk_person_conversation_conver_02 foreign key (conversation_id) references conversation (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists conversation;

drop table if exists message;

drop table if exists message_person;

drop table if exists person;

drop table if exists person_conversation;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists conversation_seq;

drop sequence if exists message_seq;

drop sequence if exists person_seq;

