#!/bin/bash

docker-compose \
--env-file default.env \
--env-file mysql.env \
convert