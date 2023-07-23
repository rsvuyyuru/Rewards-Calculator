-- Inserted Values into Customer Table
INSERT INTO CUSTOMER(CUSTOMER_ID,FIRST_NAME, LAST_NAME) values ('101','Mike', 'Ross');
INSERT INTO CUSTOMER(CUSTOMER_ID,FIRST_NAME, LAST_NAME) values ('102','Doe', 'John');
INSERT INTO CUSTOMER(CUSTOMER_ID,FIRST_NAME, LAST_NAME) values ('103','Lubri', 'Wilson');
INSERT INTO CUSTOMER(CUSTOMER_ID,FIRST_NAME, LAST_NAME) values ('104','John', 'Miller');
INSERT INTO CUSTOMER(CUSTOMER_ID,FIRST_NAME, LAST_NAME) values ('105','Will', 'Smith');

--Inserted Values into Transactions Table

INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1001', '101', '2023-07-10 12:00:00', 100.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1002', '101', '2023-06-15 14:30:00', 150.90);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1003', '101', '2023-05-25 14:30:00', 80.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1004', '101', '2023-06-18 09:45:00', 60.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1005', '101', '2023-05-05 11:20:00', 70.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1006', '101', '2023-04-05 12:20:00', 40.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1007', '101', '2023-04-25 19:45:00', 135.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1008', '101', '2023-03-08 20:40:10', 250.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1009', '101', '2023-07-10 20:40:10', 77.90);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('1010', '101', '2023-03-08 10:40:10', 190.00);

INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2001', '102', '2023-07-05 11:45:00', 75.80);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2002', '102', '2023-06-20 13:15:00', 200.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2003', '102', '2023-07-15 10:00:00', 120.23);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2004', '102', '2023-06-25 12:00:00', 150.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2005', '102', '2023-04-07 07:06:00', 45.50);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2006', '102', '2023-03-17 06:22:00', 55.50);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2007', '102', '2023-05-07 12:06:00', 170.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2008', '102', '2023-05-27 09:16:00', 200.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2009', '102', '2023-04-07 10:06:00', 88.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('2010', '102', '2023-03-17 06:22:00', 08.00);

INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3001', '103', '2023-04-10 08:00:00', 90.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3002', '103', '2023-03-05 15:20:00',70.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3003', '103', '2023-02-15 15:06:00',195.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3004', '103', '2023-04-25 11:09:00',180.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3005', '103', '2023-06-20 10:20:00',115.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3006', '103', '2023-07-15 15:12:00',20.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3007', '103', '2023-05-25 14:10:00',165.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3008', '103', '2023-01-01 09:30:00', 13.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3009', '103', '2023-02-15 11:10:00',90.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('3010', '103', '2023-07-15 19:20:00',108.90);

INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4001', '104', '2023-04-11 01:00:00', 120.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4002', '104', '2023-06-07 02:30:00', 88.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4003', '104', '2023-06-30 09:30:00', 32.34);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4004', '104', '2023-03-25 08:30:00', 97.90);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4005', '104', '2023-05-18 04:00:00', 12.30);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4006', '104', '2023-07-12 13:30:00', 03.44);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4007', '104', '2022-12-25 19:30:00', 17.90);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4008', '104', '2023-01-13 05:30:00', 376.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4009', '104', '2023-07-12 10:30:00', 18.90);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('4010', '104', '2023-03-25 18:30:00', 198.00);

INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5001', '105', '2023-08-05 10:00:00', 120.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5002', '105', '2023-07-09 19:00:00', 17.82);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5003', '105', '2023-07-20 20:30:00', 287.67);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5004', '105', '2023-05-14 14:00:00', 21.38);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5005', '105', '2023-05-10 10:30:00', 78.61);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5006', '105', '2023-04-20 12:10:00', 82.73);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5007', '105', '2023-03-12 19:05:00', 27.67);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5008', '105', '2023-03-27 11:20:00', 75.00);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5009', '105', '2023-05-10 20:30:00', 10.29);
INSERT INTO TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) VALUES ('5010', '105', '2023-04-20 02:10:00', 92.37);
