insert into `users_group` (id, name)
values (16001, "разработчики"),
		(16002, "аналитики"),
		(16003, "admin");
insert into `users_group` (id, name, parerent_id)
values (16010, "java", 16001),
		(16011, "web", 16001),
		(16012, "c#", 16001),
		(16020, "по", 16002),
		(16021, "системный", 16002);

insert into `user` (id, name, password, first_name, middle_name, last_name, is_admin, group_id)
values (16001, "patron" , "123", "петя", "петрович", "петров", false, 16010),
		(16002, "frodo" , "123", "григорий", "алексеевич", "франц", false, 16010),
		(16003, "vanil" , "123", "иван", "иванович", "иванов", false, 16011),
		(16004, "yury" , "123", "юра", "васильевич", "зазулин", false, 16012),
		(16005, "elena" , "123", "елена", "александровна", "синякова", false, 16021),
		(16006, "flower" , "123", "татьяна", "николаевна", "турковская", false, 16020),
		(16007, "admin", "123", "вася", "васильевич", "василевский", true, 16003);

insert into `contacs_type` (id, name, regexp)
values (16001, "email", "^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"),
		(16002, "mobile", "\+375(\\d{2})\\d{3}-\\d{2}-\\d{2}");
		
INSERT INTO `contact` (`id`,`name`,`user_id`,`type_id`)
VALUES (16001, "vasia@gmail.com",16007, 16001 ),
	   (16002, "tania@mail.ru", 16006, 16001),
	   (16003, "+375(29)891-03-12", 16007, 16002),
	   (16004, "+375(33)396-45-90", 16006, 16002);

	   
INSERT INTO `projects_category` (`id`, `name`)
VALUE (16001, "main");
INSERT INTO `projects_category` (`id`, `name`, `parent_id`)
VALUES  (16002, "prcat2", 16001),
		(16003, "prcat3", 16001);
		
INSERT INTO `project` (`id`, `name`, `description`,`category_id`,`manager_id`)
VALUES (16001, "pr1", "descr1 manag - patron", 16002, 16001),
		(16002, "pr2" , "descr2 manag - flower", 16003, 16006);

INSERT INTO `team` (`id`,`project_id`,`leader_id`)
VALUES 	(16001, 16001, 16001),
		(16002, 16002, 16006);

INSERT INTO `employee` (`id`, `user_id`, `team_id`,`role`)
VALUES  (16001, 16001, 16001, 16001),
		(16002, 16002, 16001, 16001),
		(16003, 16003, 16001, 16001),
		(16004, 16004, 16001, 16001),
		(16005, 16005, 16002, 16000),
		(16006, 16006, 16002, 16000);
		
INSERT INTO `module` (`id`, `name`, `parent_id`, `project_id`)
VALUES  (16001, "mod1", null, 16001),
		(16002, "mod2", null, 16002),
		(16003, "mod3.1", 16001, 16001),
		(16004, "mod4.1", 16001, 16001);
		
INSERT INTO `actor` (`id`, `name`, `project_id`)
VALUES  (16001, "user1", 16001),
		(16002, "admin1", 16001),
		(16003, "user2", 16002),
		(16004, "admin2", 16002);
		
INSERT INTO `tasks_category` (`id`, `name`, `parent_id`)
VALUES  (16001, "t_c_1", null),
		(16002, "t_c_2.1", 16001),
		(16003, "t_c_3.1", 16001);
		
INSERT INTO `use_case` (`id`, `name`, `module_id`)
VALUES  (16001, "u_c_1 mod3", 16003),
		(16002, "u_c_2 mod3", 16003),
		(16003, "u_c_3 mod4", 16004);
		
INSERT INTO `requirement` (`id`, `name`, `description`, `importance`, `change_probability`, `use_case_id`, `module_id`)
VALUES  (16001, "req1", "descr1", 0.01, 0.01, 16001, 16003),
		(16002, "req2", "descr2", 0.02, 0.02, 16002, 16003),
		(16003, "req3", "descr3", 0.03, 0.03, 16003, 16004);
		
INSERT INTO `task` (`id`, `name`, `description`, `plan_time`, `difficulty`, `open_date`, `accept_date`, `close_date`,`category_id`, `requirement_id`, `module_id`,`employee_id`,`status`)
VALUES  (16001, "task1", 11, 0.1, "11.11.2014", "12.11.2014", null, 16002, 16001, 16003, 16001, 2),
		(16002, "task2", 12, 0.2, "12.11.2014", "13.11.2014", null, 16002, 16002, 16003, 16002, 2),
		(16003, "task3", 13, 0.3, "13.11.2014", "14.11.2014", "15.11.2014", 16003, 16003, 16004, 16005, 4);
	
INSERT INTO `tasks_dependency` (`id`,`task_id`,`dependent_task_id`)
VALUE  (16001, 16001, 16002);



