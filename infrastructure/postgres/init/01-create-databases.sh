#!/bin/bash

set -e

echo "Creating application databases..."

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE keycloak_db;
    CREATE DATABASE user_db;
    CREATE DATABASE project_db;
    CREATE DATABASE notification_db;
EOSQL

echo "Application databases created successfully."
