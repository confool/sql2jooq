-- CREATE DATABASE school;


CREATE TABLE student
(
  name character varying(20) NOT NULL,
  age integer,
  sex character varying(20),
  signupdate date
)

INSERT INTO student VALUES("cnfree", 20, "male", 2013-12-22);