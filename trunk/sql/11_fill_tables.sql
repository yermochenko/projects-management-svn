INSERT INTO `users_group` (`id`,`name`,`parent_id`) VALUES (11000,"Разработчики",NULL);

/* MD5 хэш пароля "12345" */
INSERT INTO  `user` (`id`,`name`,`password`,`first_name`,`middle_name`,`last_name`,`is_admin`,`group_id`) 
VALUES (11000,"IvanES","202cb962ac59075b964b07152d234b70","Иван","Евгеньевич","Смирнов",true,11000);
INSERT INTO  `user` (`id`,`name`,`password`,`first_name`,`middle_name`,`last_name`,`is_admin`,`group_id`) 
VALUES (11001,"AndreyVS","202cb962ac59075b964b07152d234b70","Андрей","Витальевич","Смирнов",false,11000);
INSERT INTO  `user` (`id`,`name`,`password`,`first_name`,`middle_name`,`last_name`,`is_admin`,`group_id`) 
VALUES (11002,"SergeiVE","202cb962ac59075b964b07152d234b70","Сергей","Владимирович","Ерошин",false,11000);
INSERT INTO  `user` (`id`,`name`,`password`,`first_name`,`middle_name`,`last_name`,`is_admin`,`group_id`) 
VALUES (11003,"PetrVB","202cb962ac59075b964b07152d234b70","Петр","Витальевич","Бирюков",false,11000);
INSERT INTO  `user` (`id`,`name`,`password`,`first_name`,`middle_name`,`last_name`,`is_admin`,`group_id`) 
VALUES (11004,"StanislavAG","202cb962ac59075b964b07152d234b70","Станислав","Андреевич","Жилов",false,11000);


INSERT INTO  `contacts_type` (`id`,`name`,`regexp`) 
VALUES (11000,"Contact Type 1","Reg expression");
INSERT INTO  `contacts_type` (`id`,`name`,`regexp`) 
VALUES (11001,"Contact Type 2","Reg expression");
INSERT INTO  `contacts_type` (`id`,`name`,`regexp`) 
VALUES (11002,"Contact Type 3","Reg expression");
INSERT INTO  `contacts_type` (`id`,`name`,`regexp`) 
VALUES (11003,"Contact Type 4","Reg expression");

INSERT INTO  `contact` (`id`,`name`,`user_id`,`type_id`) 
VALUES (11000,"Contact 1",11000,11000);
INSERT INTO  `contact` (`id`,`name`,`user_id`,`type_id`) 
VALUES (11001,"Contact 2",11000,11001);
INSERT INTO  `contact` (`id`,`name`,`user_id`,`type_id`) 
VALUES (11002,"Contact 3",11001,11000);
INSERT INTO  `contact` (`id`,`name`,`user_id`,`type_id`) 
VALUES (11003,"Contact 4",11002,11003);
INSERT INTO  `contact` (`id`,`name`,`user_id`,`type_id`) 
VALUES (11004,"Contact 5",11003,11002);
INSERT INTO  `contact` (`id`,`name`,`user_id`,`type_id`) 
VALUES (11005,"Contact 6",11004,11001);

INSERT INTO  `projects_category` (`id`,`name`,`parent_id`) 
VALUES (11000,"Projects category 1",NULL);
INSERT INTO  `projects_category` (`id`,`name`,`parent_id`) 
VALUES (11001,"Projects category 1.1",11000);
INSERT INTO  `projects_category` (`id`,`name`,`parent_id`) 
VALUES (11002,"Projects category 2",NULL);
INSERT INTO  `projects_category` (`id`,`name`,`parent_id`) 
VALUES (11003,"Projects category 1.1.1",NULL);

INSERT INTO  `project` (`id`,`name`,`description`,`category_id`,`manager_id`) 
VALUES (11000,"Project 1","Some project 1 description",11000,11000);

INSERT INTO  `team` (`id`,`project_id`,`leader_id`) 
VALUES (11000,11000,11002);
INSERT INTO  `team` (`id`,`project_id`,`leader_id`) 
VALUES (11001,11000,11001);

INSERT INTO  `employee` (`id`,`user_id`,`team_id`,`role`) 
VALUES (11001,11001,11001,0);
INSERT INTO  `employee` (`id`,`user_id`,`team_id`,`role`) 
VALUES (11002,11002,11000,0);
INSERT INTO  `employee` (`id`,`user_id`,`team_id`,`role`) 
VALUES (11003,11003,11001,1);
INSERT INTO  `employee` (`id`,`user_id`,`team_id`,`role`) 
VALUES (11004,11004,11000,1);

INSERT INTO  `actor` (`id`,`name`,`project_id`) 
VALUES (11000,"Administrator",11000);
INSERT INTO  `actor` (`id`,`name`,`project_id`) 
VALUES (11001,"Parent User",11000);
INSERT INTO  `actor` (`id`,`name`,`project_id`) 
VALUES (11002,"Child User",11000);

INSERT INTO  `actors_relation` (`id`,`parent_id`,`child_id`) 
VALUES (11000,11001,11002);

INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11000,"module 1",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11001,"module 2",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11002,"module 1.1",11000,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11003,"module 3",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11004,"module 4",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11005,"module 5",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11006,"module 6",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11007,"module 7",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11008,"module 8",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11009,"module 9",NULL,11000);
INSERT INTO  `module` (`id`,`name`,`parent_id`,`project_id`) 
VALUES (11010,"module 10",NULL,11000);

INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11000,"Use case 1",11000);
INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11001,"Use case 2",11001);
INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11002,"Use case 1.1",11002);
INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11003,"Use case 3",11003);
INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11004,"Use case 4",11004);
INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11005,"Use case 5",11005);
INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11006,"Use case 6",11006);
INSERT INTO  `use_case` (`id`,`name`,`module_id`) 
VALUES (11007,"Use case 7",11007);

INSERT INTO  `use_cases_relation` (`id`,`source_id`,`destination_id`,`type`) 
VALUES (11000,11002,11000,0);

INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11000,11000,11000);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11001,11000,11001);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11002,11000,11002);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11003,11001,11000);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11004,11001,11003);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11005,11001,11004);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11006,11002,11000);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11007,11002,11005);
INSERT INTO  `actor_use_case_relation` (`id`,`actor_id`,`use_case_id`) 
VALUES (11008,11002,11006);

INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11000,"Requirement 1","Some requirement description",1.0,0.0,11000,11000);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11001,"Requirement 2","Some requirement description",0.5,0.0,11001,11001);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11002,"Requirement 3","Some requirement description",1.0,0.0,11002,11002);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11003,"Requirement 4","Some requirement description",0.5,0.0,11003,11003);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11004,"Requirement 5","Some requirement description",0.5,0.7,11004,11004);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11005,"Requirement 6","Some requirement description",0.5,0.0,11005,11005);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11006,"Requirement 7","Some requirement description",0.5,0.0,11006,11006);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11007,"Requirement 8","Some requirement description",0.5,0.0,NULL,NULL);
INSERT INTO  `requirement` (`id`,`name`,`description`,`importance`,`change_probability`,`use_case_id`,`module_id`) 
VALUES (11008,"Requirement 9","Some requirement description",0.5,0.9,NULL,NULL);

INSERT INTO  `tasks_category` (`id`,`name`,`parent_id`) 
VALUES (11000,"Task category 1",NULL);
INSERT INTO  `tasks_category` (`id`,`name`,`parent_id`) 
VALUES (11001,"Task category 1.1",11000);
INSERT INTO  `tasks_category` (`id`,`name`,`parent_id`) 
VALUES (11002,"Task category 1.2",11000);
INSERT INTO  `tasks_category` (`id`,`name`,`parent_id`) 
VALUES (11003,"Task category 2",NULL);
INSERT INTO  `tasks_category` (`id`,`name`,`parent_id`) 
VALUES (11004,"Task category 2.1",11003);
INSERT INTO  `tasks_category` (`id`,`name`,`parent_id`) 
VALUES (11005,"Task category 2.2",11003);

INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11000,"Task 1","Some task description",280,0.8,"2015-02-23","2015-02-23","2015-02-26",11000,11000,11000,11001,4);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11001,"Task 2","Some task description",180,0.6,"2015-02-23","2015-02-23","2015-03-02",11000,11001,11001,11002,4);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11002,"Task 3","Some task description",170,0.6,"2015-02-23","2015-02-26","2015-03-03",11000,11002,11002,11003,4);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11003,"Task 4","Some task description",180,0.6,"2015-02-23","2015-02-23","2015-03-03",11000,11003,11003,11004,4);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11004,"Task 5","Some task description",250,0.5,"2015-02-23","2015-02-26","2015-03-03",11001,11004,11004,11001,4);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11005,"Task 6","Some task description",160,0.4,"2015-02-23","2015-03-02","2015-03-05",11001,11005,11005,11002,4);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11006,"Task 7","Some task description",380,0.9,"2015-02-23","2015-03-03","2015-03-05",11001,11006,11006,11003,4);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11007,"Task 8","Some task description",280,0.8,"2015-02-26",NULL,NULL,11002,11007,11007,11004,0);
INSERT INTO  `task` (`id`,`name`,`description`,`plan_time`,`difficulty`,`open_date`,`accept_date`,`close_date`,`category_id`,`requirement_id`,`module_id`,`employee_id`,`status`) 
VALUES (11008,"Task 9","Some task description",100,0.3,"2015-02-26",NULL,NULL,11002,11008,11008,11004,0);

INSERT INTO  `tasks_dependency` (`id`,`task_id`,`dependent_task_id`) 
VALUES (11000,11000,11002);
