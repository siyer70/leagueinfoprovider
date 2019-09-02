#!/bin/bash
GENERATE_ARTIFACT_FOR_ENV=$1
MY_APP_VERSION=$2
RELEASE_VERSION_FOLDER="$GENERATE_ARTIFACT_FOR_ENV/infoserver-$MY_APP_VERSION"
echo "Building API Server.."
currdir=`pwd`
rm -r releases/$RELEASE_VERSION_FOLDER
mkdir -p releases/$RELEASE_VERSION_FOLDER
cd infoserver/
mvn clean install
ret=$?
cd ..
if [ $ret -eq 0 ]; then
	echo "Version: $MY_APP_VERSION"
	cp infoserver/target/*.jar releases/$RELEASE_VERSION_FOLDER
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
		cp -R league-info-app/build releases/$RELEASE_VERSION_FOLDER
		tar -czvf release.tar.gz releases/$RELEASE_VERSION_FOLDER
		echo "UI Component built successfully."
	else
		echo "UI Component build failed, terminating.."
	fi	
else
	echo "API server build failed, terminating.."
fi
exit $ret
