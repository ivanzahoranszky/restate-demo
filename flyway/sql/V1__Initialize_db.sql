CREATE TABLE transactionfees
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    transaction_id VARCHAR(255) UNIQUE NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    asset_type VARCHAR(255) NOT NULL,
    asset VARCHAR(255) NOT NULL
);

CREATE INDEX transactionfees_transaction_id ON transactionfees (transaction_id);