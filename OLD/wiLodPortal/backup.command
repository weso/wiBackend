zip -r backup/webapp_$(date +%y.%m.%d.%H.%M.%S).zip  src/main/webapp

cp -R /usr/local/Cellar/tomcat/7.0.42/libexec/webapps/wiLodPortal/static/* src/main/webapp/static
