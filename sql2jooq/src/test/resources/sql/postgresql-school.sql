DROP SCHEMA IF EXISTS school CASCADE
;
CREATE SCHEMA school
;
CREATE TABLE school.student
(
  name character varying(20) NOT NULL,
  age integer,
  sex character varying(20),
  signupdate date
)
;

INSERT INTO school.student VALUES('cnfree', 20, 'male', date '2013-12-22')
;