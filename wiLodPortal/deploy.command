rm target/wiLodPortal.war 
rm -r target/wiLodPortal
rm /usr/local/Cellar/tomcat/7.0.42/libexec/webapps/wiLodPortal.war
rm -r /usr/local/Cellar/tomcat/7.0.42/libexec/webapps/wiLodPortal

catalina stop

../start_OS_X.command