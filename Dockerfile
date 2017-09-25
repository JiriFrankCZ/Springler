FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/Springler.jar app.jar
ENV JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=prod -Duser.timezone=Europe/Prague "
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
