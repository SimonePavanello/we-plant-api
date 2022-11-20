#!/bin/sh
screen -d -m java -jar target/nino-0.0.1-SNAPSHOT.war --spring.profiles.active=prod,swagger
tail -f /var/log/spring-boot/nino/$(date +%Y-%m-%d).log
