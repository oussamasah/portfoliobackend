FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/portfolio.jar portfolio.jar
ENTRYPOINT ["java", "-jar", "/portfolio.jar"]
