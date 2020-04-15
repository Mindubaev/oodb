drop table if exists server,team,project,dev,director,human,computer,techquest,team_server,team_project,team_dev,team_director,dev_computer,project_techquest;
insert into human values (1,'15-04-1976','Иван Иванов',40000),
(2,'17-06-1969','Иван2 Иванов2',45000),
(3,'19-06-1986','Иван3 Иванов3',37000),
(4,'27-09-1977','Иван4 Иванов4',47000),
(5,'25-12-1991','Иван5 Иванов5',34000);
insert into cumputer values (1,'Компьютор для Иван Иванов'),
(2,'Компьютор для Иван2 Иванов2'),
(3,'Компьютор для Иван3 Иванов3'),
(4,'Компьютор для Иван4 Иванов4'),
(5,'Компьютор для Иван5 Иванов5');
insert into dev values (1,1),(2,2),(3,3),(4,4),(5,5);
