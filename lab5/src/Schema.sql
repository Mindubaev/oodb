CREATE TYPE TechRequest AS (
	description text
);
CREATE TYPE Computer AS (
	description text
);
CREATE TYPE Project AS (
	budget int,
	techRequest TechRequest
);
CREATE TYPE Dev AS (
	fullname text,
	salary integer,
	birthdate date,
	computer Computer
);
CREATE TYPE Director AS (
	fullname text,
	salary integer,
	birthdate date,
	computer Computer
);
CREATE TYPE Server AS (
	description text
);
CREATE TYPE DevGroup AS (
	project Project,
	director Director,
	server Server,
	dev Dev[],
	id integer
);
create table Company (id serial,devGroup DevGroup);
Insert into Company (devGroup) values
(ROW(
(1500000,ROW('techrequest for new project'))
,ROW('Ivan Ivanov',80000,'1976-10-09',ROW('Some computer'))
,ROW('Server for some project')
,ARRAY[ROW('Ivan2 Ivanov2',75000,'1996-09-09',ROW('Personal computer1'))::Dev,ROW('Ivan3 Ivanov3',85000,'1986-04-05',ROW('Personal computer2'))::Dev]
,0
));

CREATE FUNCTION ins_trig() RETURNS trigger LANGUAGE plpgsql AS
$$BEGIN
Update company set devGroup.id=new.id where id=new.id;
RETURN NEW;
END;$$;
CREATE TRIGGER ins_trig After INSERT ON company FOR EACH ROW
EXECUTE PROCEDURE ins_trig();