FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/portfolio.jar portfolio.jar
ENTRYPOINT ["java", "-jar", "/portfolio.jar"]
