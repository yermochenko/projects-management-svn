INSERT INTO users_group
(id, name, parent_id)
VALUES
(12001, "developers", NULL),
	(12002, "Java developers", 12001),
		(12003, "specialists of JDBC", 12002),
	(12004, "C++ developers", 12001),
(12005, "testers", NULL),

INSERT INTO user
(id, name, password, first_name, middle_name, last_name, is_admin, group_id)
VALUES
(12001, "Yurkindar", "11111a", "Юрий", "Леонидович", "Берестнеь", FALSE, 12001),
(12002, "Inkvi", "1546", "Алексей", "Алексеевич", "Ткаченок", FALSE, 12005),
(12003, "Gesha", "11111a", "Алексей", "Генадьевич", "Козлов", FALSE, 12005),
(12004, "Kazak", "MehanikKez", "Виталий", "Викторович", "Плигавко", FALSE, 12005),
(12005, "Gibur", "z1on01", "Николай", "Леонидович", "Берестень", FALSE, 12002),
(12006, "Ignas'", "Ignasuk-parsuk", "Александр", "Александрович", "Игнатьев", FALSE, 12004),
(12007, "Chips", "1403Galileogalilei", "Александр", "Леонидович", "Берестень", TRUE, 12003);

INSERT INTO contacts_type
(id, name, `regexp`)
VALUES 
(12001,"email","^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"),
(12002,"URL","^((https?|ftp)\:\/\/)?([a-z0-9]{1})((\.[a-z0-9-])|([a-z0-9-]))*\.([a-z]{2,6})(\/?)$"),
(12003,"phone number","^\+\d{2}\(\d{3}\)\d{3}-\d{2}-\d{2}$");

INSERT INTO contact
(id, name,user_id,type_id)
VALUES
(12001, "yurkindar@open.by",12001, 12001),
(12002, "inkvi@mail.by",12002, 12001),
(12003, "gesha@mail.ru",12003, 12001),
(12004, "kazak-mehanik@mail.by",12004, 12001),
(12005, "kolyanrad@open.by",12005, 12001);

INSERT INTO projects_category
(id, name, parent_id)
VALUES
(12001, "information systems", NULL),
	(12002, "Java", 12001),
	(12003, ".Net", 12001),
	(12004, "Cache",12001),
(12005, "sites", NULL),
	(12006, "PHP", 12005),
	(12007, "CMS", 12005),
		(12008, "Drupal", 12007),
		(12009, "Joomla", 12007),
(12010, "mobile application", NULL),
	(12011, "games", 12010);
	
INSERT INTO project
(id, name, description,category_id,manager_id)
VALUES
(12001, "База данных сотрудников фирмы", "Специальная программа для автоматизации сбора отчетов и сведений о сотрудниках", 12004, 12001),
(12002, "Интернет магазин", "Типичный инттернет магазин", 12005, 12002),
(12003, "Мобильная версия карты местности", "Приложения для телефона для просмотра карты местности с определением текущего местоположения", 12010, 12006);

INSERT INTO team
(id, project_id,leader_id)
VALUES
(12001, 12001,12003),
(12002, 12002,12005),
(12003, 12003,12007);

INSERT INTO employee
(id, user_id, team_id,role)
VALUES
(12001, 12003, 12001, 1),
(12002, 12001, 12001, 2),
(12003, 12002, 12001, 2),
(12004, 12005, 12002, 1),
(12005, 12006, 12002, 2),
(12006, 12007, 12003, 1),
(12007, 12004, 12003, 2),


INSERT INTO actor
(id,name,project_id)
VALUES
(12001,"Администратор",12001),
(12002,"Програмист",12001),
(12003,"Бухгалтер",12001),
(12004,"Не авторизированный пользователь",12002),
(12005,"Покупатель",12002),
(12006,"Продавец",12002),
(12007,"Пользователь",12003);

INSERT INTO module
(id, name,parent_id,project_id)
VALUES
(12001,"Авторизация",NULL,12001),
(12002,"Вход",12001,12001),
(12003,"Выход",12001,12001),
(12004,"Главное меню администратора",NULL,12001),
(12005,"Вывод данных",NULL,12001);

INSERT INTO use_case
(id,name,module_id)
VALUES
(12001,"Вход в программу",12001),
(12002,"Вход",12002),
(12003,"Выход",12003),
(12004,"Навигация администратора",12004),
(12005,"Обработка данных",12005);


INSERT INTO actor_use_case_relation
(id,actor_id,use_case_id)
VALUES
(12001, 12001, 12001),
(12002, 12001, 12002),
(12003, 12001, 12003),
(12004, 12001, 12004),
(12005, 12003, 12005);

INSERT INTO use_cases_relation
(id,source_id,destination_id,type)
VALUES
(12001,12002,12005,2);

INSERT INTO requirement
(id,name,description,importance,change_probability,use_case_id,module_id)
VALUES
(12001,"req1","Требования по запуску приложения",0.9,0.3,NULL,12001),
(12002,"req2","Требования по входу пользователя",0.9,0.3,NULL,12002),
(12003,"req3","Требования по выходу пользователя",1,0.1,NULL,12003),
(12004,"req4","Требования по навигации",1,0.1,12004,NULL),
(12005,"req5","Требования по обработке данных",1,0.1,12005,NULL);


INSERT INTO tasks_category
(id,name,parent_id)
VALUES
(12001,"Техническое задание",NULL),
(12002,"Срочные",NULL),
(12003,"Интерфейс",NULL),
(12004,"Вторичные",NULL),
(12005,"Базы данных",NULL);

INSERT INTO task
(id,name,description,plan_time,difficulty,open_date,accept_date,close_date,category_id,requirement_id,module_id,employee_id,status)
VALUES
(12001,"начало работы","приступить к созданию приложения",4,1,"2013-03-10","2013-03-12",12002,NULL,NULL,12003,0),
(12002, "разработка интерфейса","создание пользовательсого интерфейса", 1, 0.3, "2013-03-11", "2013-03-12", 12003, NULL, NULL, 12001, 0),
(12003, "реализация обработки данных", "подсчеты", 4, 0.8, "2013-03-14", "2013-03017", 12002, 12005, 12005, 12002, 0);

INSERT INTO tasks_dependency
(id,task_id,dependent_task_id)
VALUES
(12001,12002,12001),
(12002,12003,12001);
