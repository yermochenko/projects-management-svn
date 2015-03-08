USE `pm_db`;

INSERT INTO `users_group` (
	`id`, `name`
	) VALUES (
	20001, 'admin'
	);
	
INSERT INTO `users_group` (
	`id`, `name`, `parent_id`
	) VALUES (
	20002, 'user', 20002
	);
	
INSERT INTO `users_group` (
	`id`, `name`, `parent_id`
	) VALUES (
	20003, 'super_admin', 20001
	);
	
INSERT INTO `users_group` (
	`id`, `name`, `parent_id`
	) VALUES (
	20004, 'creator', 20002
	);
	
INSERT INTO `user` (
	`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`
	) VALUES (
	20001, 'dima', 'VeryHardPassword', 'Dmitriy', 'Stepanoich', 'Duryak', TRUE, 20003
	);
	
INSERT INTO `user` (
	`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`
	) VALUES (
	20002, 'zloy', '#*#$W$*%E)^%^$^$%^%@*%@)(', 'Nikita', 'Alexeevich', 'Tsaryov', TRUE, 20001
	);
	
INSERT INTO `user` (
	`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`
	) VALUES (
	20003, 'flower_mover', 'brilliant', 'Nadezhda', 'Mihaylovna', 'Noryk', TRUE, 20001
	);
	
INSERT INTO `user` (
	`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`
	) VALUES (
	20004, 'linux_zombie', 'Gentoo', 'Viktor', 'Dmitrievich', 'Smirnov', FALSE, 20004
	);
	
INSERT INTO `user` (
	`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`
	) VALUES (
	20005, 'sasha', '12345', 'Alexander', 'Gennadyevich', 'Ulasevich', FALSE, 20004
	);
	
INSERT INTO `user` (
	`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`
	) VALUES (
	20006, 'tii', 'bbbbbbbb', 'Andrey', 'Petrovich', 'Vyazov', FALSE, 20004
	);
	
INSERT INTO `user` (
	`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`
	) VALUES (
	20007, 'last', 'sudo rm -rf /*', 'Elena', 'Alexandrovna', 'Korpina', FALSE, 20002
	);
	
INSERT INTO `contacts_type` (
	`id`, `name`, `regexp`
	) VALUES (
	20001, 'kont', '*@*'
	);
	
INSERT INTO `contact` (
	`id`, `name`, `user_id`, `type_id`
	) VALUES (
	20001, 'dima_k', 20001, 20001
	);
	
INSERT INTO `contact` (
	`id`, `name`, `user_id`, `type_id`
	) VALUES (
	20002, 'zloy_k', 20002, 20001
	);
	
INSERT INTO `contact` (
	`id`, `name`, `user_id`, `type_id`
	) VALUES (
	20003, 'flower_mover_k', 20003, 20001
	);
	
INSERT INTO `projects_category` (
	`id`, `name`
	) VALUES (
	20001, 'usual_project'
	);
	
INSERT INTO `projects_category` (
	`id`, `name`, `parent_id`
	) VALUES (
	20002, 'simple_project', 20001
	);
	
INSERT INTO `project` (
	`id`, `name`, `description`, `category_id`, `manager_id`
	) VALUES (
	20001, 'Switch', 'Main project', 20001, 20004
	);
	
INSERT INTO `project` (
	`id`, `name`, `description`, `category_id`, `manager_id`
	) VALUES (
	20002, 'Trim', 'Additional project 1', 20001, 20002
	);
	
INSERT INTO `project` (
	`id`, `name`, `description`, `category_id`, `manager_id`
	) VALUES (
	20003, 'Bold', 'Additional project 2', 20001, 20006
	);
	
INSERT INTO `project` (
	`id`, `name`, `description`, `category_id`, `manager_id`
	) VALUES (
	20004, 'Push', 'Simple project 1', 20002, 20003
	);
	
INSERT INTO `project` (
	`id`, `name`, `description`, `category_id`, `manager_id`
	) VALUES (
	20005, 'Goto', 'Simple project 2', 20002, 20005
	);
	
INSERT INTO `team` (
	`id`, `project_id`, `leader_id`
	) VALUES (
	20001, 20001, 20001
	);
	
INSERT INTO `team` (
	`id`, `project_id`, `leader_id`
	) VALUES (
	20002, 20002, 20002
	);
	
INSERT INTO `team` (
	`id`, `project_id`, `leader_id`
	) VALUES (
	20003, 20003, 20003
	);
	
INSERT INTO `team` (
	`id`, `project_id`, `leader_id`
	) VALUES (
	20004, 20004, 20003
	);
	
INSERT INTO `team` (
	`id`, `project_id`, `leader_id`
	) VALUES (
	20005, 20005, 20001
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20001, 20001, 20001, 0
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20002, 20004, 20001, 1
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20003, 20002, 20002, 1
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20004, 20003, 20003, 0
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20005, 20006, 20003, 1
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20006, 20003, 20004, 1
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20007, 20001, 20005, 0
	);
	
INSERT INTO `employee` (
	`id`, `user_id`, `team_id`, `role`
	) VALUES (
	20008, 20005, 20005, 1
	);
	
INSERT INTO `actor` (
	`id`, `name`, `project_id`
	) VALUES (
	20001, 'creator', 20001
	);
	
INSERT INTO `actor` (
	`id`, `name`, `project_id`
	) VALUES (
	20002, 'updater', 20001
	);
	
INSERT INTO `actor` (
	`id`, `name`, `project_id`
	) VALUES (
	20003, 'deleter', 20001
	);
	
INSERT INTO `actor` (
	`id`, `name`, `project_id`
	) VALUES (
	20004, 'creator', 20002
	);
	
INSERT INTO `actor` (
	`id`, `name`, `project_id`
	) VALUES (
	20005, 'creator', 20003
	);
	
INSERT INTO `actors_relation` (
	`id`, `parent_id`, `child_id`
	) VALUES (
	20001, 20001, 20004
	);
	
INSERT INTO `actors_relation` (
	`id`, `parent_id`, `child_id`
	) VALUES (
	20002, 20001, 20005
	);
	
INSERT INTO `actors_relation` (
	`id`, `parent_id`, `child_id`
	) VALUES (
	20003, 20003, 20002
	);
	
INSERT INTO `module` (
	`id`, `name`, `project_id`
	) VALUES (
	20001, 'Left', 20001
	);
	
INSERT INTO `module` (
	`id`, `name`, `project_id`
	) VALUES (
	20002, 'Right', 20001
	);
	
INSERT INTO `module` (
	`id`, `name`, `parent_id`, `project_id`
	) VALUES (
	20003, 'Left_Up', 20001, 20002
	);
	
INSERT INTO `module` (
	`id`, `name`, `parent_id`, `project_id`
	) VALUES (
	20004, 'Right_Up', 20002, 20003
	);
	
INSERT INTO `module` (
	`id`, `name`, `parent_id`, `project_id`
	) VALUES (
	20005, 'Left_Down', 20001, 20004
	);
	
INSERT INTO `module` (
	`id`, `name`, `parent_id`, `project_id`
	) VALUES (
	20006, 'Right_Down', 20002, 20005
	);
	
INSERT INTO `use_case` (
	`id`, `name`, `module_id`
	) VALUES (
	20001, 'form_left', 20001
	);
	
INSERT INTO `use_case` (
	`id`, `name`, `module_id`
	) VALUES (
	20002, 'deform_left', 20001
	);
	
INSERT INTO `use_case` (
	`id`, `name`, `module_id`
	) VALUES (
	20003, 'form_right', 20002
	);
	
INSERT INTO `use_case` (
	`id`, `name`, `module_id`
	) VALUES (
	20004, 'deform_right', 20002
	);
	
INSERT INTO `use_case` (
	`id`, `name`, `module_id`
	) VALUES (
	20005, 'break_left', 20003
	);
	
INSERT INTO `use_case` (
	`id`, `name`, `module_id`
	) VALUES (
	20006, 'break_right', 20004
	);
	
INSERT INTO `use_cases_relation` (
	`id`, `source_id`, `destination_id`, `type`
	) VALUES (
	20001, 20002, 20005, 2
	);
	
INSERT INTO `use_cases_relation` (
	`id`, `source_id`, `destination_id`, `type`
	) VALUES (
	20002, 20004, 20006, 2
	);
	
INSERT INTO `actor_use_case_relation` (
	`id`, `actor_id`, `use_case_id`
	) VALUES (
	20001, 20001, 20001
	);
	
INSERT INTO `actor_use_case_relation` (
	`id`, `actor_id`, `use_case_id`
	) VALUES (
	20002, 20001, 20003
	);
	
INSERT INTO `actor_use_case_relation` (
	`id`, `actor_id`, `use_case_id`
	) VALUES (
	20003, 20003, 20002
	);
	
INSERT INTO `actor_use_case_relation` (
	`id`, `actor_id`, `use_case_id`
	) VALUES (
	20004, 20003, 20004
	);
	
INSERT INTO `actor_use_case_relation` (
	`id`, `actor_id`, `use_case_id`
	) VALUES (
	20005, 20002, 20005
	);
	
INSERT INTO `actor_use_case_relation` (
	`id`, `actor_id`, `use_case_id`
	) VALUES (
	20006, 20002, 20006
	);
	
INSERT INTO `requirement` (
	`id`, `name`, `description`, `importance`, `change_probability`, `use_case_id`, `module_id`
	) VALUES (
	20001, 'Simmetric', 'Left~Right', 1, 1, 20001, 20001
	);
	
INSERT INTO `tasks_category` (
	`id`, `name`
	) VALUES (
	20001, 'First_Part'
	);
	
INSERT INTO `task` (
	`id`, `name`, `description`, `plan_time`, `difficulty`, `open_date`, `module_id`, `status`
	) VALUES (
	20001, 'Start', 'Start developing', 2, 0, "2015-01-01", 20001, 0
	);
	
INSERT INTO `task` (
	`id`, `name`, `description`, `plan_time`, `difficulty`, `open_date`, `module_id`, `status`
	) VALUES (
	20002, 'Start 2', 'Start developing 2', 2, 0, "2015-01-01", 20002, 0
	);
	
INSERT INTO `tasks_dependency` (
	`id`, `task_id`, `dependent_task_id`
	) VALUES (
	20001, 20001, 20002
	);