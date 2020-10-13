-- schema owner
CREATE USER employers WITH password 'employers';

-- schema user
CREATE USER employers_ms WITH password 'employers_ms';

-- create schema
CREATE SCHEMA employers AUTHORIZATION employers;

GRANT USAGE ON SCHEMA employers TO employers_ms;

ALTER DEFAULT PRIVILEGES FOR USER employers IN SCHEMA employers GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON TABLES TO employers_ms;
ALTER DEFAULT PRIVILEGES FOR USER employers IN SCHEMA employers GRANT USAGE ON SEQUENCES TO employers_ms;
ALTER DEFAULT PRIVILEGES FOR USER employers IN SCHEMA employers GRANT EXECUTE ON FUNCTIONS TO employers_ms;
