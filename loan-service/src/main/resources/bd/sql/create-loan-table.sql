CREATE TABLE loan_applications
(
    id               BIGSERIAL PRIMARY KEY,
    loan_amount      DECIMAL(15, 2) NOT NULL,
    loan_term_months INTEGER        NOT NULL,
    user_income      DECIMAL(15, 2) NOT NULL,
    current_debt     DECIMAL(15, 2) NOT NULL,
    loan_rating      INTEGER        NOT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'PROCESSING',
    created_at       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_credit_applications_status ON loan_applications (status);
