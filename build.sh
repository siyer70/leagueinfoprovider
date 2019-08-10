#!/bin/bash
echo "Building API Server.."
currdir=`pwd`
mkdir -p release
cd infoserver/
$M2_HOME/bin/mvn clean install
ret=$?
cd ..
if [ $ret -eq 0 ]; then
	cp infoserver/target/*.jar release/
	echo "API server build succeeded."
	cd league-info-app/
	echo "Building UI Component.."
	npm run build
	ret=$?
	cd ..
	if [ $ret -eq 0 ]; then
		cp -R league-info-app/build release/
		echo "UI Component built successfully."
	fi
else
	echo "API server build failed, terminating.."
fi
exit $ret
