#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER}"

echo "Creating database role: ${DB_USER}"

${POSTGRES} <<-EOSQL
CREATE USER ${DB_USER} WITH CREATEDB PASSWORD '${DB_PASSWORD}';
EOSQL

echo "Creating table: user"

${POSTGRES} <<-EOSQL
CREATE TABLE "order" (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES "user" (id),
  status VARCHAR,
  special_requests TEXT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
EOSQL
