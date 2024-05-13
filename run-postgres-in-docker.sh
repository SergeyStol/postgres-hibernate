#!/usr/bin/env bash

docker run -d --name post -p 5432:5432 \
-e POSTGRES_PASSWORD=123 \
--mount type=bind,source="$(pwd)"/postgres_data,target=/var/lib/postgresql/data \
postgres