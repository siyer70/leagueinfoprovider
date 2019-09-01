TIME_STAMP=`date '+%Y%m%d%H%M%S'`
if [ -f release.tar.gz ]; then
   mv release.tar.gz "backup/release-$TIME_STAMP".tar.gz
else
   echo "No release file to backup"
fi
