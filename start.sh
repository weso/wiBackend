#vars
GIT_DIR="computex"
GIT_REPO="https://github.com/weso/computex.git"
GIT_BRANCH="web"
FUSEKI_DIR="fuseki"
PLAY_DIR="wiLodPortal" 

#Fetch the latest computex repository:
if [[ -d "${GIT_DIR}" && ! -L "${GIT_DIR}" ]] ; then
	(cd ${GIT_DIR}; git checkout ${GIT_BRANCH})
	(cd ${GIT_DIR}; git pull origin ${GIT_BRANCH})
else
	git clone ${GIT_REPO}
	(cd ${GIT_DIR}; git checkout ${GIT_BRANCH})
fi

#Set FUSEKI_HOME Environment Variable
export FUSEKI_HOME="${FUSEKI_HOME:-${FUSEKI_DIR}}"

#Starts Fuseki:
${FUSEKI_DIR}/fuseki-server --config=${FUSEKI_DIR}/ConfigFile.ttl;

# Starts Memchahed
# memcached

#Starts Play Project
(cd ${PLAY_DIR}; play ~run)
