#!/bin/bash

if [[ ! -f ".env" ]];
then
  touch .env
  awk -F '=' '{vals[$1]=$2} END {for (var in vals) print var "=" vals[var]}' default.env mysql.env > .env
fi

docker-compose convert