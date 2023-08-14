CREATE TABLE IF NOT EXISTS employee
(
    id                 BIGINT NOT NULL,
    name               VARCHAR(255),
    department         VARCHAR(255),
    company_name       VARCHAR(255),
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS address
(
    id                 BIGINT NOT NULL,
    city               VARCHAR(255),
    road_number        VARCHAR(255),
    employee_id        BIGINT,
    CONSTRAINT pk_address PRIMARY KEY (id)
);


ALTER TABLE IF EXISTS address
    ADD CONSTRAINT FK_ADDRESS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

CREATE TABLE IF NOT EXISTS post
(
    id      BIGINT NOT NULL,
    user_id BIGINT,
    title   VARCHAR(255),
    body    VARCHAR(255),
    CONSTRAINT pk_post PRIMARY KEY (id)
);