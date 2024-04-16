#!/bin/bash

set -a
source default.env
source mysql.env
set +a

cat default.env | grep -v '^#' | awk -F '=' '{print $1 "=" ENVIRON[$1]}' > .default.env
cat mysql.env | grep -v '^#' | awk -F '=' '{print $1 "=" ENVIRON[$1]}' > .mysql.env

docker-compose \
--env-file .default.env \
--env-file .mysql.env \
up -d

rm -f .default.env .mysql.env