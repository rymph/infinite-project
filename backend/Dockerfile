FROM gradle:8.2.1-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle clean build


FROM tomcat:9.0-jre17-temurin
COPY --from=build /home/gradle/project/build/libs/*.war /usr/local/tomcat/webapps/
RUN echo $(ls /usr/local/tomcat/webapps/ | grep -E "*.war")
CMD ["catalina.sh", "run"]