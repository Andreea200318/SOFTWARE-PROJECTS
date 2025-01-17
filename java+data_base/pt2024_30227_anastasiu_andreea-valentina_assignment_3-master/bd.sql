USE new_schema;

CREATE TABLE client (
    id INT AUTO_INCREMENT,
	nume VARCHAR (255) ,
    email VARCHAR (255),
    PRIMARY KEY (id) 
);

CREATE TABLE Produs (
    
    id INT AUTO_INCREMENT,
    nume VARCHAR (255) ,
    cantitate INT,
    pret double,
    PRIMARY KEY (id) 
);



CREATE TABLE orders (
    id INT AUTO_INCREMENT,
    clientid INT,
	produsid INT,
    pret double,
    cantitate INT,
     FOREIGN KEY (clientid) REFERENCES client (id),
       FOREIGN KEY (produsid) REFERENCES Produs (id),
    PRIMARY KEY (id) 
);

DROP TABLE client;
drop table Produs;
drop table orders;
drop table bill;

INSERT INTO client (id, nume, email)
VALUES(1, 'Maris Filip', 'maris.filip@example.com');
INSERT INTO client (id, nume, email)
VALUES(2, 'Atanasoaei Filip', 'at.filip@example.com');
INSERT INTO client (id, nume, email)
VALUES(3, 'Nastaca Maria', 'n.maria@example.com');
INSERT INTO Produs(id , nume , cantitate , pret)
VALUES(1,'apa',5,2);
INSERT INTO Produs(id, nume , cantitate , pret)
VALUES(2,'ciocolata',2,6);
INSERT INTO Produs(id , nume , cantitate , pret)
VALUES(3,'paste',1,4);
INSERT INTO Produs(id, nume , cantitate , pret)
VALUES(4,'suc',2,8);

INSERT INTO orders(id,clientid,produsid,pret,cantitate)
VALUES(1,1,1,2,1);
SELECT * FROM orders WHERE produsId = 1;

ALTER TABLE orders
DROP FOREIGN KEY produsid;







