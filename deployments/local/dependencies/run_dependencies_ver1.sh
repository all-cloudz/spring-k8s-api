#!/bin/bash

if [[ ! -f ".env" ]];
then
  set -a
  source default.env
  source mysql.env
  set +a

  cat default.env | grep -v '^#' | awk -F '=' '{print $1 "=" ENVIRON[$1]}' > .default.env
  cat mysql.env | grep -v '^#' | awk -F '=' '{print $1 "=" ENVIRON[$1]}' > .mysql.env

  touch .env
  awk -F '=' '{map[$1]=$2} END {for (key in map) print key "=" map[key]}' .default.env .mysql.env | sort >> .env

  rm -f .default.env .mysql.env
fi

docker-compose convert