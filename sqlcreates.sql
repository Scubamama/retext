DROP TABLE Student;
DROP TABLE Books;
DROP TABLE User_Inventory;
DROP TABLE School;

CREATE TABLE Student (Id INTEGER PRIMARY KEY AUTO_INCREMENT, 
					Email varchar(256),
                    UserName varchar(20),
                    StudentPassword varchar(20),
                    TakeCards tinyint(1),
                    school varchar(256)
	);
    
CREATE TABLE Books (Id INTEGER PRIMARY KEY AUTO_INCREMENT,
					Title varchar(256),
                    Author varchar(256) DEFAULT NULL,
                    Edition varchar(32) DEFAULT NULL,
                    CourseDept varchar(256) DEFAULT NULL,
                    CourseNumber varchar(256) DEFAULT NULL,
                    Isbn varchar(18)
);

CREATE TABLE User_Inventory (Id INTEGER PRIMARY KEY AUTO_INCREMENT,
							 User_Id INT,
                             Book_Id INT,
                             Price DECIMAL(10,2) DEFAULT NULL,
                             Sold tinyint(1)
);

CREATE TABLE School (Id INTEGER PRIMARY KEY AUTO_INCREMENT,
					 SchoolName varchar(256),
                     NickName varchar(256),
                     City varchar(256),
                     Campus varchar(256)
);

