-- schema owner
CREATE USER criteria WITH password 'criteria';

-- schema user
CREATE USER criteria_ms WITH password 'criteria_ms';

-- create schema
CREATE SCHEMA IF NOT EXISTS criteria AUTHORIZATION criteria;

GRANT USAGE ON SCHEMA criteria TO criteria_ms;

ALTER DEFAULT PRIVILEGES FOR USER criteria IN SCHEMA criteria GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON TABLES TO criteria_ms;
ALTER DEFAULT PRIVILEGES FOR USER criteria IN SCHEMA criteria GRANT USAGE ON SEQUENCES TO criteria_ms;
ALTER DEFAULT PRIVILEGES FOR USER criteria IN SCHEMA criteria GRANT EXECUTE ON FUNCTIONS TO criteria_ms;
