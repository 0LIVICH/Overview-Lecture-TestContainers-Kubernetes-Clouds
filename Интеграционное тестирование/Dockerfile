FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8081
ADD build/libs/demo-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"] 