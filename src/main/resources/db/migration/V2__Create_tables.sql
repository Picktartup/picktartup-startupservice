CREATE TABLE startup (
                         startup_id BIGINT NOT NULL PRIMARY KEY,
                         name VARCHAR(20) NOT NULL,
                         category VARCHAR(100) NOT NULL,
                         progress INTEGER NOT NULL,
                         investment_start_date TIMESTAMP NOT NULL,
                         investment_target_deadline TIMESTAMP NOT NULL,
                         goal_coin INTEGER NOT NULL,
                         current_coin FLOAT8,
                         funding_progress INTEGER,
                         wallet_id BIGINT NOT NULL
);


CREATE TABLE startup_details (
                                 startup_id BIGINT NOT NULL PRIMARY KEY,
                                 description VARCHAR(200) NOT NULL,
                                 investment_status VARCHAR(10) NOT NULL,
                                 investment_round VARCHAR(20) NOT NULL,
                                 address VARCHAR(100) NOT NULL,
                                 ceo_name VARCHAR(10) NOT NULL,
                                 page VARCHAR(30) NOT NULL,
                                 establishment_date VARCHAR(30) NOT NULL,
                                 contract_period int4 NOT NULL,
                                 expected_roi FLOAT8,
                                 roi FLOAT8,
                                 registration_num VARCHAR(20) NOT NULL,
                                 CONSTRAINT fk_startup_id FOREIGN KEY (startup_id) REFERENCES startup(startup_id)
);




CREATE TABLE ssi (
                     ssi_id BIGINT NOT NULL PRIMARY KEY,
                     eval_date TIMESTAMP NOT NULL,
                     eval_description TEXT,
                     people_grade VARCHAR(10) NOT NULL,
                     performance_grade VARCHAR(10) NOT NULL,
                     potential_grade VARCHAR(10) NOT NULL,
                     product_grade VARCHAR(10) NOT NULL,
                     startup_id BIGINT NOT NULL,
                     CONSTRAINT fk_startup_id_ssi FOREIGN KEY (startup_id) REFERENCES startup(startup_id)
);

