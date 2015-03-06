/* users_group
   6101-6110
*/
INSERT INTO users_group (id,name,parent_id)
VALUES
  ( 6101, 'разработчики', null),
  ( 6102, 'тестировщики', null),
  ( 6103, 'бизнес-аналитики', null),
  ( 6104, 'python-разработчики', 6101),
  ( 6105, 'java-разработчики', 6101),
  ( 6106, 'ruby-разработчики', 6101);

/* user
   6000-6100
*/
INSERT INTO user (id,name,password,first_name,middle_name,last_name,is_admin,group_id)
VALUES
  ( 6000, 'ivan', 'ivan', 'Иван', 'Иванович', 'Иванов', FALSE , 6101 ),
  ( 6001, 'pavel', 'pavel', 'Павел', 'Павлович', 'Павлов', FALSE , 6102 ),
  ( 6002, 'petr', 'petr', 'Петр', 'Петрович', 'Петров', FALSE , 6103);

/* contact_type
   6111-6120
*/
INSERT INTO contacts_type (id,name,`regexp`)
VALUES
  ( 6111, 'mobile', 'regexp for mobile number' ),
  ( 6112, 'home', 'regexp for home number' ),
  ( 6113, 'address', 'regexp for address' );

/* contact
   6121-6200
*/
/* mobile */

INSERT INTO contact (id,name,user_id,type_id)
VALUES
  ( 6121, '552-22-11', 6000, 6111 ),
  ( 6122, '152-22-11', 6001, 6111 ),
  ( 6123, '252-22-11', 6002, 6111 ),
/* home */
  ( 6124, '55-22-11', 6000, 6112 ),
  ( 6125, '15-22-11', 6001, 6112 ),
  (6126, '25-22-11', 6002, 6112 ),
/* address */
  ( 6127, 'ул.Груша 62-1-12', 6000, 6113 ),
  ( 6128, 'ул.Ожидания 937-99-92', 6001, 6113 ),
  ( 6129, 'ул.Катастрофы 911', 6002, 6113 );

/* projects_category
  6201-6210
*/
INSERT INTO projects_category (id,name,parent_id)
VALUES
  ( 6201, 'Web', NULL ),
  ( 6202, 'Desktop', NULL ),
  ( 6203, 'MCU', NULL );

/* project
   6211-6220
*/
INSERT INTO project (id,name,description,category_id,manager_id)
VALUES
  ( 6211, 'Поисковая система SS', 'Super Search', 6201, 6000 ),
  ( 6212, 'ТM', 'Time Management', 6202, 6000 );

/* team
  6221-6230
*/
INSERT INTO team (id,project_id,leader_id)
VALUES
  ( 6221, 6211, 6002 ),
  ( 6222, 6212, 6002 );

/* employee
    6231-6240
 */
/*first team*/
INSERT INTO employee (id,user_id,team_id,role)
VALUES
  ( 6231, 6000, 6221, 0 ),
  ( 6232, 6001, 6221, 0 ),
  ( 6233, 6002, 6221, 1 ),

/*second team*/
  ( 6234, 6000, 6222, 0 ),
  ( 6235, 6001, 6222, 0 ),
  ( 6236, 6002, 6222, 1 );

/* module
   6241-6250
*/
INSERT INTO module (id,name,parent_id,project_id)
VALUES
  ( 6241, 'front', NULL, 6211),
  ( 6242, 'back', NULL, 6211);

/* actor
   6251-6260
 */
INSERT INTO actor (id,name,project_id)
VALUES ( 6251 , 'Пользователь SS' , 6211 );

/* use_case
   6261-6270
*/
INSERT INTO use_case (id,name,module_id)
VALUES ( 6261, 'Выполнение поискового запроса', 6241);


/* actor_use_case_relation
   6271-6280
*/
INSERT INTO actor_use_case_relation (id,actor_id,use_case_id)
VALUES ( 6271, 6251, 6261);

/* requirement
   6281-6290
*/
INSERT INTO requirement (id,name,description,importance,change_probability,use_case_id,module_id)
VALUES ( 6281, 'Основное требование' , 'Создать суперкрутую поисковую систему' , 1 , 0 , NULL , NULL );

/* task_category
  6291-6299
*/
INSERT INTO tasks_category (id,name,parent_id)
VALUES
  ( 6291, 'Important', NULL ),
  ( 6292, 'Optional', NULL ),
  ( 6293, 'Not Important', NULL );

/* task
   6300-6400
*/
INSERT INTO task (id,name,description,plan_time,difficulty,open_date,accept_date,close_date,category_id,requirement_id,module_id,employee_id,status)
VALUES
  ( 6300, 'front end', 'Создание пользвательского интерфейса', 168, 1, 2015-03-6, NULL , NULL , 6291 , 6281 , 6241, NULL , 0),
  ( 6301, 'back end', 'Создание архитектуры приложения для взаимодействия с БД и схемы БД', 1, 168, 2015-03-6, NULL , NULL , 6292 , 6281 , 6242, NULL , 0);
