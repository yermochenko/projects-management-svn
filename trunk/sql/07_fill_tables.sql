/* users_group
   7101-7110
*/
INSERT INTO users_group (id,name,parent_id)
VALUES
  ( 7101, 'дизайнеры', null),
  ( 7102, 'тестировщики', null),
  ( 7103, 'разработчики', null),
  ( 7104, '3D-дизайнеры', 7101),
  ( 7105, 'web-дизайнеры', 7101);

/* user
   7000-7100
*/
INSERT INTO `user` (`id`, `name`, `password`, `first_name`, `middle_name`, `last_name`, `is_admin`, `group_id`)
	VALUES (7001, "user1", "pass1", "Иван", "Иванович", "Иванов", false, 7101),
		   (7002, "user2", "pass2", "Сидр", "Сидорович", "Сидоров", false, 7101),
		   (7003, "user3", "pass3", "Петр", "Петрович", "Петров", false, 7102),
		   (7004, "user4", "pass4", "Александр", "Александрович", "Александров", false, 7102),
		   (7005, "user5", "pass5", "Василий", "Васильевич", "Васильев", false, 7103);

/* contact_type
   7111-7120
*/
INSERT INTO contacts_type (id,name,`regexp`)
VALUES
  ( 7111, 'mobile', 'regexp for mobile number' ),
  ( 7112, 'eMail', 'regexp for email' ),
  ( 7113, 'address', 'regexp for address' );

/* contact
    7121-7200
*/
/* mobile */

INSERT INTO contact (id,name,user_id,type_id)
VALUES
  ( 7121, '111-11-11', 7000, 7111 ),
  ( 7122, '222-22-22', 7001, 7111 ),
  ( 7123, '333-33-33', 7002, 7111 ),
  ( 7124, '444-44-44', 7003, 7111 ),
  ( 7125, '555-55-55', 7004, 7111 ),
/* eMail */
  ( 7126, 'user1@mail.ru', 7000, 7112 ),
  ( 7127, 'user2@gmail.com', 7001, 7112 ),
  ( 7128, 'user3@yandex.ru', 7002, 7112 ),
  ( 7129, 'user4@yandex.ru', 7003, 7112 ),
  ( 7130, 'user5@mail.ru', 7004, 7112 ),
/* address */
  ( 7131, 'ул.Лазо 88', 7000, 7113 ),
  ( 7132, 'ул.Ленина 6', 7001, 7113 ),
  ( 7133, 'ул.Маркса 12', 7002, 7113 ),
  ( 7134, 'ул.Фрунзе 25', 7003, 7113 ),
  ( 7135, 'ул.Победы 9', 7004, 7113 );

/* projects_category
  7201-7210
*/
INSERT INTO projects_category (id,name,parent_id)
VALUES
  ( 7201, 'Web', NULL ),
  ( 7202, 'С++', NULL ),
  ( 7203, 'С#', NULL ),
  ( 7204, 'Java', NULL ),
  ( 7205, 'Drupal', NULL );
	
/* project
   7211-7220
*/
INSERT INTO project (id,name,description,category_id,manager_id)
VALUES
  ( 7211, 'PS', 'Presta Shop', 7205, 7001 ),
  ( 7212, 'Синонимайзер', 'Синонимайзей С#', 7203, 7001 );

/* team
  7221-7230
*/
INSERT INTO team (id,project_id,leader_id)
VALUES
  ( 7221, 7211, 7002 ),
  ( 7222, 7212, 7004 );

/* employee
    7231-7240
 */
/*first team*/
INSERT INTO employee (id,user_id,team_id,role)
VALUES
  ( 7231, 7001, 7221, 0 ),
  ( 7232, 7002, 7221, 0 ),
  ( 7233, 7003, 7221, 1 ),

/*second team*/
  ( 7234, 7003, 7222, 0 ),
  ( 7235, 7004, 7222, 0 ),
  ( 7236, 7005, 7222, 1 );

/* module
   7241-7250
*/
INSERT INTO module (id,name,parent_id,project_id)
VALUES
  ( 7241, 'front', NULL, 7211),
  ( 7242, 'back', NULL, 7211);

/* actor
   7251-7260
 */
INSERT INTO actor (id,name,project_id)
VALUES ( 7251 , 'Пользователь PS' , 7211 );

/* use_case
   7261-7270
*/
INSERT INTO use_case (id,name,module_id)
VALUES ( 7261, 'Выполнение поискового запроса', 7241);


/* actor_use_case_relation
   7271-7280
*/
INSERT INTO actor_use_case_relation (id,actor_id,use_case_id)
VALUES ( 7271, 7251, 7261);

/* requirement
   7281-7290
*/
INSERT INTO requirement (id,name,description,importance,change_probability,use_case_id,module_id)
VALUES ( 7281, 'Основное требование' , 'Создать интернет-магазин' , 1 , 0 , NULL , NULL );

/* task_category
  7291-7299
*/
INSERT INTO tasks_category (id,name,parent_id)
VALUES
  ( 7291, 'Важная', NULL ),
  ( 7292, 'Обычная', NULL ),
  ( 6293, 'Не важная', NULL );

/* task
   7300-7400
*/
INSERT INTO task (id,name,description,plan_time,difficulty,open_date,accept_date,close_date,category_id,requirement_id,module_id,employee_id,status)
VALUES
  ( 6300, 'front end', 'Создание оформления', 168, 1, 2015-03-20, NULL , NULL , 7291 , 7281 , 7241, NULL , 0),
  ( 6301, 'back end', 'Создание БД товаров и их классификация', 1, 168, 2015-03-20, NULL , NULL , 7292 , 7281 , 7242, NULL , 0);
