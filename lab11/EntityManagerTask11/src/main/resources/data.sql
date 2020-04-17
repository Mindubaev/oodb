--if exists  team_servers,team_projects,team_empls,team_director,dev_computer,project_techquest,server,team,project,dev,director,human,computer,tech_quest cascade;
insert into computer values (nextval('computer_seq'),'Компьютор для Иван Иванов'),
(nextval('computer_seq'),'Компьютор для Иван2 Иванов2'),
(nextval('computer_seq'),'Компьютор для Иван3 Иванов3'),
(nextval('computer_seq'),'Компьютор для Иван4 Иванов4'),
(nextval('computer_seq'),'Компьютор для Иван5 Иванов5');
insert into human values (nextval('human_seq'),'15-04-1976','Иван Иванов',40000),
(nextval('human_seq'),'17-06-1969','Иван2 Иванов2',45000),
(nextval('human_seq'),'19-06-1986','Иван3 Иванов3',37000),
(nextval('human_seq'),'27-09-1977','Иван4 Иванов4',47000),
(nextval('human_seq'),'25-12-1991','Иван5 Иванов5',34000);
insert into dev values (1,1),(2,2),(3,3),(4,4),(5,5);
insert into team (id) values (nextval('team_id_seq'));
insert into team_empls (team_id,empls_id) values (1,1),(1,2),(1,3),(1,4),(1,5);
