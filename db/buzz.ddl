DROP TABLE t_buzz;

CREATE TABLE if not exists t_buzz(
  id INTEGER not null AUTO_INCREMENT,
  ranking VARCHAR(3) not null,
  title VARCHAR(100) not null,
  description VARCHAR(300),
  created_date DATETIME,
  updated_date DATETIME,
  primary key(id)
);
