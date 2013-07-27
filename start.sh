#vars
GIT_DIR="computex"
FUSEKI_DIR="fuseki"
GIT_REPO="https://github.com/weso/computex.git"
PLAY_DIR="play" 
MVN_DIR="mvn" 

#Fetch the latest computex repository:
if [[ -d "${GIT_DIR}" && ! -L "${GIT_DIR}" ]] ; then
	cd ${GIT_DIR}
	git pull origin master
	cd ..
else
	git clone ${GIT_REPO}
fi

#Starts Fuseki:
cd ${FUSEKI_DIR}
./fuseki-server --config=ConfigFile.ttl

cd ..
#Starts play [Uncomment]
#cd ${PLAY_DIR}
#play run

#Start Spring [Uncomment]
#mvn tomcat run -f ${MVN_DIR}/pom.xml
