#!/bin/bash

BUILD_PATH=$(ls /home/ubuntu/build/*.jar)
APPLICATION_JAR_NAME=$(basename $BUILD_PATH)

echo "> build 파일명: $APPLICATION_JAR_NAME"

echo "> 현재 실행중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f $APPLICATION_JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $APPLICATION_JAR_NAME 배포"
APPLICATION_JAR=/home/ubuntu/build/$APPLICATION_JAR_NAME

nohup java -Duser.timezone=Asia/Seoul \
    -Dfile.encoding=UTF-8 \
    -Xms1G \
    -Xmx1G \
    -jar -Dspring.profiles.active=dev $APPLICATION_JAR > /dev/null 2> /dev/null < /dev/null &
