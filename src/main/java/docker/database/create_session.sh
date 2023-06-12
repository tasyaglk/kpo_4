#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER}"

echo "Creating database role: ${DB_USER}"

${POSTGRES} <<-EOSQL
CREATE USER ${DB_USER} WITH CREATEDB PASSWORD '${DB_PASSWORD}';
EOSQL

echo "Creating table: user"

${POSTGRES} <<-EOSQL
CREATE TABLE session (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES "user" (id),
  session_token VARCHAR,
  expires_at TIMESTAMP
);
EOSQL
