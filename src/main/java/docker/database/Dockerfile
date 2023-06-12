FROM postgres:14.3-alpine

# Custom initialization scripts
COPY create_users.sh   /docker-entrypoint-initdb.d/10-create_users.sh
COPY create_databases.sh     /docker-entrypoint-initdb.d/20-create_databases.sh
COPY create_extensions.sh   /docker-entrypoint-initdb.d/30-create_extensions.sh
COPY create_session.sh   /docker-entrypoint-initdb.d/40-create_session.sh
