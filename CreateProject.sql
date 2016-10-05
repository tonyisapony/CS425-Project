--Ruben Estrada  -- Napoleon Tolbert  --Tony Patlan
-------------------------------------
--DROPS FOR REMAKING TABLES
-------------------------------------
  DROP TABLE BankAcc;
  DROP TABLE CreditCard;
  DROP TABLE Group1Members;
  DROP TABLE Review;
  DROP TABLE LaptopComments;
  DROP TABLE Group1;
  DROP TABLE Group2;
  DROP TABLE Group2Members;
  DROP TABLE Leader;
  DROP TABLE Group1Points;
  DROP TABLE Group2Points;
  DROP TABLE Membership;
 /*lol*/
  

CREATE TABLE Membership(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    dateOfReg DATE DEFAULT SYSDATE,
    balance REAL DEFAULT NULL,
    memPoints INT DEFAULT 0
    );
  
CREATE TABLE BankAcc(
    MemID INT NOT NULL, 
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE,
    BankName VARCHAR(30) NOT NULL, 
    RoutNumber INT NOT NULL, 
    AccNum INT NOT NULL, 
    InitVal REAL DEFAULT NULL
    );
    
CREATE TABLE CreditCard(
    MemID INT NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE,
    CardName VARCHAR(30) NOT NULL,
    Company VARCHAR(30) NOT NULL,
    CardNum INT UNIQUE NOT NULL,
    Address VARCHAR(30) NOT NULL,
    ExpDate DATE NOT NULL,
    Balance INT DEFAULT 0
    );
    
  CREATE TABLE Group1(
    id INT PRIMARY KEY NOT NULL,
    RestName VARCHAR(30) NOT NULL,
    Rating REAL DEFAULT 0
    );
    
  CREATE TABLE Group1Members(
    MemID INT UNIQUE NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE
    );
  
  CREATE TABLE Review(
    RestID INT NOT NULL,
    FOREIGN KEY(RestID) REFERENCES Group1(id) ON DELETE CASCADE,
    Review VARCHAR(180) NOT NULL
    );
  
CREATE TABLE Group2(
    id INT PRIMARY KEY NOT NULL,
    LaptopName VARCHAR(30) NOT NULL,
    Brand VARCHAR(30) NOT NULL,
    NumOfComments INT DEFAULT 0,
    Rate INT DEFAULT 0,
    Price INT DEFAULT 0
    );
    
CREATE TABLE Group2Members(
    MemID INT UNIQUE NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE
    );
  
  CREATE TABLE LaptopComments(
    ID INT NOT NULL,
    LapID INT NOT NULL,
    FOREIGN KEY(LapID) REFERENCES Group2 ON DELETE CASCADE,
    MemID INT NOT NULL,
    FOREIGN KEY(MemID) REFERENCES MEMBERSHIP ON DELETE CASCADE,
    Comments VARCHAR(30)NOT NULL,
    CommentType VARCHAR(30) NOT NULL
    CHECK(CommentType='Help' OR CommentType='Review' OR CommentType='Sell' OR CommentType='Buy' OR CommentType='Trade')
    );
  
  CREATE TABLE Leader(
    MemID INT NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE,
    GroupNum INT NOT NULL
    );
  
CREATE TABLE Group1Points(
    Contribution VARCHAR(30) NULL,
    NumPoints INT DEFAULT 0
    );
CREATE TABLE Group2Points(
    Contribution VARCHAR(30) NULL,
    NumPoints INT DEFAULT 0
    );
    
CREATE VIEW viewAll AS SELECT id,name,mempoints FROM MEMBERSHIP;
    
INSERT INTO Group1Points (Contribution) VALUES('add rest');
INSERT INTO Group1Points (Contribution) VALUES('rate');
INSERT INTO Group1Points (Contribution) VALUES('review');
/*save to pull*/

