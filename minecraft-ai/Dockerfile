FROM eclipse-temurin:23-jre-noble
ENV AI_API_KEY=NA
ENV ACTIVE_MQ_HOST=localhost
ENV ACTIVE_MQ_PORT=61616
ENV ACTIVE_MQ_USERNAME=NA
ENV ACTIVE_MQ_PASSWORD=NA
ARG DEBIAN_FRONTEND=noninteractive
RUN useradd -ms /bin/bash minecraft
USER minecraft
ARG JAR_FILE=target/minecraft-empires-ai-0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /home/minecraft/app.jar
WORKDIR /home/minecraft
ENTRYPOINT ["java","-jar","/home/minecraft/app.jar"]