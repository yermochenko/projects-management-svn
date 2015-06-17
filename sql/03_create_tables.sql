USE `pm_db`;

CREATE TABLE `users_group` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`parent_id` INTEGER,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`parent_id`) REFERENCES `users_group` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `user` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` CHAR(200) NOT NULL UNIQUE,
	`password` CHAR(32) NOT NULL,
	`first_name` VARCHAR(200) NOT NULL,
	`middle_name` VARCHAR(200) NOT NULL,
	`last_name` VARCHAR(200) NOT NULL,
	`is_admin` BOOLEAN NOT NULL,
	`group_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`group_id`) REFERENCES `users_group` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `contacts_type` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`regexp` VARCHAR(2000) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `contact` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`user_id` INTEGER NOT NULL,
	`type_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (`type_id`) REFERENCES `contacts_type` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `projects_category` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`parent_id` INTEGER,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`parent_id`) REFERENCES `projects_category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `project` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`description` TEXT,
	`category_id` INTEGER NOT NULL,
	`manager_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`category_id`) REFERENCES `projects_category` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `team` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`project_id` INTEGER NOT NULL,
	`leader_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`leader_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `employee` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`user_id` INTEGER NOT NULL,
	`team_id` INTEGER NOT NULL,
	/*
	 * 0 - аналитик (EmployeeRole.BUSINESS_ANALYST)
	 * 1 - программист (EmployeeRole.PROGRAMMER)
	 */
	`role` TINYINT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `actor` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`project_id` INTEGER NOT NULL,
	`is_abstract` BOOLEAN,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `actors_relation` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`parent_id` INTEGER NOT NULL,
	`child_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`parent_id`) REFERENCES `actor` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (`child_id`) REFERENCES `actor` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `module` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`parent_id` INTEGER,
	`project_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`parent_id`) REFERENCES `module` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `use_case` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`module_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `use_cases_relation` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`source_id` INTEGER NOT NULL,
	`destination_id` INTEGER NOT NULL,
	/*
	 * 0 - обобщение (GENERALIZATION)
	 * 1 - расширение (EXTEND)
	 * 2 - включение (INCLUDE)
	 */
	`type` TINYINT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`source_id`) REFERENCES `use_case` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (`destination_id`) REFERENCES `use_case` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `actor_use_case_relation` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`actor_id` INTEGER NOT NULL,
	`use_case_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (`use_case_id`) REFERENCES `use_case` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `requirement` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`description` TEXT NOT NULL,
	`importance` REAL NOT NULL,
	`change_probability` REAL NOT NULL,
	`use_case_id` INTEGER,
	`module_id` INTEGER,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`use_case_id`) REFERENCES `use_case` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `tasks_category` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`parent_id` INTEGER,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`parent_id`) REFERENCES `tasks_category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `task` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(200) NOT NULL,
	`description` TEXT NOT NULL,
	`plan_time` INTEGER NOT NULL,
	`difficulty` REAL NOT NULL,
	`open_date` DATE NOT NULL,
	`accept_date` DATE,
	`close_date` DATE,
	`category_id` INTEGER,
	`requirement_id` INTEGER,
	`module_id` INTEGER,
	`employee_id` INTEGER,
	/*
	 * 0 - новое NEW (open)
	 * 1 - назначенное ACCEPTED (open)
	 * 2 - в процессе STARTED (open)
	 * 3 - вновь открытое RENEW (open)
	 * 4 - завершённое разработчиком DONE (open)
	 * 5 - успешно завершённое APPROVED (close)
	 */
	`status` TINYINT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`category_id`) REFERENCES `tasks_category` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`requirement_id`) REFERENCES `requirement` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `tasks_dependency` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`task_id` INTEGER NOT NULL,
	`dependent_task_id` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (`dependent_task_id`) REFERENCES `task` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;
