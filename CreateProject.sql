--Ruben Estrada  -- Napoleon Tolbert  --Tony Patlan
-------------------------------------
--DROPS FOR REMAKING TABLES
-------------------------------------
DROP TABLE Membership;
  DROP TABLE BankAcc;
  DROP TABLE CreditCard;
  DROP TABLE Group1;
  DROP TABLE Review;
  DROP TABLE Group2;
  DROP TABLE LaptopComments;
  DROP TABLE Leader;
  DROP TABLE Points;

CREATE TABLE Membership(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    dateOfReg DATE DEFAULT SYSDATE,
    balance DOUBLE DEFAULT NULL,
    memPoits INT DEFAULT NULL
    );
  
CREATE TABLE BankAcc(
    MemID INT NOT NULL, 
    FOREIGN KEY(memID) REFERENCES Membership(id),
    BankName VARCHAR(30) NOT NULL, 
    RoutNumber INT NOT NULL, 
    AccNum INT NOT NULL, 
    InitVal REAL DEFAULT NULL
    );
    
CREATE TABLE CreditCard(
    MemID INT NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id),
    CardName VARCHAR(30) NOT NULL,
    Company VARCHAR(30) NOT NULL,
    CardNum INT NOT NULL,
    Address VARCHAR(30) NOT NULL,
    ExpDate DATE NOT NULL
    );
    
  CREATE TABLE Group1(
    id INT PRIMARY KEY NOT NULL,
    RestName VARCHAR(30) NOT NULL,
    Rating REAL NOT NULL
    );
  
  CREATE TABLE Review(
    RestID INT NOT NULL,
    FOREIGN KEY(RestID) REFERENCES Group1(id),
    Review VARCHAR(30) NOT NULL
    );
  
  CREATE TABLE Group2(
    id INT PRIMARY KEY NOT NULL,
    LaptopName VARCHAR(30) NOT NULL,
    Brand VARCHAR(30) NOT NULL
    );
  
  CREATE TABLE LaptopComments(
    LapID INT NOT NULL,
    FOREIGN KEY(LapID) REFERENCES Group2,
    Comments VARCHAR(30)NOT NULL,
    CommentType VARCHAR(30) NOT NULL
    CHECK(CommentType='Help' OR CommentType='Review' OR CommentType='Sell' OR CommentType='Buy' OR CommentType='Trade')
    );
  
  CREATE TABLE Leader(
    MemID INT NOT NULL,
    GroupNum INT NOT NULL
    );
  
  CREATE TABLE Points(
    Contribution VARCHAR(30) NULL,
    NumPoints INT NOT NULL
    );