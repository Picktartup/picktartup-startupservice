DROP TABLE IF EXISTS startup CASCADE;
DROP TABLE IF EXISTS startupdetails CASCADE;
DROP TABLE IF EXISTS ssi CASCADE;


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
                         ceo_user_id BIGINT NOT NULL,
                         logo_url VARCHAR(255),
                         industry_type VARCHAR(100),
                         campaign_id INTEGER
);


CREATE TABLE startup_details (
                                 startup_id BIGINT NOT NULL PRIMARY KEY,
                                 description VARCHAR(200) NOT NULL,
                                 investment_status VARCHAR(10) NOT NULL,
                                 address VARCHAR(100) NOT NULL,
                                 ceo_name VARCHAR(10) NOT NULL,
                                 page VARCHAR(30) NOT NULL,
                                 establishment_date VARCHAR(30) NOT NULL,
                                 contract_period int4 NOT NULL,
                                 expected_roi FLOAT8,
                                 roi FLOAT8,
                                 registration_num VARCHAR(20) NOT NULL,
                                 signature VARCHAR(255),
                                 current_round VARCHAR(20),
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

-- 새로운 테이블 생성
CREATE TABLE startup_monthlymetrics (
                                        monthly_id BIGINT NOT NULL PRIMARY KEY,
                                        startup_id BIGINT NOT NULL,
                                        metric_date TIMESTAMP NOT NULL,
                                        monthly_revenue BIGINT,
                                        monthly_operating_profit BIGINT,
                                        monthly_net_profit BIGINT,
                                        mau INTEGER,
                                        employee_count INTEGER,
                                        created_at TIMESTAMP,
                                        data_source VARCHAR(50),
                                        CONSTRAINT fk_startup_monthly FOREIGN KEY (startup_id) REFERENCES startup(startup_id)
);

CREATE TABLE startup_annualmetrics (
                                       annual_id BIGINT NOT NULL PRIMARY KEY,
                                       startup_id BIGINT NOT NULL,
                                       year INTEGER NOT NULL,
                                       annual_revenue BIGINT,
                                       operating_profit BIGINT,
                                       net_profit BIGINT,
                                       total_asset BIGINT,
                                       created_at TIMESTAMP,
                                       data_source VARCHAR(50),
                                       investment_round VARCHAR(20),
                                       CONSTRAINT fk_startup_annual FOREIGN KEY (startup_id) REFERENCES startup(startup_id)
);