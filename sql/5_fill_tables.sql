INSERT INTO users_group
(id, name, parent_id)
VALUES
(5001, "developers", NULL),
	(5002, "Java developers", 5001),
		(5003, "specialists of JDBC", 5002),
	(5004, "C++ developers", 5001),
(5005, "testers", NULL),
	(5006, "automators", 5005),
(5007, "managers", NULL),
(5008, "specialists of UI", NULL),
	(5009, "designers", 5008);

	
 
INSERT INTO user
(id, name, password, first_name, middle_name, last_name, is_admin, group_id)
VALUES
(5001, "admin", "12345", "Василий", "Васильевич", "Васильев", TRUE, 5005),
(5002, "petrov", "1", "Пётр", "Петрович", "Петров", FALSE, 5002),
(5003, "ivanov", "1", "Иван", "Иванович", "Иванов", FALSE, 5003),
(5004, "alexandrov", "1", "Александр", "Александрович", "Александров", FALSE, 5002),
(5005, "sergeev", "1", "Сергей", "Сергеевич", "Сергеев", FALSE, 5001),
(5006, "igorev", "1", "Игорь", "Игоревич", "Игорев", FALSE, 5002),
(5007, "olegov", "1", "Олег", "Олегович", "Олегов", FALSE, 5007);


INSERT INTO contacts_type
(id, name, `regexp`)
VALUES 
(5001,"email","^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"),
(5002,"URL","^((https?|ftp)\:\/\/)?([a-z0-9]{1})((\.[a-z0-9-])|([a-z0-9-]))*\.([a-z]{2,6})(\/?)$"),
(5003,"phone number","^\+\d{2}\(\d{3}\)\d{3}-\d{2}-\d{2}$");



INSERT INTO contact
(id, name,user_id,type_id)
VALUES
(5001, "admin@camm.vsu.by",5001, 5001),
(5002, "vasiliev@camm.vsu.by",5001, 5001),
(5003, "http://www.petrov.com",5002, 5002),
(5004, "+38(033)345-34-45",5003, 5003),
(5005, "http://www.aleksandrov.com", 5004,5002),
(5006, "+50(033)111-11-11", 5005, 5003),
(5007, "+70(033)222-33-44", 5006, 5003);



INSERT INTO projects_category
(id, name, parent_id)
VALUES
(5001, "information systems", NULL),
	(5002, "Java", 5001),
	(5003, ".Net", 5001),
	(5015, "Cache",5001),
(5004, "sites", NULL),
	(5005, "PHP", 5004),
	(5006, "CMS", 5004),
		(5007, "Drupal", 5006),
		(5008, "Joomla", 5006),
(5009, "mobile application", NULL),
	(5010, "games", 5009);


INSERT INTO project
(id, name, description,category_id,manager_id)
VALUES
(5001,  "Управление проектами", "Система должна позволять разработчикам автоматизировать управление несколькими проектами. В системе должны храниться список ролей, требований (разного уровня), на основе которых формулируются задачи. Также в системе должна быть возможность отслеживать состояния задач (какой исполнитель за них отвечает, на каком этапе она находится).",5002,5001),
(5002,  "Игра <<Борьба за огонь>>", "Пошаговая стратегия с элементами развития по мотивам произведения «Борьба за огонь» Жозефа Рони-старшего.",5010,5002),
(5003,  "Текстовый редактор", "Кроссплатформенный текстовый редактор с возможностью подсветки синтаксиса, автодополнением кода, генерацией HTML-предстваления раскрашенного кода и возможность расширения функционала с помощью системы плагинов.",5003,5003);


INSERT INTO team
(id, project_id,leader_id)
VALUES
(5001, 5001,5005),
(5002, 5002,5006),
(5003, 5003,5007);


INSERT INTO employee
(id, user_id, team_id,role)
VALUES
(5001, 5001, 5001,1),
(5002, 5001, 5001,2),
(5003, 5002, 5002,2),
(5004, 5003, 5002,3),
(5005, 5003, 5002,2),
(5006, 5002, 5003,3),
(5007, 5003, 5003,4);


INSERT INTO module
(id, name,parent_id,project_id)
VALUES
(5001,"autorization",NULL,5001),
(5002,"login",5001,5001),
(5003,"logout",5001,5001),
(5004,"main menu for admin",NULL,5002),
(5005,"list scope admin",5004,5001);

INSERT INTO actor
(id,name,project_id)
VALUES
(5001,"Not authorized user",5001),
(5002,"Authorized user",5001),
(5003,"Manager",5001),
(5004,"Analyst",5001),
(5005,"Senior Software Developer",5001),
(5006,"Developer",5001),
(5007,"Admin",5001),
(5008,"admin",5002),
(5009,"Gamer",5002),
(5010,"User",5003);

INSERT INTO tasks_category
(id,name,parent_id)
VALUES
(5001,"technical task",NULL),
(5002,"Front-end",NULL),
(5003,"User Interface",5002),
(5004,"Back-end",NULL),
(5005,"Data Base",NULL),
(5006,"Business logic",NULL);

INSERT INTO use_case
(id,name,module_id)
VALUES
(5001,"start page",5001),
(5002,"for login",5002),
(5003,"for logout",5003),
(5004,"for navigate",5004),
(5005,"for solution different task",5005);

INSERT INTO requirement
(id,name,description,importance,change_probability,use_case_id,module_id)
VALUES
(5001,"req1","missing",0.9,0.3,5001,5001),
(5002,"req2","missing",0.9,0.3,5001,5001),
(5003,"req3","missing",1,0.1,5002,5001),
(5004,"req4","missing",1,0.1,5003,5002),
(5005,"req5","missing",1,0.1,5004,5003);



INSERT INTO task
(id,name,description,plan_time,difficulty,open_date,accept_date,close_date,category_id,requirement_id,module_id,employee_id,status)
VALUES
(5001,"task1","missing",4,0.8,"10.01.2013","12.01.2013",NULL,5001,5001,5001,5001,2),
(5002,"task2","missing",3,0.8,"10.03.2013","12.03.2013",NULL,5001,5001,5001,5001,2),
(5003,"task3","missing",4,0.9,"10.01.2013","12.01.2013","12.02.2013",5001,5002,5002,5001,5),
(5004,"task4","missing",3,0.9,"10.01.2014","12.01.2014",NULL,5001,5002,5002,5002,2),
(5005,"task5","missing",6,0.9,"10.01.2014","12.01.2014",NULL,5002,5003,5003,5002,2),
(5006,"task6","missing",4,0.5,"10.01.2014","12.01.2014",NULL,5002,5003,5003,5003,2),
(5007,"task7","missing",2,0.5,"10.06.2013","12.06.2013",NULL,5003,5003,5003,5003,2),
(5008,"task8","missing",5,0.5,"10.01.2013","12.01.2013",NULL,5003,5003,5004,5004,2),
(5009,"task9","missing",6,0.6,"10.01.2015","12.01.2015","24.02.2015",5003,5005,5001,5004,5),
(5010,"task10","missing",7,0.6,"10.01.2013","12.01.2013",NULL,5004,5005,5001,5005,2),
(5011,"task11","missing",4,0.7,"20.01.2013","22.01.2013",NULL,5004,5005,5001,5005,2);

INSERT INTO tasks_dependency
(id,task_id,dependent_task_id)
VALUES
(5002,5002,5001),
(5004,5003,5005);


INSERT INTO use_cases_relation
(id,source_id,destination_id,type)
VALUES
(5001,5002,5001,2),
(5002,5005,5004,3),
(5003,5005,5004,1);


