#vars

COMPUTEX_DIR="computex"
GIT_COMPUTEX_REPO="https://github.com/weso/computex.git"
GIT_COMPUTEX_BRANCH="master"

WIFETCHER_DIR="wiFetcher"
GIT_WIFETCHER_REPO="https://github.com/weso/wiFetcher.git"
GIT_WIFETCHER_BRANCH="web"

WILOPORTAL_DIR="wiLodPortal"
FUSEKI_DIR="fuseki"
PUBBY_DIR="pubby"

#Fetch the latest computex repository:

function update_repo {
	if [[ -d "$1" && ! -L "$1" ]] ; then
		(cd $1; git checkout $3)
		(cd $1; git pull origin $3)
	else
		git clone $2
		(cd $1; git checkout $3)
	fi
}

update_repo ${COMPUTEX_DIR} ${GIT_COMPUTEX_REPO} ${GIT_COMPUTEX_BRANCH}
update_repo ${WIFETCHER_DIR} ${GIT_WIFETCHER_REPO} ${GIT_WIFETCHER_BRANCH}

#Set FUSEKI_HOME Environment Variable
export FUSEKI_HOME="${FUSEKI_HOME:-${FUSEKI_DIR}}"

#Starts Pubby
(cd ${PUBBY_DIR}; mvn tomcat:run -Dmaven.tomcat.port=8081 &)

#Starts wiLodPortal Project
(cd ${WILOPORTAL_DIR}; play "~run 9001" &)

#Starts Play Project
(cd ${WIFETCHER_DIR}; play "~run 9002" &)

#Computex
#(cd ${COMPUTEX_DIR}; play "~run 9003 &)
