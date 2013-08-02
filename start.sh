#vars
GIT_DIR="computex"
FUSEKI_DIR="fuseki"
GIT_REPO="https://github.com/weso/computex.git"
PLAY_DIR="play" 
MVN_DIR="mvn" 

#Fetch the latest computex repository:
if [[ -d "${GIT_DIR}" && ! -L "${GIT_DIR}" ]] ; then
	cd ${GIT_DIR}
#	Uncomment the line below as soon as the 100 countries example is integrated in computex
#	git pull origin master
	cd ..
else
	git clone ${GIT_REPO}
fi

#Set FUSEKI_HOME Environment Variable
export FUSEKI_HOME="${FUSEKI_HOME:-${FUSEKI_DIR}}"
#Starts Fuseki:
${FUSEKI_DIR}/fuseki-server --config=${FUSEKI_DIR}/ConfigFile.ttl & memcached & cd wiLodPortal; mvn tomcat7:undeploy; mvn tomcat7:deploy

#Starts Play Project [Uncomment]
#(cd ${PLAY_DIR; play run)

#Start Maven Project [Uncomment]
#mvn tomcat run -f ${MVN_DIR}/pom.xml
