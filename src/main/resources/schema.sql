-- Create a Customer table
CREATE TABLE IF NOT EXISTS `customer`  (
    `CUSTOMER_ID` VARCHAR(50) PRIMARY KEY,
    `FIRST_NAME` VARCHAR(255) ,
    `LAST_NAME` VARCHAR(255)
);

-- Create Transaction Table
CREATE TABLE IF NOT EXISTS `transactions`(
      `TRANSACTION_ID` VARCHAR(50) PRIMARY KEY,
      FOREIGN KEY (`CUSTOMER_ID`) REFERENCES Customer(`CUSTOMER_ID`),
      `TRANSACTION_DATE` TIMESTAMP,
      `AMOUNT` DECIMAL(13,2)
);

