DROP TABLE t_buzz;

CREATE TABLE if not exists t_buzz(
  `rank` INT,
  title VARCHAR(100) not null,
  description VARCHAR(300),
  created_date DATETIME,
  updated_date DATETIME,
  primary key(`rank`)
);
