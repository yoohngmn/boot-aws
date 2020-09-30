#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=boot-aws

echo "> Copy build files"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Check running application pid"

CURRENT_PID=$(pgrep -f boot-aws | grep jar | awk '{print $1}')

echo "> App pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> Not terminate because of no running app"
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> Deploy new app"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> Authorization $JAR_NAME"

chmod +x $JAR_NAME

echo "> Run $JAR_NAME"

nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-real.properties, \
        -Dspring.profiles.active=real \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
