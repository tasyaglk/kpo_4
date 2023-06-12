#!/bin/bash
set -e

POSTGRES_DB="psql --username ${POSTGRES_USER} -d ${DB_NAME}"

echo "Creating extension pgcrypto in ${DB_NAME}"
${POSTGRES_DB} <<-EOSQL
CREATE EXTENSION pgcrypto;
EOSQL
