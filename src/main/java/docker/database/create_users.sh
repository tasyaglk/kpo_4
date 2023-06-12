#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER}"

echo "Creating database role: ${DB_USER}"

${POSTGRES} <<-EOSQL
CREATE USER ${DB_USER} WITH CREATEDB PASSWORD '${DB_PASSWORD}';
EOSQL

echo "Creating table: user"

${POSTGRES} <<-EOSQL
CREATE TABLE user (
  id SERIAL PRIMARY KEY,
  username VARCHAR UNIQUE,
  email VARCHAR UNIQUE,
  password_hash VARCHAR,
  role VARCHAR,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
EOSQL
