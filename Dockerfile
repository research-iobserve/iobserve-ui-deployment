FROM jetty

#RUN echo 'deb http://ppa.launchpad.net/webupd8team/java/ubuntu yakkety main' > /etc/apt/sources.list.d/java.list
#COPY java.key /tmp

#RUN apt-key add /tmp/java.key \
#	&& apt-get update \
#	&& apt-get install -y --force-yes oracle-java8-installer
#ENV JAVA_HOME=/usr/lib/jvm/java-8-oracle/

#COPY backend/postgres-entrypoint.sh /usr/local/bin/

# make jetty available
COPY backend/target/iobserve-ui-backend-1.0-SNAPSHOT.war $JETTY_BASE/webapps/
COPY backend/docker-entrypoint.sh /
RUN mkdir /var/cache/databases/ && chmod 777 /var/cache/databases/

EXPOSE 8080
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["java","-jar","/usr/local/jetty/start.jar"]

