INSERT INTO `users_group` (`id`,`name`,`parent_id`) VALUES
(8000, "разработчики", NULL),
(8001, "аналитики", NULL),
(8002, "admin", NULL),
(8003, "C#", 8000),
(8004, "java", 8000),
(8005, "web", 8000),
(8006, "системный", 8001),
(8007, "ПО", 8001);

INSERT INTO `user` (`id`,`name`,`password`,`last_name`,`first_name`,`middle_name`,`is_admin`,`group_id`) VALUE
		(8001, "asdg" , "123", "Зазулин", "Юрий", "Васильевич", false, 8000);
INSERT INTO `user` (`id`,`name`,`password`,`last_name`,`first_name`,`middle_name`,`is_admin`,`group_id`) VALUE		
		(8002, "frodos" , "123", "Алексеевич", "Григорий", "Франц", false, 8000);
		INSERT INTO `user` (`id`,`name`,`password`,`last_name`,`first_name`,`middle_name`,`is_admin`,`group_id`) VALUE
		(8003, "vanilka" , "123", "Иванов", "Иван", "Иванович", false, 8001);
INSERT INTO `user` (`id`,`name`,`password`,`last_name`,`first_name`,`middle_name`,`is_admin`,`group_id`) VALUE
		(8004, "yurka" , "123", "Петров", "Петр", "Петрович", false, 8004);
INSERT INTO `user` (`id`,`name`,`password`,`last_name`,`first_name`,`middle_name`,`is_admin`,`group_id`) VALUE		
		(8005, "elenka" , "123", "Синякова", "Елена", "Александровна", false, 8005);
INSERT INTO `user` (`id`,`name`,`password`,`last_name`,`first_name`,`middle_name`,`is_admin`,`group_id`) VALUE		
		(8006, "flowerka" , "123", "Турковская", "Татьяна", "Николаевна", false, 8006);
INSERT INTO `user` (`id`,`name`,`password`,`last_name`,`first_name`,`middle_name`,`is_admin`,`group_id`) VALUE		
		(8007, "Admins", "123", "Василевский", "Василий", "Васильевич", true, 8002);
		
INSERT INTO `contacts_type` (`id`,`name`,`regexp`) VALUES
	(8101,"email", "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"),
	(8102, "mobile", "\+375(\\d{2})\\d{3}-\\d{2}-\\d{2}");
	
INSERT INTO `contact` (`id`,`name`,`user_id`,`type_id`) VALUES
(8010, "mail@mail.ru", 8004, 8101),
(8011, "mail@mail.ru", 8005, 8101),
(8012, "mail@mail.ru", 8006, 8101),
(8013, "mail@mail.ru", 8007, 8101),
(8014, "+375(29)123-11-22", 8001, 8102),
(8015, "+375(29)123-11-22", 8002, 8102),
(8016, "+375(29)123-11-22", 8003, 8102),
(8017, "+375(29)123-11-22", 8004, 8102);

INSERT INTO `projects_category` (`id`,`name`,`parent_id`) VALUES
(8050, "main", NULL),
(8051, "prcat1", 8050),
(8052, "prcat2", 8050);

INSERT INTO `project` (`id`,`name`,`description`,`category_id`,`manager_id`) VALUES
(8800, "pr", "lol", 8050, 8001),
(8801, "pr1", ":)", 8051, 8002),
(8802, "pr2", "how_are_you?", 8051, 8003),
(8803, "pt3", "hello", 8052, 8004); 

INSERT INTO `team` (`id`,`project_id`,`leader_id`) VALUES
(8700, 8800, 8001),
(8701, 8801, 8002),
(8702, 8802, 8003),
(8703, 8803, 8004);
               
INSERT INTO `employee` (`id`,`user_id`,`team_id`,`role`) VALUES
(8600, 8001, 8700, 1),
(8601, 8002, 8701, 0),
(8602, 8003, 8702, 1),
(8603, 8004, 8703, 1);

INSERT INTO `actor` (`id`,`name`,`project_id`) VALUES
(8500, "us1",8800),
(8501, "us2",8801),
(8502, "us3",8802),
(8503, "us4",8803),
(8505, "admin1",8801),
(8506, "admin2",8802),
(8507, "admin3",8803);

INSERT INTO `actors_relation` (`id`,`parent_id`,`child_id`) VALUES
	(8001,8500,8507),
	(8002,8501,8506),
	(8003,8502,8505),
	(8004,8503,8503),
	(8005,8505,8502),
	(8006,8506,8501),	
	(8007,8507,8500);

INSERT INTO `module` (`id`,`name`,`parent_id`,`project_id`) VALUES
(8300, "mod1", NULL, 8800),
(8301, "mod2", 8300, 8801),
(8302, "mod3", 8300, 8802),
(8303, "mod4", 8300, 8803);

INSERT INTO `use_case` (`id`,`name`,`module_id`) VALUES
(8400, "asdg", 8300),
(8401, "qwert", 8300),
(8402, "zxcv", 8301),
(8403, "jiewsjfc", 8302),
(8404, "ksdhfk", 8303);

INSERT INTO `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) VALUES
	(8000,8500,8401),
	(8001,8501,8402),
	(8002,8502,8403),
	(8003,8503,8402),
	(8005,8505,8402),
	(8006,8506,8403),
	(8007,8507,8404);

INSERT INTO `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) VALUES
(8200, "requ", "descr", 0.1, 0.1, 8400, 8300),
(8201, "requ1", "descr1", 0.2, 0.2, 8401, 8300),
(8202, "requ2", "descr2", 0.3, 0.3, 8402, 8301),
(8203, "requ3", "descr3", 0.4, 0.4, 8403, 8302),
(8204, "requ4", "descr4", 0.5, 0.5, 8404, 8303);

INSERT INTO `tasks_category` (`id`,`name`,`parent_id`) VALUES
(8400,"tasks",NULL),
(8401,"tasks1",NULL),
	(8402,"tasks2",8401),
	(8403,"tasks3",8402),
	(8404,"tasks4",8402);

INSERT INTO `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`)
VALUES 
		 (8001, "task1","desc1", 11, 0.1, "11.11.2014", "12.11.2014", null, 8401, null, 8300, 8601, 2),
		 (8002, "task2","desc2", 12, 0.2, "12.11.2014", "13.11.2014", null, 8402, 8202, null, 8602, 2),
		 (8003, "task3","desc3", 13, 0.3, "13.11.2014", "14.11.2014", "15.11.2014", null, 8203, null, 8603, 4);