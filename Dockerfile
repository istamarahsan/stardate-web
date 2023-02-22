ARG PORT
FROM maven:3.8.7-eclipse-temurin-17
COPY pom.xml /pom.xml
COPY src/main /src/main
RUN mvn -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install
EXPOSE $PORT
ENV PORT $PORT
ENTRYPOINT ["java", "-jar", "/target/app.jar"]