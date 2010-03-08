DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee 
  (UserName VARCHAR(50) PRIMARY KEY,
   Password VARCHAR(50),
   FirstName VARCHAR(50),
   LastName VARCHAR(50),
   Dept VARCHAR(50),
   EmpDate DATE,
   EmailAddr VARCHAR(50),
   ModDate DATETIME);

DROP TABLE IF EXISTS EmployeeProjects;
CREATE TABLE EmployeeProjects
  (UserName VARCHAR(50) NOT NULL,
   ProjectName VARCHAR(50) NOT NULL,
   PRIMARY KEY (UserName, ProjectName));

DROP TABLE IF EXISTS Sizes;
CREATE TABLE Sizes
  (Id INT Primary KEY,
   Name VARCHAR(50));
INSERT INTO Sizes VALUES(1, 'Small');
INSERT INTO Sizes VALUES(2, 'Medium');
INSERT INTO Sizes VALUES(3, 'Large');

DROP TABLE IF EXISTS Toppings;
CREATE TABLE Toppings
  (Id INT Primary KEY,
   Name VARCHAR(50));
INSERT INTO Toppings VALUES(1, 'Pepperoni');
INSERT INTO Toppings VALUES(2, 'Italian Sausage');
INSERT INTO Toppings VALUES(3, 'Ham');
INSERT INTO Toppings VALUES(4, 'Ground Beef');
INSERT INTO Toppings VALUES(5, 'Garlic');


