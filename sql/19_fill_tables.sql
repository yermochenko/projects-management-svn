/* user
   19000-19100
*/
INSERT INTO user (id,name,password,first_name,middle_name,last_name,is_admin,group_id)
VALUES
  ( 19000, 'ivan007', 'ivan1991', 'Иван', 'Иванович', 'Иванов', FALSE , 19101 ),
  ( 19001, 'pavel007', 'qwerty', 'Павел', 'Павлович', 'Павлов', FALSE , 19102 ),
  ( 19002, 'Semen', 'sdf234sdf3', 'Семен', 'семенович', 'Семенов', FALSE , 19103 ),
  ( 19003, 'ivan_s', 'sds768a6sa', 'Иван', 'Сергеевич', 'Сорокин', FALSE , 19104 ),
  ( 19004, 'Alex003', '00301031991df', 'Алексей', 'Петрович', 'Кабанов', FALSE , 19105 ),
  ( 19005, 'petr11', 'petrovech13', 'Петр', 'Петрович', 'Петров', FALSE , 19106);

/* users_group
   19101-19110
*/
INSERT INTO users_group (id,name,parent_id)
VALUES
  ( 19101, 'разработчики', null),
  ( 19102, 'тестировщики', null),
  ( 19103, 'бизнес-аналитики', null),
  ( 19104, 'php-разработчики', 191101),
  ( 19105, 'java-разработчики', 191101),
  ( 19106, 'C#-разработчики', 191101);

/* contact_type
   19111-19120
*/
INSERT INTO contacts_type (id,name,`regexp`)
VALUES
(19111,"email","^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"),
(19112,"URL","^((https?|ftp)\:\/\/)?([a-z0-9]{1})((\.[a-z0-9-])|([a-z0-9-]))*\.([a-z]{2,6})(\/?)$"),
(19113,"phone number","^\+\d{2}\(\d{3}\)\d{3}-\d{2}-\d{2}$");


/* contact
    19121-19200
*/
/* mobile */
INSERT INTO contact (id,name,user_id,type_id)
VALUES
  ( 19121, '552-22-11', 19000, 19111 ),
  ( 19122, '152-22-11', 19001, 19111 ),
  ( 19123, '252-22-11', 19002, 19111 ),
  ( 19124, '534-45-23', 19004, 19111 ),
  ( 19125, '678-32-12', 19005, 19111 ),
  ( 19126, '433-33-21', 19006, 19111 ),
/* home */
  ( 6124, '43-56-45', 6000, 6112 ),
  ( 6125, '15-22-13', 6001, 6112 ),
  ( 6126, '26-22-09', 6002, 6112 ),
  ( 6127, '55-22-11', 6003, 6112 ),
  ( 6128, '15-89-09', 6004, 6112 ),
  ( 6129, '34-22-11', 6005, 6112 ),
/* address */
  ( 6130, 'ул.Бесконечная 8', 6000, 6113 ),
  ( 6131, 'ул.Перепечиной 13', 6001, 6113 ),
  ( 6132, 'ул.Д. Бородавки 2', 6002, 6113 ),
  ( 6133, 'ул.Остащенки 23', 6003, 6113 ),
  ( 6134, 'ул.Держицкого 54', 6004, 6113 ),
  ( 6135, 'ул.Невероятная 911', 6005, 6113 );

/* projects_category
  19201-19210
*/
INSERT INTO projects_category (id,name,parent_id)
VALUES
  ( 19201, 'Web', NULL ),
  ( 19202, 'Desktop', NULL ),
  ( 19203, 'MCU', NULL );

/* project
   19211-19220
*/
INSERT INTO project (id,name,description,category_id,manager_id)
VALUES
  ( 19211, 'Поисковая система SS', 'Super Search', 19201, 19000 ),
  ( 19212, 'ТM', 'Time Management', 19202, 19001 );

/* team
  19221-19230
*/
INSERT INTO team (id,project_id,leader_id)
VALUES
  ( 19221, 19211, 19001 ),
  ( 19222, 19212, 19002 );

/* employee
    19231-19240
 */
/*first team*/
INSERT INTO employee (id,user_id,team_id,role)
VALUES
  ( 19231, 19000, 19221, 0 ),
  ( 19232, 19001, 19221, 0 ),
  ( 19233, 19002, 19221, 1 ),

/*second team*/
  ( 19234, 19000, 19222, 0 ),
  ( 19235, 19001, 19222, 0 ),
  ( 19236, 19002, 19222, 1 );

/* module
   19241-19250
*/
INSERT INTO module (id,name,parent_id,project_id)
VALUES
  ( 19241, 'front', NULL, 19211),
  ( 19242, 'back', NULL, 19211);

/* actor
   19251-19260
 */
INSERT INTO actor (id,name,project_id)
VALUES ( 19251 , 'Пользователь SS' , 19211 );

/* use_case
   19261-19270
*/
INSERT INTO use_case (id,name,module_id)
VALUES ( 19261, 'Выполнение поискового запроса', 19241);


/* actor_use_case_relation
   19271-19280
*/
INSERT INTO actor_use_case_relation (id,actor_id,use_case_id)
VALUES ( 19271, 19251, 19261);

/* requirement
   19281-19290
*/
INSERT INTO requirement (id,name,description,importance,change_probability,use_case_id,module_id)
VALUES ( 19281, 'Основное требование' , 'Создать поисковую систему' , 1 , 0 , 19261 , NULL );

/* task_category
  19291-19299
*/
INSERT INTO tasks_category (id,name,parent_id)
VALUES
  ( 19291, 'Important', NULL ),
  ( 19292, 'Optional', NULL ),
  ( 19293, 'Not Important', NULL );

/* task
   19300-19400
*/
INSERT INTO task (id,name,description,plan_time,difficulty,open_date,accept_date,close_date,category_id,requirement_id,module_id,employee_id,status)
VALUES
  ( 19300, 'front end', 'Создание пользвательского интерфейса', 200, 1, "2015-03-06", NULL , NULL , 19291 , 19281 , NULL, NULL , 0),
  ( 19301, 'back end', 'Создание архитектуры приложения для взаимодействия с БД и схемы БД', 200, 1, "2015-03-06", NULL , NULL , 19291 , 19281 , NULL, NULL , 0),
  ( 19302, 'верстка', 'Верстка, HTML-шаблонов страниц', 100 , 0.5, "2015-03-06", NULL , NULL , 19291 , 19281 , NULL, NULL , 0),
  ( 19303, 'javascript', 'Добавление JS-эффектов к html-шаблонам', 100, 0.5, "2015-03-06", NULL , NULL , 19291 , 19281 , NULL, NULL , 0);


/* task_dependency
    19500-19600
*/
INSERT INTO tasks_dependency ( id, task_id, dependent_task_id )
VALUES
  ( 19501, 19300, 19303 ),
  ( 19502, 19302, 19303 );