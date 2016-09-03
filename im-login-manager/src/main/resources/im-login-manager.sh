#!/bin/sh

#SERVER_NAME="im-group-manager"
SERVER_VERSION="0.0.1-SNAPSHOT"
JAVA=`which java`
SERVER_JAVA_OPTS="-XX:MaxPermSize=192m -Xmx512m -Xms512m"
 

doStop () {
  for id in  $(ps -ef|grep java|grep ${SERVER_NAME}|awk '{print $2}')
	do
		kill -9 ${id}
		echo "kill -9" ${id}
	done
}

doStart () {
	if [ -e ${SERVER_NAME}-${SERVER_VERSION}.jar ]
	then
		echo  "${JAVA} -jar ${SERVER_JAVA_OPTS} ${SERVER_NAME}-${SERVER_VERSION}.jar &"
		${JAVA} -jar ${SERVER_JAVA_OPTS} ${SERVER_NAME}-${SERVER_VERSION}.jar &
	else
		echo "${SERVER_NAME}-${SERVER_VERSION}.jar is not exists!"
	fi
}
 

reStart () {
	doStop
	doStart
}


update () {
		if [ ! -d bakfile ]
		then
			mkdir bakfile
		fi
        DATE=$(date +%Y%m%d%H%M%S)
        mv ${SERVER_NAME}-${SERVER_VERSION}.jar ./bakfile/${SERVER_NAME}-${SERVER_VERSION}.jar.${DATE}.bak
        cp /home/gitlab-runner/.m2/repository/com/xuanwu/im/${SERVER_NAME}/${SERVER_VERSION}/${SERVER_NAME}-${SERVER_VERSION}.jar .
        reStart
}

SERVER_NAME=$0
SERVER_NAME=${SERVER_NAME%.*}
SERVER_NAME=${SERVER_NAME##*/}

case "$1" in
  start)
    echo "Starting ${SERVER_NAME}-${SERVER_VERSION}..."
    doStart
    echo "${SERVER_NAME}-${SERVER_VERSION} server booted."
   ;;
  restart)
    echo "restart ${SERVER_NAME}-${SERVER_VERSION} server ..."
    reStart
    ehco "${SERVER_NAME}-${SERVER_VERSION} server is restart"
   ;;
  stop)
    echo "Stopping ${SERVER_NAME}-${SERVER_VERSION} server..."
    doStop
    echo "${SERVER_NAME}-${SERVER_VERSION} server is stopped."
    ;;
  update)
    echo "updating ${SERVER_NAME}-${SERVER_VERSION} server..."
    update
    echo "${SERVER_NAME}-${SERVER_VERSION} server is updated."
    ;;
  *)
    # Print help, don't advertise halt, it's nasty
    echo "Usage: $0 {start|stop|restart|update}" 1>&2
    exit 1
    ;;
esac

exit 0
