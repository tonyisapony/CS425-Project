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
  DROP TABLE Points;
  DROP TABLE Membership;
 
  

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
    CardNum INT NOT NULL,
    Address VARCHAR(30) NOT NULL,
    ExpDate DATE NOT NULL
    );
    
  CREATE TABLE Group1(
    id INT PRIMARY KEY NOT NULL,
    RestName VARCHAR(30) NOT NULL,
    Rating REAL NOT NULL
    );
    
  CREATE TABLE Group1Members(
    MemID INT NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE
    );
  
  CREATE TABLE Review(
    RestID INT NOT NULL,
    FOREIGN KEY(RestID) REFERENCES Group1(id),
    Review VARCHAR(180) NOT NULL
    );
  
CREATE TABLE Group2(
    id INT PRIMARY KEY NOT NULL,
    LaptopName VARCHAR(30) NOT NULL,
    Brand VARCHAR(30) NOT NULL,
    NumOfComments INT NOT NULL
    );
    
CREATE TABLE Group2Members(
    MemID INT NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE
    );
  
  CREATE TABLE LaptopComments(
    LapID INT NOT NULL,
    FOREIGN KEY(LapID) REFERENCES Group2,
    MemID INT NOT NULL,
    FOREIGN KEY(MemID) REFERENCES MEMBERSHIP,
    Comments VARCHAR(30)NOT NULL,
    CommentType VARCHAR(30) NOT NULL
    CHECK(CommentType='Help' OR CommentType='Review' OR CommentType='Sell' OR CommentType='Buy' OR CommentType='Trade'),
    NumOfComments INT NOT NULL,
    Price INT NOT NULL
    );
  
  CREATE TABLE Leader(
    MemID INT NOT NULL,
    FOREIGN KEY(memID) REFERENCES Membership(id) ON DELETE CASCADE,
    GroupNum INT NOT NULL
    );
  
  CREATE TABLE Points(
    Contribution VARCHAR(30) NULL,
    NumPoints INT NOT NULL
    );