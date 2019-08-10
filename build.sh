#!/bin/bash
echo "Building API Server.."
currdir=`pwd`
mkdir -p release
cd infoserver/
mvn clean install
ret=$?
cd ..
if [ $ret -eq 0 ]; then
	cp infoserver/target/*.jar release/
	echo "API server build succeeded."
else
	echo "API server build failed, terminating.."
fi
exit $ret
