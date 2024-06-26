#!/bin/bash

ROOT_PATH="/home/ubuntu/deploy"
JAR="$ROOT_PATH/job-application.jar"

APP_LOG="$ROOT_PATH/job-application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

NOW=$(date +%c)

echo "[$NOW] $JAR 복사" >> $START_LOG
cp $ROOT_PATH/build/libs/LikeKNU-JobServer-1.0.0.jar $JAR

echo "[$NOW] > $JAR 실행" >> $START_LOG
source ~/.bash_profile
nohup java -jar $JAR > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG