FROM openjdk:17

MAINTAINER bfrost.myafiback

# Service name
ENV SERVICE_NAME myafiback

ENV VERSION 0.0.1-SNAPSHOT

# Context path
ENV CONTEXT_PATH myafiback
# WAR filename
ENV WAR_FILENAME myafiback.jar

COPY /myafiback/target/${SERVICE_NAME}-${VERSION}.jar ${WAR_FILENAME}

COPY ./entrypoint.sh /

RUN chmod 755 entrypoint.sh

ENTRYPOINT ["java", "-jar", "/myafiback.jar"]

# Expose necessary port
EXPOSE 8095

# HEALTHCHECK
HEALTHCHECK --retries=3 CMD curl --fail http://localhost:8095/actuator/health || exit 1

