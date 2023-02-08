use owp_pharmacy;


CREATE TABLE User (
	id BIGINT AUTO_INCREMENT,
    username varchar(20) not null unique,
    password varchar(20) not null,
    email varchar(50) not null unique,
    firstName varchar(15) not null,
    lastName varchar(15) not null,
    dateOfBirth Date not null,
    address varchar(40) not null,
    phNumber varchar(10) not null,
    registrationDate DateTime not null,
    userRole enum('USER', 'PHARMACIST', 'ADMIN'),
    blocked boolean not null,
    PRIMARY KEY (id)
);


CREATE TABLE MedicineCategory (
	id BIGINT AUTO_INCREMENT,
    title varchar(50) not null unique,
    purpose varchar(100) not null,
    description varchar(200) not null,
    PRIMARY KEY (id)
);

CREATE TABLE MedicineManufacturer(
	id BIGINT AUTO_INCREMENT,
    title varchar(50) not null,
    hqLocation varchar(3) not null,
    PRIMARY KEY (id)
);

CREATE TABLE Medicine(
	id VARCHAR(14),
    title varchar(50) not null,
    description varchar(300) not null,
    contraindications varchar(400) not null,
    medicineForm enum('SYRUP', 'INJECTION', 'FILM_COATED', 'CAPSULE_PILL'),
    picture varchar(100) not null,
    quantity int not null,
    price float not null,
    manufacturer_id BIGINT not null,
    category_id BIGINT not null,
    approved boolean not null,
    FOREIGN KEY (manufacturer_id) REFERENCES MedicineManufacturer(id) ON DELETE Cascade,
    FOREIGN KEY (category_id) REFERENCES MedicineCategory(id) ON DELETE Cascade,
    PRIMARY KEY (id)
);


CREATE TABLE LoyaltyCard (
	id BIGINT AUTO_INCREMENT,
    discount float not null,
    points int not null,
    user_id BIGINT, 
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE Cascade,
    PRIMARY KEY (id)
);

CREATE TABLE Comment (
	id BIGINT AUTO_INCREMENT,
    description varchar(500) not null,
    dateSubmission Date not null,
    user_id BIGINT not null,
    medicine_id varchar(14) not null,
    anonymous boolean not null,
    rating float not null,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE Cascade,
    FOREIGN KEY (medicine_id) REFERENCES Medicine(id) ON DELETE Cascade,
    PRIMARY KEY (id)
);


CREATE TABLE WishList (
	id BIGINT AUTO_INCREMENT,
    user_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE Cascade
);

CREATE TABLE WishItem (
	list_id BIGINT,
    medicine_id varchar(14), 
    FOREIGN KEY (list_id) REFERENCES WishList(id) ON DELETE Cascade,
    FOREIGN KEY (medicine_id) REFERENCES Medicine(id) ON DELETE Cascade,
    PRIMARY KEY (list_id, medicine_id)
);

CREATE TABLE ShoppingHistory (
	id BIGINT AUTO_INCREMENT,
	user_id BIGINT,
	FOREIGN KEY (user_id) REFERENCES User(id),
    PRIMARY KEY (id)
);

CREATE TABLE ShoppingHistoryItem (
	id BIGINT AUTO_INCREMENT,
	shopping_id BIGINT,
    medicine_id VARCHAR(14),
    quantity BIGINT,
    shopping_date Date,
	FOREIGN KEY (shopping_id) REFERENCES ShoppingHistory(id) ON DELETE Cascade,
    FOREIGN KEY (medicine_id) REFERENCES Medicine(id) ON DELETE Cascade,
    PRIMARY KEY (id)
);

    
CREATE TABLE MedicineOrder (
	id varchar(36), 
    creation_date DateTime,
    pharmacist_id BIGINT,
    order_status enum ('APPROVED', 'REJECTED', 'PENDING_CHANGES', 'PENDING_REVIEW'),
    order_feedback varchar(300),
    FOREIGN KEY (pharmacist_id) REFERENCES User(id) ON DELETE Cascade,
	PRIMARY KEY (id)
);



CREATE TABLE MedicineOrderItem (
	id BIGINT AUTO_INCREMENT,
    order_id varchar(36),
    medicine_id VARCHAR(14),
    order_description VARCHAR(500),
    quantity BIGINT,
    FOREIGN KEY (order_id) REFERENCES MedicineOrder(id),
    FOREIGN KEY (medicine_id) REFERENCES Medicine(id),
    PRIMARY KEY (id)
);
    

    
CREATE TABLE LoyaltyCardRequest (
	id BIGINT AUTO_INCREMENT,
	user_id BIGINT,
    request_status enum('APPROVED', 'REJECTED', 'PENDING'),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Discount (
	id varchar(36),
    start_date Date not null,
    end_date Date,
    PRIMARY KEY (id)
);

CREATE TABLE onDiscount (
	discount_id varchar(36),
    category_id BIGINT,
    FOREIGN KEY (discount_id) REFERENCES Discount(id) ON DELETE Cascade,
    FOREIGN KEY (category_id) REFERENCES MedicineCategory(id) ON DELETE Cascade,
    PRIMARY KEY (discount_id, category_id)
);

INSERT INTO User (username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked)
	values ('jankkk', '123123', 'janko@gmail.com', 'Janko', 'Rakonjac', '1995-08-07', 'Car Lazar 33 Novi Sad', '0631631631', '2023-01-13 10:54:00', 'USER', false);
INSERT INTO User (username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked)
	values ('atzooo', '123123', 'atzooo@gmail.com', 'Aca', 'Pavlovic', '1998-03-03', 'Papucarska 33 Novi Sad', '0631632222', '2023-01-13 10:54:00', 'USER', false);
INSERT INTO User (username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked)
	values ('rxd', '123123', 'rixenzi@gmail.com', 'Mateja', 'Rilak', '2002-02-11', 'Mornarska 26a Novi Sad', '0631631631', '2023-01-13 10:54:00', 'ADMIN', false);
INSERT INTO User (username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked)
	values ('saka', '123123', 'saka@gmail.com', 'Saka', 'Saka', '2001-08-08', 'Mornarska 26a Novi Sad', '0631631631', '2023-01-13 10:54:00', 'PHARMACIST', false);


INSERT INTO MedicineCategory (title, purpose, description) values ('Pain killers', 'Pain killers', 'Used to kill pain');
INSERT INTO MedicineCategory (title, purpose, description) values ('Antihistaminics', 'Alergic reactions', 'Used to kill alergic reactions');
INSERT INTO MedicineCategory (title, purpose, description) values ('Antibacterials', 'Bactery infections', 'Used to kill bacteries');


INSERT INTO MedicineManufacturer (title, hqLocation) VALUES ('Hemofarm', 'SRB');
INSERT INTO MedicineManufacturer (title, hqLocation) VALUES ('Galenika', 'SRB');
INSERT INTO MedicineManufacturer (title, hqLocation) VALUES ('Johnson & Johnson', 'USA');


INSERT INTO Medicine (id, title, description, contraindications, medicineForm, picture, quantity, price, manufacturer_id, category_id, approved) 
values ('sUITlhBvx6', 'Brufen', 'Pain killer', 'Alergic reaction', 'FILM_COATED', 'brufen.png', 1, 200, 1, 1, true);
INSERT INTO Medicine (id, title, description, contraindications, medicineForm, picture, quantity, price, manufacturer_id, category_id, approved) 
values ('QtXis8DsAj', 'Nixar', 'Alergic reactions', 'Bubreg moze da strada', 'FILM_COATED', 'nixar.png', 1, 250, 1, 1, true);
INSERT INTO Medicine (id, title, description, contraindications, medicineForm, picture, quantity, price, manufacturer_id, category_id, approved) 
values ('AtCiz8DsDj', 'Paracetamol', 'Pain killer', 'Alergic reaction', 'FILM_COATED', 'paracetamol.png', 0, 300, 1, 1, true);

INSERT INTO WishList (user_id) values (1);
INSERT INTO WishList (user_id) values (2);
INSERT INTO WishList (user_id) values (3);

INSERT INTO WishItem (list_id, medicine_id) values (1,'sUITlhBvx6');
INSERT INTO WishItem (list_id, medicine_id) values (1,'QtXis8DsAj');


INSERT INTO ShoppingHistory (user_id) values (1);
INSERT INTO ShoppingHistory (user_id) values (2);

INSERT INTO ShoppingHistoryItem (shopping_id, medicine_id, shopping_date, quantity) VALUES (1, 'sUITlhBvx6', '2022-1-24', 5);


INSERT INTO Comment (description, dateSubmission, user_id, medicine_id, anonymous, rating) values ('Komentar za nixar', '2023-1-29', '1', 'QtXis8DsAj', false, 5);
INSERT INTO Comment (description, dateSubmission, user_id, medicine_id, anonymous, rating) values ('Komentar za nixar 2', '2023-1-29', '3', 'QtXis8DsAj', false, 3);
INSERT INTO Comment (description, dateSubmission, user_id, medicine_id, anonymous, rating) values ('Komentar za nixar 3', '2023-1-29', '2', 'QtXis8DsAj', false, 5);

INSERT INTO LoyaltyCardRequest (user_id, request_status) VALUES (1, 'PENDING');

