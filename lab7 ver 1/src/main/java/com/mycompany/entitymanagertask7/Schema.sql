/**
 * Author:  Artur
 * Created: 14 февр. 2020 г.
 */
drop table if exists server,team,project,dev,director,computer,techquest;
create table team (id serial PRIMARY KEY);
create table server (id serial PRIMARY KEY,team_id integer references team(id),description text);
create table project (id serial PRIMARY KEY,team_id integer references team(id),budget integer);
create table dev (id serial PRIMARY KEY,team_id integer references team(id),fullname text,birthdate date,salary integer);
create table director (id serial PRIMARY KEY,team_id integer references team(id),fullname text,birthdate date,salary integer);
create table computer (id serial PRIMARY KEY,dev_id integer references dev(id),description text);
create table techquest (id serial PRIMARY KEY,project_id integer references project(id),description text);



