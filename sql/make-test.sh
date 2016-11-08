#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

cd "$(dirname ${BASH_SOURCE[0]})"

psql -U postgres -c 'DROP DATABASE IF EXISTS gen'
psql -U postgres -c 'CREATE DATABASE gen'
cat test.sql | psql -U postgres gen
