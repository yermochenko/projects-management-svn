insert into `users_group` (id, name)
values (1, "разботчики"),
		(2, "аналитики"),
		(3, "admin");
insert into `users_group` (id, name, parerent_id)
values (10, "java", 1),
		(11, "web", 1),
		(12, "c#", 1),
		(20, "по", 2),
		(21, "системный", 2);

insert into `user` (id, name, password, first_name, middle_name, last_name, is_admin, group_id)
values (1, "patron" , "123", "петя", "петрович", "петров", false, 10),
		(2, "frodo" , "123", "григорий", "алексеевич", "франц", false, 10),
		(3, "vanil" , "123", "иван", "иванович", "иванов", false, 11),
		(4, "yury" , "123", "юра", "васильевич", "зазулин", false, 12),
		(5, "elena" , "123", "елена", "александровна", "синякова", false, 21),
		(6, "flower" , "123", "татьяна", "николаевна", "турковская", false, 20),
		(7, "admin", "123", "вася", "васильевич", "василевский", true, 3);

insert into `contacs_type` (id, name, regexp)
values (1, "email", "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"),
		(2, "mobile", "\+375(\\d{2})\\d{3}-\\d{2}-\\d{2}");
		
INSERT INTO `contact` (`id`,`name`,`user_id`,`type_id`)
VALUES (1, "vasia@gmail.com",7, 1 ),
	   (2, "tania@mail.ru", 6, 1),
	   (3, "+375(29)891-03-12", 7, 2),
	   (4, "+375(33)396-45-90", 6, 2);

	   
INSERT INTO `projects_category` (`id`, `name`)
VALUE (1, "main");
INSERT INTO `projects_category` (`id`, `name`, `parent_id`)
VALUES  (2, "prcat2", 1),
		(3, "prcat3", 1);
		
INSERT INTO `project` (`id`, `name`, `description`,`category_id`,`manager_id`)
VALUES (1, "pr1", "descr1 manag - patron", 2, 1),
		(2, "pr2" , "descr2 manag - flower", 3, 6);

INSERT INTO `team` (`id`,`project_id`,`leader_id`)
VALUES 	(1, 1, 1),
		(2, 2, 6);

INSERT INTO `employee` (`id`, `user_id`, `team_id`,`role`)
VALUES  (1, 1, 1, 1),
		(2, 2, 1, 1),
		(3, 3, 1, 1),
		(4, 4, 1, 1),
		(5, 5, 2, 0),
		(6, 6, 2, 0);
		
INSERT INTO `module` (`id`, `name`, `parent_id`, `project_id`)
VALUES  (1, "mod1", null, 1),
		(2, "mod2", null, 2),
		(3, "mod3.1", 1, 1),
		(4, "mod4.1", 1, 1);
		
INSERT INTO `actor` (`id`, `name`, `project_id`)
VALUES  (1, "user1", 1),
		(2, "admin1", 1),
		(3, "user2", 2),
		(4, "admin2", 2);
		
INSERT INTO `tasks_category` (`id`, `name`, `parent_id`)
VALUES  (1, "t_c_1", null),
		(2, "t_c_2.1", 1),
		(3, "t_c_3.1", 1);
		
INSERT INTO `use_case` (`id`, `name`, `module_id`)
VALUES  (1, "u_c_1 mod3", 3),
		(2, "u_c_2 mod3", 3),
		(3, "u_c_3 mod4", 4);
		
INSERT INTO `requirement` (`id`, `name`, `description`, `importance`, `change_probability`, `use_case_id`, `module_id`)
VALUES  (1, "req1", "descr1", 0.01, 0.01, 1, 3),
		(2, "req2", "descr2", 0.02, 0.02, 2, 3),
		(3, "req3", "descr3", 0.03, 0.03, 3, 4);
		
INSERT INTO `task` (`id`, `name`, `description`, `plan_time`, `difficulty`, `open_date`, `accept_date`, `close_date`,`category_id`, `requirement_id`, `module_id`,`employee_id`,`status`)
VALUES  (1, "task1", 11, 0.1, "11.11.2014", "12.11.2014", null, 2, 1, 3, 1, 2),
		(2, "task2", 12, 0.2, "12.11.2014", "13.11.2014", null, 2, 2, 3, 2, 2),
		(3, "task3", 13, 0.3, "13.11.2014", "14.11.2014", "15.11.2014", 3, 3, 4, 5, 4);
	
INSERT INTO `tasks_dependency` (`id`,`task_id`,`dependent_task_id`)
VALUE  (1, 1, 2);



