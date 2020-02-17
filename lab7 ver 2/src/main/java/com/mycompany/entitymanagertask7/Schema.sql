/**
 * Author:  Artur
 * Created: 17 февр. 2020 г.
 */
drop table if exists server,team,project,dev,director,computer,techquest,team_server,team_project,team_dev,team_director,dev_computer,project_techquest;
create table team (id serial PRIMARY KEY);
create table server (id serial PRIMARY KEY,description varchar(500));
create table project (id serial PRIMARY KEY,budget integer);
create table dev (id serial PRIMARY KEY,fullname varchar(100),birthdate date,salary integer);
create table director (id serial PRIMARY KEY,fullname text,birthdate date,salary integer);
create table computer (id serial PRIMARY KEY,description varchar(100));
create table techquest (id serial PRIMARY KEY,description varchar(10000));
create table team_server (id serial primary key,team_id integer references team(id),server_id integer references server(id));
create table team_project (id serial primary key,team_id integer references team(id),project_id integer references project(id));
create table team_dev (id serial primary key,team_id integer references team(id),dev_id integer references dev(id));
create table team_director (id serial primary key,team_id integer references team(id),director_id integer references director(id));
create table dev_computer (id serial primary key,dev_id integer references dev(id),computer_id integer references computer(id));
create table project_techquest (id serial primary key,project_id integer references project(id),techquest_id integer references techquest(id));



