#!/bin/sh

SERVER_NAME="im-login-manager"
SERVER_VERSION="0.0.1-SNAPSHOT"
JAVA=`which java`
SERVER_PID_DIR="./pid"
SERVER_JAVA_OPTS="-XX:MaxPermSize=192m -Xmx512m -Xms512m"
 

finalStop () {
  for id in  $(cat ${SERVER_PID_DIR})
	do
		kill -9 ${id}
		echo "kill -9" ${id}
	done
  rm -rf ${SERVER_PID_DIR}
}

doStop () {
  for id in  $(cat ${SERVER_PID_DIR})
	do
		kill ${id}
		echo "kill " ${id}
	done
}

doStart () {
	if [ -e ${SERVER_NAME}-${SERVER_VERSION}.jar ]
	then
		echo  '${JAVA} -jar ${SERVER_JAVA_OPTS} ${SERVER_NAME}-${SERVER_VERSION}.jar &'
		${JAVA} -jar ${SERVER_JAVA_OPTS} ${SERVER_NAME}-${SERVER_VERSION}.jar &
		filePID=$!
		echo ${filePID} > ${SERVER_PID_DIR}
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

cd "$(dirname "$0")"


case "$1" in
  start)
    echo "Starting ${SERVER_NAME}-${SERVER_VERSION}..."
    doStart
    echo "${SERVER_NAME}-${SERVER_VERSION} server booted."
   ;;
  restart)
    echo "restart ${SERVER_NAME}-${SERVER_VERSION} server ..."
    reStart
    echo "${SERVER_NAME}-${SERVER_VERSION} server is restart"
   ;;
  stop)
    echo "Stopping ${SERVER_NAME}-${SERVER_VERSION} server..."
    doStop
    echo "${SERVER_NAME}-${SERVER_VERSION} server is stopped."
    ;;
  finalStop)
  	echo "Final stopping ${SERVER_NAME}-${SERVER_VERSION} server..."
    finalStop
    echo "${SERVER_NAME}-${SERVER_VERSION} server is stopped."
    ;;
  *)
    # Print help, don't advertise halt, it's nasty
    echo "Usage: $0 {start|stop|restart}" 1>&2
    exit 1
    ;;
esac

exit 0
