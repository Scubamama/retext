DROP TABLE User_Inventory; /*have to drop child table first*/
DROP TABLE Student;
DROP TABLE Books;
DROP TABLE School;

CREATE TABLE Student (Id INTEGER PRIMARY KEY AUTO_INCREMENT, 
					Email varchar(256),
                    UserName varchar(20),
                    StudentPassword varchar(20),
                    TakeCards int,
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
                             Sold int,
                             FOREIGN KEY (User_Id) 
								REFERENCES Student (Id)
                                ON DELETE CASCADE,
							 FOREIGN KEY (Book_Id) 
								REFERENCES Books (Id)
                                ON DELETE CASCADE
);

CREATE TABLE School (Id INTEGER PRIMARY KEY AUTO_INCREMENT,
					 SchoolName varchar(256),
                     NickName varchar(256),
                     City varchar(256),
                     Campus varchar(256)
);
INSERT INTO `retext`.`books` (`Title`, `Author`, `Edition`, `CourseDept`, `CourseNumber`, `Isbn`) VALUES ('All Classes are Great', 'Ima Greatauth', '2nd', 'Soc', '1001', '123456789');
INSERT INTO `retext`.`books` (`Title`, `Author`, `Edition`, `CourseDept`, `CourseNumber`, `Isbn`) VALUES ('All Classes are Terrible', 'Ima Lousyauth', '4th', 'Soc', '1002', '234567890');
INSERT INTO `retext`.`school` (`SchoolName`, `City`, `Campus`) VALUES ('St. Louis Community College', 'Ferguson', 'Florissant Valley');
INSERT INTO `retext`.`school` (`SchoolName`, `City`, `Campus`) VALUES ('Oklahoma University', 'Norman', 'main');
INSERT INTO `retext`.`student` (`Email`, `UserName`, `StudentPassword`, `TakeCards`, `school`) VALUES ('sclause@myschool.edu', 'santa123', 'Isleigh1', 0, 'mizzou');
INSERT INTO `retext`.`student` (`Email`, `UserName`, `StudentPassword`, `TakeCards`, `school`) VALUES ('mclause@myschool.edu', 'mizsanta', 'Ibakeit1', 1, 'mizzou'); 
INSERT INTO `retext`.`student` (`Email`, `UserName`, `StudentPassword`, `TakeCards`, `school`) VALUES ('rrudolf@myschool.edu', 'rednose1', 'santasfav', 0, 'mizzou'); 