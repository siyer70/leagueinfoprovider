#!/bin/bash
echo "Building API Server.."
currdir=`pwd`
rm -r release
mkdir -p release
cd infoserver/
mvn clean install
ret=$?
cd ..
if [ $ret -eq 0 ]; then
	cp infoserver/target/*.jar release/
	tar -czvf release.tar.gz release/
	echo "API server build succeeded."
	echo "Building UI Component.."
	cd league-info-app/
	echo "npm version is:"
	npm -v
	npm install
	npm run build
	ret=$?
	cd ..
	if [ $ret -eq 0 ]; then
		cp -R league-info-app/build release/
		echo "UI Component built successfully."
	else
		echo "UI Component build failed, terminating.."
	fi	
else
	echo "API server build failed, terminating.."
fi
exit $ret
