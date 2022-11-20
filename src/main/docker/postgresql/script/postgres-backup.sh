#!/bin/bash
cd db_backup
file=$(date +%F)_$(date +%T).backup
pg_dump --dbname=postgresql://we-plant:TuttoVerde!2018!@127.0.0.1:5432/we-plant -Fc --file=${file}
if [ "${?}" -eq 0 ]; then
  aws s3 cp ${file} s3://we-plant-database-backup
else
  echo "Error backing up postgres"
  exit 255
fi
